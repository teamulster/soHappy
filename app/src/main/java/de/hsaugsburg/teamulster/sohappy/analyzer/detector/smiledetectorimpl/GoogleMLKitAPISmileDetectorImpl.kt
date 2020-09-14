package de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl

import android.graphics.Bitmap
import com.google.android.gms.vision.face.FaceDetector.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector

class GoogleMLKitAPISmileDetectorImpl :
    SmileDetector {
    companion object {
        /**
         * This data class inherits the SmileDetector.Companion.SmileDetectionResult(isSmiling) function
         * and overrides it.
         *
         * @param isSmiling determines whether the image contains a smiling person or not
         * @param predictionResults stores all top matches
         * @constructor creates SmileDetectionResult object containing the given params
         * */
        data class SmileDetectionResult(
            override val isSmiling: Boolean,
            override val predictionResults: ArrayList<SmileDetector.Companion.Recognition>
        ) :
            SmileDetector.Companion.SmileDetectionResult
    }

    override val detectorName: String = "Google MLKit API Smile Detector"

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(ACCURATE_MODE)
        .setLandmarkMode(NO_LANDMARKS)
        .setClassificationMode(ALL_CLASSIFICATIONS)
        .build()

    override fun detect(img: Bitmap): SmileDetector.Companion.SmileDetectionResult {
        val faceDetector = FaceDetection.getClient(options)
        val inputImage = InputImage.fromBitmap(img, 0)
        lateinit var result: SmileDetector.Companion.SmileDetectionResult
        faceDetector.process(inputImage)
            .addOnSuccessListener { faces ->
                val firstFace = faces[0]
                val predictionResults = ArrayList<SmileDetector.Companion.Recognition>()
                var isSmiling = false

                if (firstFace.smilingProbability!! > 0.75f) {
                    isSmiling = true
                    predictionResults.add(
                        SmileDetector.Companion.Recognition(
                            "Smiling",
                            firstFace.smilingProbability!!
                        )
                    )
                }
                result = SmileDetectionResult(
                    isSmiling,
                    predictionResults
                )
            }
            .addOnFailureListener { err ->
                // TODO: move to next state after timer has expired or add exception handling
            }
        return result
    }
}