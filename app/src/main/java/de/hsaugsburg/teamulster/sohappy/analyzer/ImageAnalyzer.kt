package de.hsaugsburg.teamulster.sohappy.analyzer

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import de.hsaugsburg.teamulster.sohappy.config.ImageAnalyzerConfig
import de.hsaugsburg.teamulster.sohappy.factories.DetectorFactory
import kotlin.concurrent.thread

/**
 * This class implements the ImageAnalyzer based on a given ImageAnalyzerConfig.
 *
 * @param config a given ImageAnalyzerConfig which determines the
 *               faceDetectorImpl/smileDetectorImpl to be used
 */
class ImageAnalyzer (val activity: CameraActivity, config: ImageAnalyzerConfig) {
    private var faceDetector: FaceDetector? = DetectorFactory.getFaceDetectorFromConfig(config, activity)
    private var smileDetector: SmileDetector? = DetectorFactory.getSmileDetectorFromConfig(config, activity)

    /**
     * This function calculates a FaceDetectionResult using [faceDetector].
     *
     * @param img a bitmap which will be analyzed
     * @return [FaceDetector.Companion.FaceDetectionResult]
     */
    fun computeFaceDetectionResult(img: Bitmap): FaceDetector.Companion.FaceDetectionResult? =
        faceDetector?.detect(img)

    /**
     * This function calculates a SmileDetectionResult which holds the result,
     * whether or not a smile was detected in a already analyzed FaceDetectionResult.
     *
     * @param img a bitmap which will be analyzed
     * @return SmileDetector.Companion.SmileDetectionResult?
     */
    fun computeSmileDetectionResult(img: Bitmap): SmileDetector.Companion.SmileDetectionResult? {
        val faceDR = faceDetector?.detect(img)
        if (faceDR == null) {
            //fixme: do we want to throw an exception here?
            return null;
        }
        val croppedOutFace = BitmapEditor.crop(img, faceDR.frame)!!
        val smileDR = smileDetector!!.detect(croppedOutFace)
        return smileDR
    }

    /**
     * starts an thread with an infinite loop. Gets the current frame from the bitmapQueue and
     * and processes it.
     */
    fun execute() {
        // TODO: Improve this
        thread {
            while (true) {
                val bitmap = this.activity.queue.poll()
                if (bitmap != null) {
                    val result = computeFaceDetectionResult(bitmap)
                    Log.d("Result:", result?.frame.toString())
                    bitmap.recycle()
                }
            }
        }
    }
}
