package de.hsaugsburg.teamulster.sohappy

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.TFLiteImpl
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SmileDetectorTest {
    lateinit var tfliteImpl : TFLiteImpl
    private lateinit var scenario : ActivityScenario<CameraActivity>
    private val instrumentationContext: Context = InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        scenario = launchActivity<CameraActivity>()
        scenario.onActivity { tfliteImpl = TFLiteImpl("model.tflite", 1, it) }
    }

    @Test
    fun useSmileDetectorWithPositiveResult() {
        scenario.onActivity {
            val istr = instrumentationContext.assets.open("test.jpg")
            val detectResult = tfliteImpl.detect(BitmapFactory.decodeStream(istr))
            assert(detectResult.isSmiling)
        }
    }

    @Test
    fun useSmileDetectorWithNegativeResult() {
        scenario.onActivity {
            val istr = instrumentationContext.assets.open("negative_test.png")
            val detectResult = tfliteImpl.detect(BitmapFactory.decodeStream(istr))
            assert(!detectResult.isSmiling)
        }
    }
}
