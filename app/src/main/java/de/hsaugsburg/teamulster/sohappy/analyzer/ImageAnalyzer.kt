package de.hsaugsburg.teamulster.sohappy.analyzer

import android.media.Image
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

    // NOTE: Do we really want to use android.media.Image ?
    fun computeFaceDetectionResult(img: Image): FaceDetector.Companion.FaceDetectionResult? {
        return null
    }

    fun computeSmileDetectionResult(img: Image): SmileDetector.Companion.SmileDetectionResult? {
        return null
    }
}