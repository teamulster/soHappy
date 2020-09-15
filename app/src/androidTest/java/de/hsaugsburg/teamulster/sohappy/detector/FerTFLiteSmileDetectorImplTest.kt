package de.hsaugsburg.teamulster.sohappy.detector

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.FerTFLiteSmileDetectorImpl
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FerTFLiteSmileDetectorImplTest {
    private lateinit var tfliteImpl: FerTFLiteSmileDetectorImpl
    private lateinit var scenario: ActivityScenario<CameraActivity>
    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        scenario = launchActivity<CameraActivity>()
        scenario.onActivity { tfliteImpl = FerTFLiteSmileDetectorImpl(it) }
    }

    @Test
    fun useDetectPositive() {
        scenario.onActivity {
            val istr = instrumentationContext.assets.open("smileDetector_test_positive.jpg")
            val detectResult = tfliteImpl.detect(BitmapFactory.decodeStream(istr))
            if (detectResult != null) {
                assertTrue(detectResult.isSmiling)
            }
        }
    }

    @Test
    fun useDetectNegative() {
        scenario.onActivity {
            val istr = instrumentationContext.assets.open("smileDetector_test_negative.png")
            val detectResult = tfliteImpl.detect(BitmapFactory.decodeStream(istr))
            if (detectResult != null) {
                assertFalse(detectResult.isSmiling)
            }
        }
    }
}
