package de.hsaugsburg.teamulster.sohappy

import android.content.Context
import android.graphics.BitmapFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.HaarCascadeFaceDetector
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HaarCascadeFaceDetectorTest {
    lateinit var haarCascadeFaceDetector: HaarCascadeFaceDetector
    private lateinit var scenario : ActivityScenario<CameraActivity>
    private val instrumentationContext: Context = InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        scenario = launchActivity<CameraActivity>()
        scenario.onActivity { haarCascadeFaceDetector = HaarCascadeFaceDetector(it) }
    }

    @After
    fun tearDown() {

    }

    @Test
    fun detectTest() {
        scenario.onActivity {
            // TODO: Rename files and choose better files
            val istream = instrumentationContext.assets.open("test.jpg")
            val bitmap = BitmapFactory.decodeStream(istream);
            val detectResult = haarCascadeFaceDetector.detect(bitmap)
            assert(detectResult != null)
        }
        scenario.onActivity {
            val istream = instrumentationContext.assets.open("negative-test.jpg")
            val bitmap = BitmapFactory.decodeStream(istream);
            val detectResult = haarCascadeFaceDetector.detect(bitmap)
            assert(detectResult == null)
        }
    }
}