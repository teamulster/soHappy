package de.hsaugsburg.teamulster.sohappy.analyzer.detector

data class DetectionResult(
    val faceDetectionResult: FaceDetector.Companion.FaceDetectionResult?,
    val smileDetectionResult: SmileDetector.Companion.SmileDetectionResult?
)