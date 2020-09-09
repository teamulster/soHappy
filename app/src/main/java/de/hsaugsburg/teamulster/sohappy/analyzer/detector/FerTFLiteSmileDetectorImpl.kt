package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import java.nio.ByteBuffer
import java.nio.ByteOrder

// TODO: add self-trained model
/**
* This class inherits AbstractTFLiteSmileDetector and prepares the image before TF lite detection is run.
*
* @param activity the current activity this class was invoked by (e.g. CameraActivity)
* @constructor creates a TFLiteImpl object with a test model
* */
class FerTFLiteSmileDetectorImpl(activity: Activity) :
    AbstractTFLiteSmileDetector(tfliteModelPath = "model.tflite", numberOfThreads = 1, activity) {

    init {
        // TODO: This has to be adapted depending on each model
        labels.add("Angry")
        labels.add("Disgust")
        labels.add("Fear")
        labels.add("Happy")
        labels.add("Sad")
        labels.add("Surprise")
        labels.add("Neutral")
    }

    /**
    * This function overrides the detect function declared in the AbstractTFLiteSmileDetector
    * abstract class.
    * It runs TF lite detection by invoking the execute method in its super class
    * (AbstractTFLiteSmileDetector).
    *
    * @param bitmap the image which TF lite will be running detection on.
    * @return Companion.SmileDetectionResult
    * */
    override fun detect(bitmap: Bitmap): Companion.SmileDetectionResult {
        val predictionResults: ArrayList<Companion.Recognition> = super.execute(bitmap)
        val firstPredictionResult = predictionResults[0]
        var isSmiling = false
        // if Happy is detected, set isSmiling = true
        // TODO: This has to be adapted depending on each model
        if (firstPredictionResult.title == "Happy" && firstPredictionResult.confidence > 0.75f) {
            isSmiling = true
        }
        return Companion.SmileDetectionResult(isSmiling, predictionResults)
    }

    /**
    * This private function overrides the prepare function declared in the AbstractTFLiteSmileDetector
    * abstract class.
    * It scales a given bitmap and passes the result to the convertToByteBuffer function to get it ready
    * for prediction.
    *
    * @param img the current image which will be prepared
    * @return ByteBuffer?
    * */
    override fun prepare(img: Bitmap): ByteBuffer? {
        // TODO: This has to be adapted depending on each model
        val scaledBitmap = Bitmap.createScaledBitmap(img, 48, 48, true)
        return convertToByteBuffer(scaledBitmap)
    }

    /**
    * This private function converts a given Bitmap into a ByteBuffer.
    *
    * @param bitmap a Bitmap which will be converted into a ByteBuffer
    * @return ByteBuffer?
    * */
    private fun convertToByteBuffer(bitmap: Bitmap): ByteBuffer? {
        val width = bitmap.width
        val height = bitmap.height
        val mImgData: ByteBuffer = ByteBuffer
            .allocateDirect(4 * width * height)
        mImgData.order(ByteOrder.nativeOrder())
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        for (pixel in pixels) {
            mImgData.putFloat(Color.red(pixel).toFloat())
        }
        return mImgData
    }
}