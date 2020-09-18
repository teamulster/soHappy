package de.hsaugsburg.teamulster.sohappy.factories

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.GoogleMLKitAPIFaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.HaarCascadeFaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.FerTFLiteSmileDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.GoogleMLKitAPISmileDetector
import de.hsaugsburg.teamulster.sohappy.config.ImageAnalyzerConfig
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DetectorFactoryTest {
    private val imageAnalyzerConfig = ImageAnalyzerConfig(
        "de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.HaarCascadeFaceDetector",
        "de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.FerTFLiteSmileDetector"
    )
    private val imageAnalyzerConfigGoogle = ImageAnalyzerConfig(
        "de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.GoogleMLKitAPIFaceDetector",
        "de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.GoogleMLKitAPISmileDetector"
    )
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = launchActivity<MainActivity>()
    }

    @Test
    fun useCreateFaceDetectorFromConfig() {
        scenario.onActivity {
            val faceDetectorInstance =
                DetectorFactory.createFaceDetectorFromConfig(
                    imageAnalyzerConfig,
                    it
                ) as HaarCascadeFaceDetector
            val faceDetectorInstanceGoogle =
                DetectorFactory.createFaceDetectorFromConfig(imageAnalyzerConfigGoogle, it)
                        as GoogleMLKitAPIFaceDetector
            assertEquals(HaarCascadeFaceDetector::class.java, faceDetectorInstance.javaClass)
            assertEquals(
                GoogleMLKitAPIFaceDetector::class.java,
                faceDetectorInstanceGoogle.javaClass
            )
        }
    }

    @Test
    fun useCreateSmileDetectorFromConfig() {
        scenario.onActivity {
            val smileDetectorInstance =
                DetectorFactory.createSmileDetectorFromConfig(
                    imageAnalyzerConfig,
                    it
                ) as FerTFLiteSmileDetector
            val smileDetectorInstanceGoogle =
                DetectorFactory.createSmileDetectorFromConfig(imageAnalyzerConfigGoogle, it)
                        as GoogleMLKitAPISmileDetector
            assertEquals(FerTFLiteSmileDetector::class.java, smileDetectorInstance.javaClass)
            assertEquals(
                GoogleMLKitAPISmileDetector::class.java,
                smileDetectorInstanceGoogle.javaClass
            )
        }
    }
}
