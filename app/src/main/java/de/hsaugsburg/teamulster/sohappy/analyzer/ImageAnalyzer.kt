package de.hsaugsburg.teamulster.sohappy.analyzer

import android.graphics.Bitmap
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import de.hsaugsburg.teamulster.sohappy.config.ImageAnalyzerConfig

/**
 * This class implements the ImageAnalyzer based on a given ImageAnalyzerConfig.
 *
 * @param config a given ImageAnalyzerConfig which determines the
 *               faceDetectorImpl/smileDetectorImpl to be used
 */
class ImageAnalyzer
    (config: ImageAnalyzerConfig) {
    var faceDetector: FaceDetector =
        Class.forName(config.faceDetector).newInstance() as FaceDetector
    var smileDetector: SmileDetector =
        Class.forName(config.smileDetector).newInstance() as SmileDetector

    /**
     * This function calculates a FaceDetectionResult which holds the result,
     * whether or not a face was detected.
     *
     * @param img a bitmap which will be analyzed
     * @return FaceDetector.Companion.FaceDetectionResult?
     */
    fun computeFaceDetectionResult(img: Bitmap): FaceDetector.Companion.FaceDetectionResult? =
        faceDetector.detect(img)

    /**
     * This function calculates a SmileDetectionResult which holds the result,
     * whether or not a smile was detected in a already analyzed FaceDetectionResult.
     *
     * @param img a bitmap which will be analyzed
     * @return SmileDetector.Companion.SmileDetectionResult?
     */
    fun computeSmileDetectionResult(img: Bitmap): SmileDetector.Companion.SmileDetectionResult? {
        val faceDR = faceDetector.detect(img)
            ?: //fixme: do we want to throw an exception here?
            return null
        val croppedOutFace = BitmapEditor.crop(img, faceDR.frame)!!
        return smileDetector.detect(croppedOutFace)
    }
}
