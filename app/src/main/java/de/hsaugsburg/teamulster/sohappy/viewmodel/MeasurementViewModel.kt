package de.hsaugsburg.teamulster.sohappy.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.DetectionResult
import org.dizitart.no2.objects.Id
import java.util.*
import kotlin.collections.ArrayList
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel

/**
 * This class hold a list of DetectionResults.
 *
 * @property [timeStamp] a Date object
 * */
class MeasurementViewModel(application: Application) : AndroidViewModel(application) {
    @Id
    val timeStamp: Date = Date()
    var questionnaire = QuestionnaireViewModel()
    private val results: ArrayList<DetectionResult> = ArrayList()
    @SuppressLint("HardwareIds")
    val id: String = Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID)

    /**
     * This function adds a DetectionResult to the results list.
     *
     * @param [detectionResult] a DetectionResult object which will be stored
     * */
    fun addDetectionResult(detectionResult: DetectionResult) {
        results.add(detectionResult)
    }

    /**
     * computes Percentage using [computePercentage] and returns it as decimal with an %.
     * @return [String]
     */
    fun computePercentageString() = "%d%%".format((computePercentage() * 100).toInt())

    /**
     * computes percentage of an [MeasurementViewModel]
     * For this, all [DetectionResult]s containing an
     * [de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector.Companion.SmileDetectionResult]
     * will be counted. All SmileDetectionResults containing an Smile will be divided by all SmileDetectionResults.
     * @return [Float]
     */
    fun computePercentage(): Float {
        results.filter {
            detectionResult ->
            detectionResult.smileDetectionResult != null
        }.map {
            detectionResult ->
            detectionResult.smileDetectionResult?.isSmiling
        }.let { filteredResult ->
            val length = filteredResult.size
            val positiveResults = filteredResult.filter {
                it != null && it
            }.size
            return positiveResults.toFloat() / length.toFloat()
        }
    }
}
