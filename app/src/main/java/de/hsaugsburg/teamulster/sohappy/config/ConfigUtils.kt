package de.hsaugsburg.teamulster.sohappy.config

import android.webkit.URLUtil
import java.net.MalformedURLException

/**
 * This object can be used for config validation.
 * */
object ConfigUtils {
    /**
     * This function checks a given URL compliance.
     *
     * @param [url] a String which will be checked
     * @return [Boolean]
     * */
    fun isURLValid(url: String): Boolean = URLUtil.isValidUrl(url) &&
        (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url))

    /**
     * This function checks a given AboutConfig object for its property value compliance.
     *
     * @param [aboutConfig] a given AboutConfig object which will be checked
     * @throws [MalformedURLException]
     */
    fun checkAboutConfig(aboutConfig: AboutConfig) {
        val optRecommendation = "Make sure http:// or https:// is prepended."
        var errorString = ""
        if (!isURLValid(aboutConfig.creditsURL)) {
            errorString = "aboutConfig.creditsURL is not properly formatted." +
                "\n" + optRecommendation
        }
        if (!isURLValid(aboutConfig.privacyURL)) {
            errorString = "aboutConfig.privacyURL is not properly formatted." +
                "\n" + optRecommendation
        }
        if (!isURLValid(aboutConfig.imprintURL)) {
            errorString = "aboutConfig.imprintURL is not properly formatted." +
                "\n" + optRecommendation
        }
        if (!isURLValid(aboutConfig.licenseURL)) {
            errorString = "aboutConfig.licenseURL is not properly formatted." +
                "\n" + optRecommendation
        }
        if (!isURLValid(aboutConfig.feedbackURL)) {
            errorString = "aboutConfig.feedbackURL is not properly formatted." +
                "\n" + optRecommendation
        }
        if (!isURLValid(aboutConfig.issueURL)) {
            errorString = "aboutConfig.issueURL is not properly formatted." +
                "\n" + optRecommendation
        }
        if (errorString != "") {
            throw MalformedURLException(errorString)
        }
    }

    /**
     * This function checks a given ImageAnalyzerConfig object for its property value compliance.
     *
     * @param [imageAnalyzerConfig]
     * @throws [ClassNotFoundException]
     * */
    fun checkImageAnalyzerConfig(imageAnalyzerConfig: ImageAnalyzerConfig) {
        try {
            Class.forName(imageAnalyzerConfig.faceDetector)
            Class.forName(imageAnalyzerConfig.smileDetector)
        } catch (e: ClassNotFoundException) {
            throw ClassNotFoundException("Invalid package path name : " + e.message)
        }
    }

    /**
     * This function checks a given RemoteConfig object for its property value compliance.
     *
     * @param [remoteConfig] a given RemoteConfig object which will be checked
     * @throws [MalformedURLException]
     */
    fun checkRemoteConfig(remoteConfig: RemoteConfig) {
        val optRecommendation = "Make sure http:// or https:// is prepended."
        if (!isURLValid(remoteConfig.url)) {
            throw MalformedURLException(
                "remoteConfig.url is not properly formatted" +
                    "\n" + optRecommendation
            )
        }
    }
}
