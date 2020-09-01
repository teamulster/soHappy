package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.app.Activity
import android.media.Image

class TFLiteImpl(tfliteModelPath: String, numberOfThreads: Int, activity: Activity) :
    TFLite(tfliteModelPath, numberOfThreads, activity) {

    override fun detect(img: Image): SmileDetector.Companion.SmileDetectionResult {
        TODO("Not yet implemented")
        super.execute(img)
    }

    class SmileDetectionResult {

    }
}