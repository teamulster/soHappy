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
object ConfigManager {
    // Init variables
    lateinit var imageAnalyzerConfig: ImageAnalyzerConfig
    lateinit var aboutConfig: AboutConfig
    lateinit var mainConfig: MainConfig
    private val gson = Gson()
    @Suppress("StringLiteralDuplication")
    private val defaultConfig: MainConfig = MainConfig(
        ImageAnalyzerConfig(
            "de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.HaarCascadeFaceDetector",
            "de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl.FerTFLiteSmileDetectorImpl"
        ),
        AboutConfig(
            "https://github.com/teamulster/soHappy",
            "https://github.com/teamulster/soHappy",
            "https://github.com/teamulster/soHappy"
        )
    )

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
        val jsonString: String
        try {
            val file = getFile(context)
            jsonString = FileInputStream(file).bufferedReader().use { it.readText() }

            val parsedJson = fromJson(jsonString)
            checkAboutConfig(parsedJson.aboutConfig)
            checkImageAnalyzerConfig(parsedJson.imageAnalyzerConfig)
            mainConfig = parsedJson
            imageAnalyzerConfig = parsedJson.imageAnalyzerConfig
            aboutConfig = parsedJson.aboutConfig
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
    fun store(context: Context, mainConfig: MainConfig) {
        val jsonString = toJson(mainConfig)
        val file = getFile(context)
        try {
            file.writeText(jsonString, Charset.defaultCharset())
        } catch (e: IOException) {
            throw e
        }
    }

    /**
     * This private function returns the current config.json file or creates it, if it does'nt exist.
     *
     * @param [context] the current context
     * @throws [IOException]
     * @return [File]
     * */
    private fun getFile(context: Context): File {
        try {
            val dirPath = context.filesDir
            val configDirectory = File(dirPath, "config")
            configDirectory.mkdirs()
            val file = File(configDirectory, "config.json")
            if (!file.exists()) {
                file.writeText(toJson(defaultConfig), Charset.defaultCharset())
            }
            return file
        } catch (e: IOException) {
            throw e
        }
    }

    /**
     * This private function parses a given JSON string into a MainConfig object. The object is stored
     * in class attributes mainConfig (imageAnalyzerConfig).
     *
     * @param [jsonString] JSON string which will be converted into MainConfig object
     * @throws [MalformedJsonException]
     * @return [MainConfig]
     * */
    private fun fromJson(jsonString: String): MainConfig {
        try {
            return gson.fromJson<MainConfig>(jsonString, MainConfig::class.java)
        } catch (e: JsonParseException) {
            throw MalformedJsonException(
                this::class.java.name + " : the given json string" +
                        "is not JSON compliant."
            )
        }
    }

    /**
     * This private function parses a given MainConfig object into a JSON string. This string will
     * be returned.
     *
     * @param [mainConfig] MainConfig object which will be parsed to JSON
     * @return [String]
     * */
    private fun toJson(mainConfig: MainConfig): String = gson.toJson(mainConfig)

    /**
     * This private function checks a given URL URL compliance.
     *
     * @param [url] a String which will be checked
     * @return [Boolean]
     * */
    private fun isURLValid(url: String): Boolean = URLUtil.isValidUrl(url)
            && (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url))

    /**
     * This private function checks a given AboutConfig object for its property value compliance.
     *
     * @param [aboutConfig] a given AboutConfig object which will be checked
     * @throws [MalformedURLException]
     */
    @Suppress("ThrowsCount")
    private fun checkAboutConfig(aboutConfig: AboutConfig) {
        val optRecommendation = "Make sure http:// or https:// is prepended."
        if (!isURLValid(aboutConfig.creditsURL)) {
            throw MalformedURLException(
                this::class.java.name
                        + " : aboutConfig.creditsURL is not properly formatted."
                        + "\n" + optRecommendation
            )
        }
        if (!isURLValid(aboutConfig.privacyURL)) {
            throw MalformedURLException(
                this::class.java.name
                        + " : aboutConfig.privacyURL is not properly formatted"
                        + "\n" + optRecommendation
            )
        }
        if (!isURLValid(aboutConfig.imprintURL)) {
            throw MalformedURLException(
                this::class.java.name
                        + " : aboutConfig.imprintURL is not properly formatted"
                        + "\n" + optRecommendation
            )
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
            throw e
        }
    }
}
