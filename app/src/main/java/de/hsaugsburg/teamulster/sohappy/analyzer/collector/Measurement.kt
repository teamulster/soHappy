package de.hsaugsburg.teamulster.sohappy.analyzer.collector

import de.hsaugsburg.teamulster.sohappy.analyzer.detector.DetectionResult

/**
 * This class hold a list of DetectionResults.
 * */
class Measurements {
    private val results : ArrayList<DetectionResult> = ArrayList()

    /**
     * This function adds a DetectionResult to the results list.
     *
     * @param [detectionResult] a DetectionResult object which will be stored
     * */
    fun add(detectionResult: DetectionResult) {
        results.add(detectionResult)
    }
}
