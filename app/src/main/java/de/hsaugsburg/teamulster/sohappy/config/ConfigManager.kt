package de.hsaugsburg.teamulster.sohappy.config

import android.content.Context
import android.util.MalformedJsonException
import android.webkit.URLUtil
import com.google.gson.Gson
import com.google.gson.JsonParseException
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.net.MalformedURLException
import java.nio.charset.Charset

/**
 * This object parses configuration based on config.json.
 * config.json is placed in internal storage and is only accessible from within the app (app-specific)
 * storage.
 * */
@Suppress("TooManyFunctions")
object ConfigManager {
    // Init variables
    lateinit var imageAnalyzerConfig: ImageAnalyzerConfig
    lateinit var aboutConfig: AboutConfig
    lateinit var timerConfig: TimerConfig
    lateinit var notificationConfig: NotificationConfig
    lateinit var remoteConfig: RemoteConfig
    lateinit var mainConfig: MainConfig
    lateinit var settingsConfig: SettingsConfig
    private val gson = Gson()

    @Suppress("StringLiteralDuplication")
    private val defaultMainConfig: MainConfig = MainConfig.Builder()
        .setImageAnalyzerConfig(
            ImageAnalyzerConfig(
                "de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.HaarCascadeFaceDetector",
                "de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.SoHappyTFLiteSmileDetector"
            )
        )
        .setAboutConfig(
            AboutConfig(
                "https://github.com/teamulster/soHappy",
                "https://github.com/teamulster/soHappy",
                "https://github.com/teamulster/soHappy",
                "https://github.com/teamulster/soHappy",
                "https://github.com/teamulster/soHappy",
                "https://github.com/teamulster/soHappy"
            )
        )
        .setTimerConfig(TimerConfig(3000, 2500, 10_000, 10_000, 30_000))
        .setNotificationConfig(NotificationConfig(3 * 60 * 60 * 1000))
        .setRemoteConfig(RemoteConfig("https://lively.craftam.app/"))
        .build()
    private val defaultSettingsConfig = SettingsConfig.Builder()
        .setNotifications(true)
        .setDatabaseSync(true)
        .build()

    /**
     * This function loads config from the config file and parses the JSON string to MainConfig(
     * ImageAnalyzerConfig) objects.
     *
     * @param [context] current context describing from where the method was invoked
     * @throws [IOException]
     * @throws [ClassNotFoundException]
     * @return [MainConfig]
     * */
    fun load(context: Context): MainConfig {
        val mainJsonString: String
        val settingsJsonString: String
        try {
            val settingsFile = getFile(context)[0]
            settingsJsonString =
                FileInputStream(settingsFile).bufferedReader().use { it.readText() }
            val mainFile = getFile(context)[1]
            mainJsonString = FileInputStream(mainFile).bufferedReader().use { it.readText() }

            val parsedSettingsJson = fromJsonToSettings(settingsJsonString)
            settingsConfig = parsedSettingsJson

            val parsedMainJson = fromJsonToMain(mainJsonString)
            checkAboutConfig(parsedMainJson.aboutConfig)
            checkImageAnalyzerConfig(parsedMainJson.imageAnalyzerConfig)
            checkRemoteConfig(parsedMainJson.remoteConfig)
            mainConfig = parsedMainJson
            imageAnalyzerConfig = parsedMainJson.imageAnalyzerConfig
            aboutConfig = parsedMainJson.aboutConfig
            timerConfig = parsedMainJson.timerConfig
            notificationConfig = parsedMainJson.notificationConfig
            remoteConfig = parsedMainJson.remoteConfig
        } catch (e: IOException) {
            throw e
        } catch (e: ClassNotFoundException) {
            throw e
        }
        return mainConfig
    }

    /**
     * This function stores the MainConfig object it is handed into the config.json file.
     *
     * @param [context] current context describing from where the method was invoked
     * @param [mainConfig] MainConfig object which contents will be stored
     * @throws [IOException]
     * */
    fun store(context: Context, mainConfig: MainConfig, settingsConfig: SettingsConfig) {
        val mainJsonString = toJson(mainConfig)
        val settingsJsonString = toJson(settingsConfig)
        try {
            val settingsFile = getFile(context)[0]
            settingsFile.writeText(settingsJsonString, Charset.defaultCharset())
            val mainFile = getFile(context)[1]
            mainFile.writeText(mainJsonString, Charset.defaultCharset())
        } catch (e: IOException) {
            throw e
        }
    }

    /**
     * This function restores the default settings/main config.
     *
     * @param [context]
     * */
    fun restoreDefaults(context: Context) {
        val defaultMainJsonString = toJson(defaultMainConfig)
        val defaultSettingsJsonString = toJson(defaultSettingsConfig)
        try {
            val settingsFile = getFile(context)[0]
            settingsFile.writeText(defaultSettingsJsonString, Charset.defaultCharset())
            val mainFile = getFile(context)[1]
            mainFile.writeText(defaultMainJsonString, Charset.defaultCharset())
        } catch (e: IOException) {
            throw e
        }
    }

    /**
     * This private function returns the current config.json/settingsConfig.json file or creates it,
     * if it does not exist.
     *
     * @param [context] the current context
     * @throws [IOException]
     * @return [ArrayList]<File>
     * */
    private fun getFile(context: Context): ArrayList<File> {
        try {
            val dirPath = context.filesDir
            val configDirectory = File(dirPath, "config")
            configDirectory.mkdirs()
            val settingsFile = File(configDirectory, "settingsConfig.json")
            val mainFile = File(configDirectory, "config.json")
            if (!mainFile.exists()) {
                mainFile.writeText(toJson(defaultMainConfig), Charset.defaultCharset())
            }
            if (!settingsFile.exists()) {
                settingsFile.writeText(toJson(defaultSettingsConfig), Charset.defaultCharset())
            }
            return arrayListOf<File>(settingsFile, mainFile)
        } catch (e: IOException) {
            throw e
        }
    }

    /**
     * This private function parses a given JSON string into a [MainConfig] object. The object is stored
     * in class attributes [mainConfig] ([imageAnalyzerConfig]).
     *
     * @param [jsonString] JSON string which will be converted into MainConfig object
     * @throws [MalformedJsonException]
     * @return [MainConfig]
     * */
    internal fun fromJsonToMain(jsonString: String): MainConfig {
        try {
            return gson.fromJson<MainConfig>(jsonString, MainConfig::class.java)
        } catch (e: JsonParseException) {
            throw MalformedJsonException(
                this::class.java.name + " : the given json string" +
                    " is not JSON compliant."
            )
        }
    }

    /**
     * This private function parses a given JSON string into a [SettingsConfig] object. The object is stored
     * in class attributes [settingsConfig].
     *
     * @param [jsonString] JSON string which will be converted into MainConfig object
     * @throws [MalformedJsonException]
     * @return [SettingsConfig]
     * */
    private fun fromJsonToSettings(jsonString: String): SettingsConfig {
        try {
            return gson.fromJson<SettingsConfig>(jsonString, SettingsConfig::class.java)
        } catch (e: JsonParseException) {
            throw MalformedJsonException(
                this::class.java.name + " : the given json string" +
                    " is not JSON compliant."
            )
        }
    }

    /**
     * This private function parses a given [MainConfig] object into a JSON string. This string will
     * be returned.
     *
     * @param [config] [MainConfig] object which will be parsed to JSON
     * @return [String]
     * */
    private fun toJson(config: MainConfig): String = gson.toJson(config)

    /**
     * This private function parses a given [SettingsConfig] object into a JSON string. This string will
     * be returned.
     *
     * @param [config] [SettingsConfig] object which will be parsed to JSON
     * @return [String]
     * */
    private fun toJson(config: SettingsConfig): String = gson.toJson(config)

    /**
     * This private function checks a given URL compliance.
     *
     * @param [url] a String which will be checked
     * @return [Boolean]
     * */
    private fun isURLValid(url: String): Boolean = URLUtil.isValidUrl(url) &&
        (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url))

    /**
     * This private function checks a given AboutConfig object for its property value compliance.
     *
     * @param [aboutConfig] a given AboutConfig object which will be checked
     * @throws [MalformedURLException]
     */
    private fun checkAboutConfig(aboutConfig: AboutConfig) {
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
     * This private function checks a given ImageAnalyzerConfig object for its property value compliance.
     *
     * @param [imageAnalyzerConfig]
     * @throws [ClassNotFoundException]
     * */
    private fun checkImageAnalyzerConfig(imageAnalyzerConfig: ImageAnalyzerConfig) {
        try {
            Class.forName(imageAnalyzerConfig.faceDetector)
            Class.forName(imageAnalyzerConfig.smileDetector)
        } catch (e: ClassNotFoundException) {
            throw ClassNotFoundException("Invalid package path name : " + e.message)
        }
    }

    /**
     * This private function checks a given RemoteConfig object for its property value compliance.
     *
     * @param [remoteConfig] a given RemoteConfig object which will be checked
     * @throws [MalformedURLException]
     */
    private fun checkRemoteConfig(remoteConfig: RemoteConfig) {
        val optRecommendation = "Make sure http:// or https:// is prepended."
        if (!isURLValid(remoteConfig.url)) {
            throw MalformedURLException(
                "remoteConfig.url is not properly formatted" +
                    "\n" + optRecommendation
            )
        }
    }
}
