package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores a ImageAnalyzerConfig.
 *
 * @property faceDetector a package string to the selected faceDetectorImpl
 * @property smileDetector a package string to the selected smileDetectorImpl
 */
data class ImageAnalyzerConfig(
    val faceDetector: String,
    val smileDetector: String
)
