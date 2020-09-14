package de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl

import android.graphics.Bitmap
import android.graphics.Rect
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector

/**
 * This class implements a FaceDetector using the Google MLKit API.
 * */
class GoogleMLKitAPIFaceDetectorImpl : FaceDetector {
    override val detectorName: String = "Google MLKit API Face Detector"

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(com.google.android.gms.vision.face.FaceDetector.ACCURATE_MODE)
        .setLandmarkMode(com.google.android.gms.vision.face.FaceDetector.NO_LANDMARKS)
        .setClassificationMode(com.google.android.gms.vision.face.FaceDetector.ALL_CLASSIFICATIONS)
        .build()

    override fun detect(img: Bitmap): FaceDetector.Companion.FaceDetectionResult? {
        // Init faceDetector and convert bitmap to InputImage object
        val faceDetector = FaceDetection.getClient(options)
        val inputImage = InputImage.fromBitmap(img, 0)
        var result: FaceDetector.Companion.FaceDetectionResult? = null
        // process given image and store results as FaceDetectionResult
        faceDetector.process(inputImage)
            .addOnSuccessListener { faces ->
                val firstFace = faces[0]
                result = FaceDetector.Companion.FaceDetectionResult(
                    Rect(
                        firstFace.boundingBox.left, firstFace.boundingBox.top,
                        firstFace.boundingBox.right, firstFace.boundingBox.bottom
                    )
                )
            }
        return result
    }
}
