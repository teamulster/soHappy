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
    @Suppress("LongMethod")
    fun useStore() {
        scenario.onActivity { cameraActivity ->
            ConfigManager.store(
                cameraActivity,
                MainConfig(
                    ImageAnalyzerConfig.Builder()
                        .setFaceDetector(
                            "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".facedetectorimpl.HaarCascadeFaceDetector"
                        )
                        .setSmileDetector(
                            "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".smiledetectorimpl.FerTFLiteSmileDetector"
                        )
                        .build(),
                    AboutConfig(
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy/pulse",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy"
                    ),
                    TimerConfig(3000, 2500, 10_000, 10_000, 30_000),
                    NotificationConfig(3 * 60 * 60 * 1000),
                    RemoteConfig("https://lively.craftam.app/")
                ),
                SettingsConfig(notifications = true, databaseSync = true)
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
                        "https://github.com/teamulster/soHappy/pulse",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy"
                    ),
                    TimerConfig(3000, 2500, 10_000, 10_000, 30_000),
                    NotificationConfig.Builder()
                        .setAlarmDelay(3 * 60 * 60 * 1000)
                        .build(),
                    RemoteConfig.Builder()
                        .setURL("https://lively.craftam.app/")
                        .build()
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
                it,
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
                        "https://github.com/teamulster/soHappy/pulse",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy"
                    ),
                    TimerConfig.Builder()
                        .setBreathTimer(3000)
                        .setStimulusTimer(2500)
                        .setWaitingForSmileTimer(10_000)
                        .setWaitingForFaceTimer(10_000)
                        .setSmileTimer(30_000)
                        .build(),
                    NotificationConfig(3 * 60 * 60 * 1000),
                    RemoteConfig("https://lively.craftam.app/")
                ),
                SettingsConfig(notifications = true, databaseSync = true)
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
                    "https://github.com/teamulster/soHappy/pulse",
                    "https://github.com/teamulster/soHappy",
                    "https://github.com/teamulster/soHappy",
                    "https://github.com/teamulster/soHappy"
                ),
                TimerConfig(3000, 2500, 10_000, 10_000, 30_000),
                NotificationConfig(3 * 60 * 60 * 1000),
                RemoteConfig("https://lively.craftam.app/")
            )
            assertEquals(assertValue, loadObject)
        }
    }

    @Test
    fun useLoadClassNotFoundException() {
        scenario.onActivity {
            ConfigManager.store(
                it,
                MainConfig.Builder()
                    .setImageAnalyzerConfig(
                        ImageAnalyzerConfig(
                            "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                                ".facedetectorimpl.HaarCascadeFaceDetector",
                            "bla"
                        )
                    )
                    .setAboutConfig(
                        AboutConfig(
                            "https://github.com/teamulster/soHappy",
                            "https://github.com/teamulster/soHappy",
                            "https://github.com/teamulster/soHappy/pulse",
                            "https://github.com/teamulster/soHappy",
                            "https://github.com/teamulster/soHappy",
                            "https://github.com/teamulster/soHappy"
                        )
                    )
                    .setTimerConfig(TimerConfig(3000, 2500, 10_000, 10_000, 30_000))
                    .setNotificationConfig(NotificationConfig(3 * 60 * 60 * 1000))
                    .setRemoteConfig(RemoteConfig("https://lively.craftam.app/"))
                    .build(),
                SettingsConfig(notifications = true, databaseSync = true)
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
                it,
                MainConfig(
                    ImageAnalyzerConfig(
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                            ".facedetectorimpl.HaarCascadeFaceDetector",
                        "de.hsaugsburg.teamulster.sohappy.analyzer.detector" +
                            ".smiledetectorimpl.FerTFLiteSmileDetector"
                    ),
                    AboutConfig(
                        "https://github.com/teamulster/soHappy",
                        "hallo",
                        "https://github.com/teamulster/soHappy/pulse",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy",
                        "https://github.com/teamulster/soHappy"
                    ),
                    TimerConfig(3000, 2500, 10_000, 10_000, 30_000),
                    NotificationConfig(3 * 60 * 60 * 1000),
                    RemoteConfig("lively.craftam.app/")
                ),
                SettingsConfig(notifications = true, databaseSync = true)
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
