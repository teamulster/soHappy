package de.hsaugsburg.teamulster.sohappy.detector

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FerTFLiteSmileDetectorImpl
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FerTFLiteSmileDetectorImplTest {
    lateinit var tfliteImpl: FerTFLiteSmileDetectorImpl
    private lateinit var scenario: ActivityScenario<CameraActivity>
    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        scenario = launchActivity<CameraActivity>()
        scenario.onActivity { tfliteImpl = FerTFLiteSmileDetectorImpl(it) }
    }

    @Test
    fun useSmileDetectorWithPositiveResult() {
        scenario.onActivity {
            val istr = instrumentationContext.assets.open("tflite_test.jpg")
            val detectResult = tfliteImpl.detect(BitmapFactory.decodeStream(istr))
            assertTrue(detectResult.isSmiling)
        }
    }

    @Test
    fun useSmileDetectorWithNegativeResult() {
        scenario.onActivity {
            val istr = instrumentationContext.assets.open("tflite_negative_test.png")
            val detectResult = tfliteImpl.detect(BitmapFactory.decodeStream(istr))
            assertFalse(detectResult.isSmiling)
        }
    }
}
