package de.hsaugsburg.teamulster.sohappy.analyzer

import android.graphics.Bitmap
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import de.hsaugsburg.teamulster.sohappy.config.ImageAnalyzerConfig

class ImageAnalyzer
    (config: ImageAnalyzerConfig) {
    var faceDetector: FaceDetector =
        Class.forName(config.faceDetector).newInstance() as FaceDetector
    var smileDetector: SmileDetector =
        Class.forName(config.smileDetector).newInstance() as SmileDetector

    @Suppress("FunctionOnlyReturningConstant")
    fun computeFaceDetectionResult(img: Bitmap): FaceDetector.Companion.FaceDetectionResult? =
        faceDetector.detect(img)

    @Suppress("FunctionOnlyReturningConstant")
    fun computeSmileDetectionResult(img: Bitmap): SmileDetector.Companion.SmileDetectionResult? {
        val faceDR = faceDetector.detect(img)
        if (faceDR == null) {
            //fixme: do we want to throw an exception here?
            return null;
        }
        val croppedOutFace = BitmapEditor.crop(img, faceDR.frame)!!
        val smileDR = smileDetector.detect(croppedOutFace)
        return smileDR
    }
}
