package de.hsaugsburg.teamulster.sohappy.viewmodel

import androidx.lifecycle.ViewModel
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.DetectionResult
import org.dizitart.no2.objects.Id
import java.util.*
import kotlin.collections.ArrayList

/**
 * This class hold a list of DetectionResults.
 *
 * @property [timeStamp] a Date object
 * */
class MeasurementViewModel : ViewModel() {
    @Id
    val timeStamp: Date = Date()
    val results: ArrayList<DetectionResult> = ArrayList()
    var questionnaire = QuestionnaireViewModel()

    /**
     * This function adds a DetectionResult to the results list.
     *
     * @param [detectionResult] a DetectionResult object which will be stored
     * */
    fun addDetectionResult(detectionResult: DetectionResult) {
        results.add(detectionResult)
    }

    fun computePercentageString() = "%.2f".format(computePercentage())

    fun computePercentage(): Float {
        results.filter {
            detectionResult -> detectionResult.smileDetectionResult != null
        }.map {
            detectionResult -> detectionResult.smileDetectionResult?.isSmiling
        }.let { filteredResult ->
            val length = filteredResult.size
            val positiveResults = filteredResult.filter {
                it != null && it
            }.size
            return positiveResults.toFloat() / length.toFloat()
        }
    }
}
