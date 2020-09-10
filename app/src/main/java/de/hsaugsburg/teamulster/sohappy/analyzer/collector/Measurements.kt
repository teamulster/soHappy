package de.hsaugsburg.teamulster.sohappy.analyzer.collector

import de.hsaugsburg.teamulster.sohappy.analyzer.detector.DetectionResult

class Measurements {
    private val results : ArrayList<DetectionResult> = ArrayList()

    fun add(detectionResult: DetectionResult) {
        results.add(detectionResult)
    }
}