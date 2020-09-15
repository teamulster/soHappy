package de.hsaugsburg.teamulster.sohappy.detector

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.GoogleMLKitAPISmileDetectorImpl
import junit.framework.Assert
import org.junit.Test

class GoogleMLKitAPISmileDetectorTest {
    private val mLKitFaceDetector = GoogleMLKitAPISmileDetectorImpl()
    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context

    @Test
    fun useDetectPositive() {
        val istr = instrumentationContext.assets.open("smileDetector_test_positive.jpg")
        val detectResult = mLKitFaceDetector.detect(BitmapFactory.decodeStream(istr))
        if (detectResult != null) {
            Assert.assertTrue(detectResult.isSmiling)
        }
    }

    @Test
    fun useDetectNegative() {
        val istr = instrumentationContext.assets.open("smileDetector_test_negative.png")
        val detectResult = mLKitFaceDetector.detect(BitmapFactory.decodeStream(istr))
        if (detectResult != null) {
            Assert.assertFalse(detectResult.isSmiling)
        }
    }
}
