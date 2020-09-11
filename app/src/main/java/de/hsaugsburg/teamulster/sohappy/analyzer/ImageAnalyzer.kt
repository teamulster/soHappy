package de.hsaugsburg.teamulster.sohappy.analyzer

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import de.hsaugsburg.teamulster.sohappy.analyzer.collector.Measurement
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.DetectionResult
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import de.hsaugsburg.teamulster.sohappy.config.ImageAnalyzerConfig
import de.hsaugsburg.teamulster.sohappy.factories.DetectorFactory
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.*
import de.hsaugsburg.teamulster.sohappy.fragment.CameraFragment
import kotlin.concurrent.thread

/**
 * This class implements the ImageAnalyzer based on a given ImageAnalyzerConfig.
 *
 * @param [config] a given ImageAnalyzerConfig which determines the
 *               faceDetectorImpl/smileDetectorImpl to be used
 */
class ImageAnalyzer (val fragment: CameraFragment, config: ImageAnalyzerConfig) {
    companion object {
        enum class ImageAnalyzerState {
            NONE,
            FACE_DETECTION,
            SMILE_DETECTION,
            CANCEL
        }
    }
    private val measurement = Measurement()
    private var faceDetector: FaceDetector? = DetectorFactory.getFaceDetectorFromConfig(config, activity)
    private var smileDetector: SmileDetector? = DetectorFactory.getSmileDetectorFromConfig(config, activity)
    private var imageAnalyzerState: ImageAnalyzerState = ImageAnalyzerState.NONE

    init {
        fragment.stateMachine.onStateChangeList.add { _, new ->
            imageAnalyzerState = when (new) {
                // TODO: use boolean value
                is WaitingForFace -> ImageAnalyzerState.FACE_DETECTION
                is WaitingForSmile -> ImageAnalyzerState.SMILE_DETECTION
                is Questions -> ImageAnalyzerState.CANCEL
                is NoSmile, is Start -> ImageAnalyzerState.CANCEL
                else -> imageAnalyzerState
            }
        }
    }

    /**
     * This function calculates a FaceDetectionResult using [faceDetector].
     *
     * @param [img] a bitmap which will be analyzed
     * @return [DetectionResult]
     */
    fun computeFaceDetectionResult(img: Bitmap): DetectionResult {
        val result = faceDetector?.detect(img)
        return DetectionResult(result, null)
    }

    /**
     * This function calculates a SmileDetectionResult which holds the result,
     * whether or not a smile was detected in a already analyzed FaceDetectionResult.
     *
     * @param [img] a bitmap which will be analyzed
     * @return [DetectionResult]
     */
    fun computeSmileDetectionResult(img: Bitmap): DetectionResult {
        val faceDetectionResult = faceDetector?.detect(img)
        val croppedOutFace = faceDetectionResult?.frame?.let { BitmapEditor.crop(img, it) }
        val smileDR = croppedOutFace?.let { smileDetector?.detect(it) }
        return DetectionResult(faceDetectionResult, smileDR)
    }

    /**
     * starts an thread with an infinite loop. Gets the current frame from the bitmapQueue and
     * and processes it.
     */
    fun execute() {
        // TODO: Improve this
        thread {
            while (true) {
                val bitmap = fragment.queue.poll()
                if (bitmap != null) {
                    val result = when (imageAnalyzerState) {
                        ImageAnalyzerState.NONE -> DetectionResult(null, null)
                        ImageAnalyzerState.FACE_DETECTION -> computeFaceDetectionResult(bitmap)
                        ImageAnalyzerState.SMILE_DETECTION -> computeSmileDetectionResult(bitmap)
                        ImageAnalyzerState.CANCEL -> break
                    }
                    Log.d("Result:", result.toString())
                    measurement.add(result)
                    bitmap.recycle()
                }
            }
        }
    }
}
