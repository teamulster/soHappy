package de.hsaugsburg.teamulster.sohappy.config

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileInputStream

@RunWith(AndroidJUnit4::class)
class ConfigManagerTest {
    private lateinit var scenario: ActivityScenario<CameraActivity>

    @Before
    fun setUp() {
        scenario = launchActivity<CameraActivity>()
    }

    @Test
    fun useWriteSuccessful() {
        scenario.onActivity { activity ->
            ConfigManager.store(
                activity, MainConfig(
                    ImageAnalyzerConfig(
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl",
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector.TFLiteImpl"
                    )
                )
            )
            val dirPath = activity.filesDir
            val configDirectory = File(dirPath, "config")
            val file = File(configDirectory, "config.json")
            val inString = FileInputStream(file).bufferedReader().use { it.readText() }
            val assertValues = Gson().toJson(
                MainConfig(
                    ImageAnalyzerConfig(
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl",
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector.TFLiteImpl"
                    )
                )
            )
            assertEquals(
                assertValues,
                inString
            )
        }
    }

    @Test
    fun useLoad() {
        scenario.onActivity {
            ConfigManager.store(it, MainConfig(ImageAnalyzerConfig("", "bla")))
            ConfigManager.load(it)
            assertEquals(
                "",
                ConfigManager.mainConfig.imageAnalyzerConfig.faceDetector.toString()
            )
            assertEquals(
                "bla",
                ConfigManager.mainConfig.imageAnalyzerConfig.smileDetector.toString()
            )
        }
    }
}
