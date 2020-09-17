package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.HaarCascadeFaceDetector
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HaarCascadeFaceDetectorTest {
    private lateinit var haarCascadeFaceDetector: HaarCascadeFaceDetector
    private lateinit var scenario: ActivityScenario<MainActivity>
    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        scenario = launchActivity<MainActivity>()
        scenario.onActivity { haarCascadeFaceDetector = HaarCascadeFaceDetector(it) }
    }

    // TODO: Choose better files

    @Test
    fun detectTestPositive() {
        scenario.onActivity {
            val istream = instrumentationContext.assets.open("faceDetector_test_positive.jpg")
            val bitmap = BitmapFactory.decodeStream(istream)
            val detectResult = haarCascadeFaceDetector.detect(bitmap)
            assertTrue(detectResult != null)
        }
    }

    @Test
    fun detectTestNegative() {
        scenario.onActivity {
            val istream = instrumentationContext.assets.open("faceDetector_test_negative.jpg")
            val bitmap = BitmapFactory.decodeStream(istream)
            val detectResult = haarCascadeFaceDetector.detect(bitmap)
            assertTrue(detectResult == null)
        }
    }
}
