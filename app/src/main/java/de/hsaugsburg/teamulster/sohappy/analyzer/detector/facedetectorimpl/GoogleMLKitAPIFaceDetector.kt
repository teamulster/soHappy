package de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl

import android.graphics.Bitmap
import android.graphics.Rect
import com.google.common.util.concurrent.SettableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector

/**
 * This class implements a FaceDetector using the Google MLKit API.
 * */
class GoogleMLKitAPIFaceDetector : FaceDetector {
    override val detectorName: String = "Google MLKit API Face Detector"

    // Specify options for MLKit SmileDetector and init faceDetector
    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()
    private val faceDetector = FaceDetection.getClient(options)

    override fun detect(img: Bitmap): FaceDetector.Companion.FaceDetectionResult? {
        // Init faceDetector and convert bitmap to InputImage object
        val inputImage = InputImage.fromBitmap(img, 0)
        val future = SettableFuture.create<FaceDetector.Companion.FaceDetectionResult?>()

        // Process given image and store results as FaceDetectionResult
        faceDetector.process(inputImage)
            .addOnSuccessListener { faces ->
                // Guard clause in case no face was detected
                if (faces.size == 0) {
                    future.set(null)
                    return@addOnSuccessListener
                }
                val firstFace = faces[0]

                // Convert detected face to FaceDetectionResult and set it as future
                future.set(
                    FaceDetector.Companion.FaceDetectionResult(
                        Rect(
                            firstFace.boundingBox.left,
                            firstFace.boundingBox.top,
                            firstFace.boundingBox.right,
                            firstFace.boundingBox.bottom
                        )
                    )
                )
            }
            // Set future result null if faceDetector fails
            .addOnFailureListener {
                future.set(null)
            }
        return future.get()
    }
}
