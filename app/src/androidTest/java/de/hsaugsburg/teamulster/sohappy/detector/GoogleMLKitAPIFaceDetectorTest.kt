package de.hsaugsburg.teamulster.sohappy.detector

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.GoogleMLKitAPIFaceDetectorImpl
import junit.framework.Assert.assertTrue
import org.junit.Test

class GoogleMLKitAPIFaceDetectorTest {
    private val mLKitFaceDetector = GoogleMLKitAPIFaceDetectorImpl()
    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context

    @Test
    fun useDetectPositive() {
        val istr = instrumentationContext.assets.open("faceDetector_test_positive.jpg")
        val detectResult = mLKitFaceDetector.detect(BitmapFactory.decodeStream(istr))
        assertTrue(detectResult != null)

    }

    @Test
    fun useDetectNegative() {
        val istr = instrumentationContext.assets.open("faceDetector_test_negative.jpg")
        val detectResult = mLKitFaceDetector.detect(BitmapFactory.decodeStream(istr))
        assertTrue(detectResult == null)
    }
}
