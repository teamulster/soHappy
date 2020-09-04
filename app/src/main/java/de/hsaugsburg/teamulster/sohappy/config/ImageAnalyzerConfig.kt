package de.hsaugsburg.teamulster.sohappy.config

import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector

class ImageAnalyzerConfig(
    val faceDetector: Class<out FaceDetector>?,
    val smileDetector: Class<out SmileDetector>?
    )
