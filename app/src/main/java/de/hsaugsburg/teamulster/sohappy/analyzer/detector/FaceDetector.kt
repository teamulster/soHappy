package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.graphics.Rect
import android.media.Image

interface FaceDetector {
    companion object {
        class FaceDetectionResult (frame: Rect)
    }

    val detectorName: String

    fun detect(img: Image) : FaceDetectionResult
}