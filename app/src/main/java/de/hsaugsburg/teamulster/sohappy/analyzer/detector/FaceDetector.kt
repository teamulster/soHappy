package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.graphics.Bitmap
import android.graphics.Rect

interface FaceDetector {
    companion object {
        class FaceDetectionResult (frame: Rect)
    }

    val detectorName: String

    fun detect(img: Bitmap) : FaceDetectionResult
}
