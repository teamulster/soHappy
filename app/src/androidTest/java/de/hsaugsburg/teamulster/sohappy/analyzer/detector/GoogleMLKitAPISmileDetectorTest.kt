package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.GoogleMLKitAPISmileDetector
import junit.framework.Assert
import org.junit.Before
import org.junit.Test

class GoogleMLKitAPISmileDetectorTest {
    private lateinit var mLKitFaceDetector: GoogleMLKitAPISmileDetector
    private lateinit var scenario: ActivityScenario<MainActivity>
    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        scenario = launchActivity<MainActivity>()
        scenario.onActivity { mLKitFaceDetector = GoogleMLKitAPISmileDetector(it) }
    }

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
