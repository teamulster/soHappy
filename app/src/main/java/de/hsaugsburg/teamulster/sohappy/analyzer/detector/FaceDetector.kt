package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.graphics.Bitmap
import android.graphics.Rect

/**
 * This interface determines the FaceDetectorImpl functionality.
 *
 * @property FaceDetectionResult a static data class holding a Rect from an analyzed image
 * @property detectorName a string to a package name specifying the current FaceDetectorImpl
 */
interface FaceDetector {
    companion object {
        data class FaceDetectionResult(val frame: Rect)
    }

    val detectorName: String

    /**
     * This functions specifies that FaceDetectorImpl should have a detect function.
     *
     * @param img a bitmap which will be analyzed
     * @return FaceDetectionResult?
     */
    fun detect(img: Bitmap): FaceDetectionResult?
}
