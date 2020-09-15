package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.graphics.Bitmap
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.AbstractTFLiteSmileDetector

/**
* This interface determines the SmileDetectorImpl functionality.
 *
 * @property SmileDetectionResult a static interface determining the isSmiling/predictionResults
 *                                properties
 * @property detectorName a string to a package name specifying the current SmileDetectorImpl
 */
interface SmileDetector {
    companion object {
        interface SmileDetectionResult {
            val isSmiling: Boolean
            val predictionResults: ArrayList<AbstractTFLiteSmileDetector.Companion.Recognition>
        }
    }

    val detectorName: String

    /**
     * This functions specifies that SmileDetectorImpl should have a detect function.
     *
     * @param img a bitmap which will be analyzed
     * @return SmileDetectionResult
     * */
    fun detect(img: Bitmap): SmileDetectionResult?
}
