package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.media.Image

interface SmileDetector {
    companion object {
        class SmileDetectionResult (isSmiling: Boolean)
    }

    val detectorName: String

    fun detect(img: Image) : SmileDetectionResult
}
