package de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl

import android.app.Activity
import android.graphics.Bitmap
import de.hsaugsburg.teamulster.sohappy.analyzer.BitmapEditor.convertToByteBuffer
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import java.nio.ByteBuffer

/**
 * implementation for our self-trained model.
 */
class SoHappyTFLiteSmileDetector(activity: Activity) :
    AbstractTFLiteSmileDetector("model_so_happy.tflite", activity) {

    init {
        labels.add("smile")
        labels.add("nosmile")
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
        val scaledBitmap = Bitmap.createScaledBitmap(img, 64, 64, true)
        return convertToByteBuffer(scaledBitmap)
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
    override fun detect(img: Bitmap): SmileDetector.Companion.SmileDetectionResult? {
        val predictionResults: ArrayList<SmileDetector.Companion.Recognition> = super.execute(img)
        val firstPredictionResult = predictionResults[0]
        var isSmiling = false
        if (firstPredictionResult.title == "smile" && firstPredictionResult.confidence > 0.75f) {
            isSmiling = true
        }
        return Companion.SmileDetectionResult(isSmiling, predictionResults)
    }
}
