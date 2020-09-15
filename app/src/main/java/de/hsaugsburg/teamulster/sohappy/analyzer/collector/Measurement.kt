package de.hsaugsburg.teamulster.sohappy.analyzer.collector

import de.hsaugsburg.teamulster.sohappy.analyzer.detector.DetectionResult
import java.util.*
import kotlin.collections.ArrayList

/**
 * This class hold a list of DetectionResults.
 *
 * @property [timeStamp] a Date object
 * */
class Measurement {
    val timeStamp: Date = Date()
    private val results: ArrayList<DetectionResult> = ArrayList()

    /**
     * This function adds a DetectionResult to the results list.
     *
     * @param [detectionResult] a DetectionResult object which will be stored
     * */
    fun add(detectionResult: DetectionResult) {
        results.add(detectionResult)
    }
}
