package de.hsaugsburg.teamulster.sohappy.analyzer.detector

/**
 * This data class stores faceDetectionResult / smileDetectionResult of a analyzed frame.
 *
 * @property [faceDetectionResult]
 * @property [smileDetectionResult]
 * */
data class DetectionResult(
    val faceDetectionResult: FaceDetector.Companion.FaceDetectionResult?,
    val smileDetectionResult: SmileDetector.Companion.SmileDetectionResult?
)
