package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import java.nio.ByteBuffer
import java.nio.ByteOrder

// TODO: add self-trained model
class TFLiteImpl(activity: Activity) :
    TFLite(tfliteModelPath = "model.tflite", numberOfThreads = 1, activity) {

    init {
        // TODO: add "labels" based on model
        labels.add("Angry")
        labels.add("Disgust")
        labels.add("Fear")
        labels.add("Happy")
        labels.add("Sad")
        labels.add("Surprise")
        labels.add("Neutral")
    }

    // Detect emotion by running execute method in super class (TFLite)
    override fun detect(bitmap: Bitmap): SmileDetector.Companion.SmileDetectionResult {
        val predictionResults: ArrayList<Companion.Recognition> = super.execute(bitmap)
        return if (predictionResults[0].title == "Happy") {
            SmileDetector.Companion.SmileDetectionResult(true)
        } else {
            Companion.SmileDetectionResult(false, predictionResults)
        }
    }

    // Get image ready for prediction
    override fun prepare(img: Bitmap): ByteBuffer? {
        val scaledBitmap = Bitmap.createScaledBitmap(img, 48, 48, true)

        return getByteBuffer(scaledBitmap)
    }

    // Convert bitmap to bytebuffer
    private fun getByteBuffer(bitmap: Bitmap): ByteBuffer? {
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
