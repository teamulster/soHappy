package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.GoogleMLKitAPIFaceDetector
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GoogleMLKitAPIFaceDetectorTest {
    private lateinit var mLKitFaceDetector: GoogleMLKitAPIFaceDetector
    private lateinit var scenario: ActivityScenario<MainActivity>
    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        scenario = launchActivity<CameraActivity>()
        scenario.onActivity { mLKitFaceDetector = GoogleMLKitAPIFaceDetector() }
    }

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
