package de.hsaugsburg.teamulster.sohappy.analyzer

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.provider.Settings
import android.util.Log
import android.view.InflateException
import androidx.fragment.app.activityViewModels
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.DetectionResult
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import de.hsaugsburg.teamulster.sohappy.config.ImageAnalyzerConfig
import de.hsaugsburg.teamulster.sohappy.exceptions.ExceptionHandler
import de.hsaugsburg.teamulster.sohappy.factories.DetectorFactory
import de.hsaugsburg.teamulster.sohappy.fragment.CameraFragment
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.*
import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel
import kotlin.concurrent.thread

/**
 * This class implements the ImageAnalyzer based on a given ImageAnalyzerConfig.
 *
 * @param [config] a given ImageAnalyzerConfig which determines the
 *               faceDetectorImpl/smileDetectorImpl to be used
 */
class ImageAnalyzer(val fragment: CameraFragment, config: ImageAnalyzerConfig) {
    companion object {
        enum class ImageAnalyzerState {
            NONE,
            FACE_DETECTION,
            SMILE_DETECTION,
            CANCEL
        }
    }

    private val measurement: MeasurementViewModel by fragment.activityViewModels()
    private lateinit var faceDetector: FaceDetector
    private lateinit var smileDetector: SmileDetector
    private var imageAnalyzerState: ImageAnalyzerState = ImageAnalyzerState.NONE
    private val stateMachine: StateMachine by fragment.activityViewModels()

    init {
        try {
            faceDetector =
                DetectorFactory.createFaceDetectorFromConfig(config, fragment.requireActivity())
            smileDetector =
                DetectorFactory.createSmileDetectorFromConfig(config, fragment.requireActivity())
        } catch (e: NoSuchMethodException) {
            ExceptionHandler.callExceptionDialog(fragment.requireActivity(), fragment.resources, e)
        } catch (e: ClassCastException) {
            ExceptionHandler.callExceptionDialog(fragment.requireActivity(), fragment.resources, e)
        } catch (e: InflateException) {
            ExceptionHandler.callExceptionDialog(fragment.requireActivity(), fragment.resources, e)
        } catch (e: UninitializedPropertyAccessException) {
            ExceptionHandler.callExceptionDialog(fragment.requireActivity(), fragment.resources, e)
        }

        stateMachine.addStateChangeListener { _, new ->
            if (fragment.isVisible) {
                imageAnalyzerState = when (new) {
                    is Start -> ImageAnalyzerState.NONE
                    is WaitingForFace, is TakeABreath -> ImageAnalyzerState.FACE_DETECTION
                    is WaitingForSmile -> ImageAnalyzerState.SMILE_DETECTION
                    is Questions, is NoSmile -> ImageAnalyzerState.CANCEL
                    else -> imageAnalyzerState
                }
            }
        }
        imageAnalyzerState = when (stateMachine.getCurrentMachineState()) {
            is Start -> ImageAnalyzerState.NONE
            is WaitingForFace, is TakeABreath -> ImageAnalyzerState.FACE_DETECTION
            is WaitingForSmile -> ImageAnalyzerState.SMILE_DETECTION
            is Questions, is NoSmile -> ImageAnalyzerState.CANCEL
            else -> imageAnalyzerState
        }
    }

    /**
     * This function calculates a FaceDetectionResult using [faceDetector].
     *
     * @param [img] a bitmap which will be analyzed
     * @return [DetectionResult]
     */
    private fun computeFaceDetectionResult(img: Bitmap): DetectionResult {
        val result = faceDetector.detect(img)
        return DetectionResult(result, null)
    }

    /**
     * This function calculates a SmileDetectionResult which holds the result,
     * whether or not a smile was detected in a already analyzed FaceDetectionResult.
     *
     * @param [img] a bitmap which will be analyzed
     * @return [DetectionResult]
     */
    private fun computeSmileDetectionResult(img: Bitmap): DetectionResult {
        val faceDetectionResult = faceDetector.detect(img)
        val croppedOutFace = faceDetectionResult?.frame?.let { BitmapEditor.crop(img, it) }
        val smileDR = croppedOutFace?.let { smileDetector.detect(it) }
        return DetectionResult(faceDetectionResult, smileDR)
    }

    /**
     * starts an thread with an infinite loop. Gets the current frame from the bitmapQueue and
     * and processes it.
     */
    @SuppressLint("HardwareIds")
    fun execute() {
        thread {
            while (true) {
                if (imageAnalyzerState == ImageAnalyzerState.CANCEL) {
                    measurement.id = Settings.Secure.getString(
                        fragment.context?.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                    (fragment.requireActivity() as MainActivity).localDatabaseManager?.addOrUpdateMeasurement(
                        measurement
                    )
                    break
                }
                val bitmap = fragment.queue.poll()
                if (bitmap != null) {
                    val result = when (imageAnalyzerState) {
                        ImageAnalyzerState.FACE_DETECTION -> {
                            val r = computeFaceDetectionResult(bitmap)
                            if (stateMachine.getCurrentMachineState() is WaitingForFace &&
                                r.faceDetectionResult != null
                            ) {
                                stateMachine.consumeAction(Action.FaceDetected)
                            }
                            r
                        }
                        ImageAnalyzerState.SMILE_DETECTION -> {
                            val r = computeSmileDetectionResult(bitmap)
                            if (stateMachine.getCurrentMachineState() is WaitingForSmile &&
                                r.smileDetectionResult != null &&
                                r.smileDetectionResult.isSmiling
                            ) {
                                stateMachine.consumeAction(Action.SmileDetected)
                            }
                            r
                        }
                        else -> null
                    }
                    Log.d("Result:", result.toString())
                    result?.let { measurement.addDetectionResult(it) }
                    bitmap.recycle()
                }
            }
        }
    }
}
