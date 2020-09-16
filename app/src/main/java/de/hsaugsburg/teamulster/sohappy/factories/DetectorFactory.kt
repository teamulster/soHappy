package de.hsaugsburg.teamulster.sohappy.factories

import android.app.Activity
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
     * @return [FaceDetector]
     */
    fun getFaceDetectorFromConfig(config: ImageAnalyzerConfig, activity: Activity): FaceDetector {
        return Class.forName(config.faceDetector)
            .getConstructor(Activity::class.java)
            .newInstance(activity) as FaceDetector
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
     * @return [SmileDetector]
     */
    fun getSmileDetectorFromConfig(config: ImageAnalyzerConfig, activity: Activity): SmileDetector {
        return Class.forName(config.smileDetector).getConstructor(Activity::class.java)
            .newInstance(activity) as SmileDetector
    }
}
