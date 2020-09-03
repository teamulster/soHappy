package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.graphics.Bitmap

interface SmileDetector {
    companion object {
        class SmileDetectionResult (isSmiling: Boolean)
    }

    val detectorName: String

    fun detect(img: Bitmap) : SmileDetectionResult
}
