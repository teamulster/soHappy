package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.graphics.Bitmap

interface SmileDetector {
    companion object {
        interface SmileDetectionResult {
            val isSmiling: Boolean
            val predictionResults: ArrayList<AbstractTFLiteSmileDetector.Companion.Recognition>
        }
    }

    val detectorName: String

    fun detect(bitmap: Bitmap) : SmileDetectionResult
}
