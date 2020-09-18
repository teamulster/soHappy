package de.hsaugsburg.teamulster.sohappy.factories

import android.app.Activity
import android.view.InflateException
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import de.hsaugsburg.teamulster.sohappy.config.ImageAnalyzerConfig

/**
 * DetectorFactory creates Detector classes from our config classes.
 */
object DetectorFactory {
    /**
     * creates an Instance of FaceDetector from a ImageAnalyzerConfig.
     *
     * config stores the package.class string for the FaceDetector. This FaceDetector needs an
     * constructor taking just an Activity. By using reflection, an instance of this class will
     * be created.
     *
     * @param config [ImageAnalyzerConfig]
     * @param activity [Activity] this activity will mostly be used in order to get assets.
     * @throws [NoSuchMethodException]
     * @return [FaceDetector]
     */
    @Suppress("ThrowsCount")
    fun createFaceDetectorFromConfig(
        config: ImageAnalyzerConfig,
        activity: Activity
    ): FaceDetector {
        lateinit var returnValue: FaceDetector
        try {
            val constructors = Class.forName(config.faceDetector).constructors
            constructors.forEach { constructor ->
                val paramTypes = constructor.parameterTypes
                returnValue =
                    if (paramTypes.isNotEmpty() && paramTypes[0] == Activity::class.java) {
                        constructor.newInstance(activity) as FaceDetector
                    } else {
                        constructor.newInstance() as FaceDetector
                    }
            }
        } catch (e: NoSuchMethodException) {
            throw e
        } catch (e: ClassCastException) {
            throw e
        } catch (e: InflateException) {
            throw e
        } catch (e: UninitializedPropertyAccessException) {
            throw e
        }
        return returnValue
    }

    /**
     * creates an Instance of SmileDetector from a ImageAnalyzerConfig.
     *
     * config stores the package.class string for the SmileDetector. This SmileDetector needs an
     * constructor taking just an Activity. By using reflection, an instance of this class will
     * be created.
     *
     * @param config [ImageAnalyzerConfig]
     * @param activity [Activity] this activity will mostly be used in order to get assets.
     * @throws [NoSuchMethodException]
     * @return [SmileDetector]
     */
    @Suppress("ThrowsCount")
    fun createSmileDetectorFromConfig(
        config: ImageAnalyzerConfig,
        activity: Activity
    ): SmileDetector {
        lateinit var returnValue: SmileDetector
        try {
            val constructors = Class.forName(config.smileDetector).constructors
            constructors.forEach { constructor ->
                val paramTypes = constructor.parameterTypes
                returnValue =
                    if (paramTypes.isNotEmpty() && paramTypes[0] == Activity::class.java) {
                        constructor.newInstance(activity) as SmileDetector
                    } else {
                        constructor.newInstance() as SmileDetector
                    }
            }
        } catch (e: NoSuchMethodException) {
            throw e
        } catch (e: ClassCastException) {
            throw e
        } catch (e: InflateException) {
            throw e
        } catch (e: UninitializedPropertyAccessException) {
            throw e
        }
        return returnValue
    }
}
