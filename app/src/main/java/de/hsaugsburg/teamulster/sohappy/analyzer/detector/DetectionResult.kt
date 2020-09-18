package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import java.util.*

/**
 * This data class stores faceDetectionResult / smileDetectionResult of a analyzed frame.
 *
 * @property [faceDetectionResult]
 * @property [smileDetectionResult]
 * @property [timeStamp] a Date object
 * */
data class DetectionResult(
    val faceDetectionResult: FaceDetector.Companion.FaceDetectionResult?,
    val smileDetectionResult: SmileDetector.Companion.SmileDetectionResult?,
) {
    val timeStamp: Date = Date()
}
