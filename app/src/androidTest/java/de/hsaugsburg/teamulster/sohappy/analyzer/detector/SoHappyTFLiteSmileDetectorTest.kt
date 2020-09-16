package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.SoHappyTFLiteSmileDetector
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SoHappyTFLiteSmileDetectorTest {
    lateinit var tfliteImpl: SoHappyTFLiteSmileDetector
    private lateinit var scenario: ActivityScenario<CameraActivity>
    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        scenario = launchActivity<CameraActivity>()
        scenario.onActivity { tfliteImpl = SoHappyTFLiteSmileDetector(it) }
    }

    @Test
    fun useSmileDetectorWithPositiveResult() {
        scenario.onActivity {
            val istr = instrumentationContext.assets.open("tflite_test.jpg")
            val detectResult = tfliteImpl.detect(BitmapFactory.decodeStream(istr))
            assertTrue(detectResult!!.isSmiling)
        }
    }

    @Test
    fun useSmileDetectorWithNegativeResult() {
        scenario.onActivity {
            val istr = instrumentationContext.assets.open("test.jpg")
            val detectResult = tfliteImpl.detect(BitmapFactory.decodeStream(istr))
            assertFalse(detectResult!!.isSmiling)
        }
    }
}
