package de.hsaugsburg.teamulster.sohappy.analyzer

import android.graphics.Bitmap
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import de.hsaugsburg.teamulster.sohappy.config.ImageAnalyzerConfig

class ImageAnalyzer {
    var faceDetector: FaceDetector
    var smileDetector: SmileDetector

    constructor(config: ImageAnalyzerConfig) {
        // TODO: Use proper method for this
        faceDetector = config.faceDetector.newInstance()
        smileDetector = config.smileDetector.newInstance()
    }

    @Suppress("FunctionOnlyReturningConstant")
    fun computeFaceDetectionResult(img: Bitmap): FaceDetector.Companion.FaceDetectionResult? {
        return faceDetector.detect(img)
    }

    @Suppress("FunctionOnlyReturningConstant")
    fun computeSmileDetectionResult(img: Bitmap): SmileDetector.Companion.SmileDetectionResult? {
        val faceDR = faceDetector.detect(img)
        if (faceDR == null) {
            //fixme: do we want to throw an exception here?
            return null;
        }
        var croppedOutFace = ImageEditor.crop(img, faceDR.frame)!!
        var smileDR = smileDetector.detect(croppedOutFace)
        return smileDR
    }
}
