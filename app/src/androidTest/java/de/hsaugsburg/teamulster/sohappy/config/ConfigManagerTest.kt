package de.hsaugsburg.teamulster.sohappy.config

import android.util.MalformedJsonException
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import de.hsaugsburg.teamulster.sohappy.MainActivity
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileInputStream
import java.net.MalformedURLException
import java.nio.charset.Charset
import kotlin.test.assertFailsWith

@RunWith(AndroidJUnit4::class)
class ConfigManagerTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = launchActivity<MainActivity>()
    }

    @Test
    fun useStore() {
        scenario.onActivity { cameraActivity ->
            ConfigManager.store(
                cameraActivity, MainConfig(
                    ImageAnalyzerConfig(
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".facedetectorimpl.HaarCascadeFaceDetector",
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".smiledetectorimpl.FerTFLiteSmileDetector"
                    ),
                    AboutConfig(
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy/pulse"
                    ),
                    TimerConfig(3000, 2500, 10_000, 30_000)
                ), SettingsConfig(notifications = true, databaseSync = true)
            )
            val dirPath = cameraActivity.filesDir
            val configDirectory = File(dirPath, "config")
            val file = File(configDirectory, "config.json")
            val inString = FileInputStream(file).bufferedReader().use { it.readText() }
            val assertObject = Gson().toJson(
                MainConfig(
                    ImageAnalyzerConfig(
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".facedetectorimpl.HaarCascadeFaceDetector",
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".smiledetectorimpl.FerTFLiteSmileDetector"
                    ),
                    AboutConfig(
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy/pulse"
                    ),
                    TimerConfig(3000, 2500, 10_000, 30_000)
                )
            )
            assertEquals(
                assertObject,
                inString
            )
        }
    }

    @Test
    fun useLoad() {
        scenario.onActivity {
            ConfigManager.store(
                it, MainConfig(
                    ImageAnalyzerConfig(
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".facedetectorimpl.HaarCascadeFaceDetector",
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".smiledetectorimpl.FerTFLiteSmileDetector"
                    ),
                    AboutConfig(
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy/pulse"
                    ),
                    TimerConfig(3000, 2500, 10_000, 30_000)
                ), SettingsConfig(notifications = true, databaseSync = true)
            )
            val loadObject = ConfigManager.load(it)
            val assertValue = MainConfig(
                ImageAnalyzerConfig(
                    "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                            ".facedetectorimpl.HaarCascadeFaceDetector",
                    "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                            ".smiledetectorimpl.FerTFLiteSmileDetector"
                ),
                AboutConfig(
                    "https://github.com/teamulster/soHappy",
                    "https://github.com/teamulster/soHappy",
                    "https://github.com/teamulster/soHappy/pulse"
                ),
                TimerConfig(3000, 2500, 10_000, 30_000)
            )
            assertEquals(assertValue, loadObject)
        }
    }

    @Test
    fun useLoadClassNotFoundException() {
        scenario.onActivity {
            ConfigManager.store(
                it, MainConfig(
                    ImageAnalyzerConfig(
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".facedetectorimpl.HaarCascadeFaceDetector",
                        "bla"
                    ),
                    AboutConfig(
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy/pulse"
                    ),
                    TimerConfig(3000, 2500, 10_000, 30_000)
                ), SettingsConfig(notifications = true, databaseSync = true)
            )
            assertFailsWith(ClassNotFoundException::class) {
                ConfigManager.load(it)
            }
        }
    }

    @Test
    fun useLoadMalformedURLExceptionLoad() {
        scenario.onActivity {
            ConfigManager.store(
                it, MainConfig(
                    ImageAnalyzerConfig(
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".facedetectorimpl.HaarCascadeFaceDetector",
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".smiledetectorimpl.FerTFLiteSmileDetector"
                    ),
                    AboutConfig(
                        "https://github.com/teamulster/soHappy",
                        "hallo",
                        "https://github.com/teamulster/soHappy/pulse"
                    ),
                    TimerConfig(3000, 2500, 10_000, 30_000)
                ), SettingsConfig(notifications = true, databaseSync = true)
            )
            assertFailsWith(MalformedURLException::class) {
                ConfigManager.load(it)
            }
        }
    }

    @Test
    fun useLoadMalformedJsonException() {
        scenario.onActivity { activity ->
            val dirPath = activity.filesDir
            val configDirectory = File(dirPath, "config")
            configDirectory.mkdirs()
            val mainFile = File(configDirectory, "config.json")
            mainFile.writeText("No JSON", Charset.defaultCharset())
            assertFailsWith(MalformedJsonException::class) {
                ConfigManager.load(activity).toString()
            }
            val settingsFile = File(configDirectory, "settingsConfig.json")
            settingsFile.writeText("No JSON", Charset.defaultCharset())
            assertFailsWith(MalformedJsonException::class) {
                ConfigManager.load(activity).toString()
            }
        }
    }
}
