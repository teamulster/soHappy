package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.graphics.Bitmap

interface SmileDetector {
    companion object {
        open class SmileDetectionResult (val isSmiling: Boolean)
    }

    val detectorName: String

    fun detect(bitmap: Bitmap) : SmileDetectionResult
}
