package de.hsaugsburg.teamulster.sohappy

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.HaarCascadeFaceDetector
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import junit.framework.Assert.assertTrue as assertTrue

@RunWith(AndroidJUnit4::class)
class HaarCascadeFaceDetectorTest {
    private lateinit var haarCascadeFaceDetector: HaarCascadeFaceDetector
    private lateinit var scenario: ActivityScenario<CameraActivity>
    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        scenario = launchActivity<CameraActivity>()
        scenario.onActivity { haarCascadeFaceDetector = HaarCascadeFaceDetector(it) }
    }

    @Test
    fun detectTest() {
        scenario.onActivity {
            val inputStream = instrumentationContext.assets.open("test.jpg")
            val bitmap = BitmapFactory.decodeStream(inputStream);
            val detectResult = haarCascadeFaceDetector.detect(bitmap)
            assertTrue(detectResult != null)
        }
        scenario.onActivity {
            val inputStream = instrumentationContext.assets.open("negative-test.jpg")
            val bitmap = BitmapFactory.decodeStream(inputStream);
            val detectResult = haarCascadeFaceDetector.detect(bitmap)
            assertTrue(detectResult == null)
        }
    }
}
