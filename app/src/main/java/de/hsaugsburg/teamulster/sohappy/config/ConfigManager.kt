package de.hsaugsburg.teamulster.sohappy.config

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParseException
import de.hsaugsburg.teamulster.sohappy.exceptions.InvalidJsonStringException
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.charset.Charset


/**
* This object parses configuration based on config.json.
* config.json is placed in internal storage and is only accessible from within the app (app-specific)
* storage.
* */
object ConfigManager {
    // Init variables
    lateinit var imageAnalyzerConfig: ImageAnalyzerConfig
    lateinit var mainConfig: MainConfig
    private var file: File? = null
    private val gson = Gson()
    private val defaultConfig: MainConfig = MainConfig(
        ImageAnalyzerConfig(
            "de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl",
            "de.hsaugsburg.teamulster.sohappy.analyzer.detector.TFLiteImpl"
        )
    )

    /**
    * This function loads config from the config file and parses the JSON string to MainConfig(
    * ImageAnalyzerConfig) objects.
    *
    * @param context current context describing from where the method was invoked
    * @throws IOException
    * @return MainConfig
    * */
    fun load(context: Context): MainConfig {
        val jsonString: String
        try {
            if (file == null || !file?.exists()!!) {
                createConfigFile(context)
            }
            jsonString = FileInputStream(file).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            throw e
        }
        fromJson(jsonString)
        return mainConfig
    }

    /**
    * This function stores the MainConfig object it is handed into the config.json file.
    *
    * @param context current context describing from where the method was invoked
    * @param mainConfig MainConfig object which contents will be stored
    * @throws IOException
    * */
    fun store(context: Context, mainConfig: MainConfig) {
        val jsonString = toJson(mainConfig)
        try {
            if (file == null || !file?.exists()!!) {
                createConfigFile(context)
            }
            file!!.writeText(jsonString, Charset.defaultCharset())
        } catch (e: IOException) {
            throw e
        }
    }

    /**
    * This private function creates a config.json inside a config folder
    *
    * @param context current context describing from where the method was invoked
    * @throws IOException
    * */
    private fun createConfigFile(context: Context) {
        try {
            val dirPath = context.filesDir
            val configDirectory = File(dirPath, "config")
            configDirectory.mkdirs()
            file = File(configDirectory, "config.json")
            file!!.writeText(toJson(defaultConfig), Charset.defaultCharset())
        } catch (e : IOException) {
            throw e
        }
    }

    /**
    * This private function parses a given JSON string into a MainConfig object. The object is stored
    * in class attributes mainConfig (imageAnalyzerConfig)
    *
    * @param jsonString JSON string which will be converted into MainConfig object
    * @throws InvalidJsonStringException
    * */
    private fun fromJson(jsonString: String) {
        try {
            mainConfig = gson.fromJson<MainConfig>(jsonString, MainConfig::class.java)
            imageAnalyzerConfig = mainConfig.imageAnalyzerConfig
        } catch (e: JsonParseException) {
            throw InvalidJsonStringException(this::class.java.name + " : the given json string" +
                    "is not JSON compliant.")
        }
    }

    /**
    * This private function parses a given MainConfig object into a JSON string. This string will
    * be returned.
    *
    * @param mainConfig MainConfig object which will be parsed to JSON
    * @return String
    * */
    private fun toJson(mainConfig: MainConfig): String {
        return gson.toJson(mainConfig)
    }
}
