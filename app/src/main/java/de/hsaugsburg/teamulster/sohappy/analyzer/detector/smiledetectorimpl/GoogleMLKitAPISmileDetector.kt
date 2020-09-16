package de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl

import android.app.Activity
import android.graphics.Bitmap
import com.google.common.util.concurrent.SettableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector

/**
 * This class implements a SmileDetector using the Google MLKit API.
 * */
// TODO: remove Suppress statement when DetectorFactory is compliant
@Suppress("UnusedPrivateMember")
class GoogleMLKitAPISmileDetector(activity: Activity) : SmileDetector {
    companion object {
        /**
         * This data class inherits the SmileDetector.Companion.SmileDetectionResult(isSmiling) function
         * and overrides it.
         *
         * @param [isSmiling] determines whether the image contains a smiling person or not
         * @param [predictionResults] stores all top matches
         * @constructor creates SmileDetectionResult object containing the given params
         * */
        data class SmileDetectionResult(
            override val isSmiling: Boolean,
            override val predictionResults: ArrayList<SmileDetector.Companion.Recognition>
        ) : SmileDetector.Companion.SmileDetectionResult
    }

    override val detectorName: String = "Google MLKit API Smile Detector"

    // Specify options for MLKit SmileDetector and init faceDetector
    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()
    private val faceDetector = FaceDetection.getClient(options)

    override fun detect(img: Bitmap): SmileDetector.Companion.SmileDetectionResult? {
        // Convert bitmap to InputImage object and create SettableFuture to be able to work with
        // ML Kit callbacks
        val inputImage = InputImage.fromBitmap(img, 0)
        val future = SettableFuture.create<SmileDetector.Companion.SmileDetectionResult?>()

        // process given image and store results as SmileDetectionResult
        // NOTE: predictionResults has to be stored as ArrayList to make it compliant to the TF Lite approach
        faceDetector.process(inputImage)
            .addOnSuccessListener { faces ->
                // Init result vars
                val predictionResults = ArrayList<SmileDetector.Companion.Recognition>()
                var isSmiling = false
                // Guard clause in case no face was detected somehow
                if (faces.size == 0) {
                    future.set(SmileDetectionResult(isSmiling, predictionResults))
                    return@addOnSuccessListener
                }
                val firstFace = faces[0]

                // Add smiling probability to predictionResults if smiling confidence is above 0.7
                if (firstFace.smilingProbability != null &&
                    firstFace.smilingProbability!! > 0.7f
                ) {
                    isSmiling = true
                    predictionResults.add(
                        SmileDetector.Companion.Recognition(
                            "Smiling",
                            firstFace.smilingProbability!!
                        )
                    )
                }
                future.set(
                    SmileDetectionResult(
                        isSmiling,
                        predictionResults
                    )
                )
            }
            // Set empty SmileDetectionResult if faceDetector fails
            .addOnFailureListener {
                future.set(
                    SmileDetectionResult(
                        isSmiling = false,
                        ArrayList<SmileDetector.Companion.Recognition>()
                    )
                )
            }
        return future.get()
    }
}
