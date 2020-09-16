package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.graphics.Bitmap
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector.Companion.SmileDetectionResult

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
            val predictionResults: ArrayList<Recognition>
        }

        /**
         * This data class stores prediction results based on each label a model allows.
         *
         * @param title the label title.
         * @param confidence a Float value between 0 and 1 stating possible this label is
         * @constructor creates a Recognition object containing the given params
         * */
        data class Recognition(
            val title: String, val confidence: Float
        )
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
