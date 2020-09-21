package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores a ImageAnalyzerConfig.
 *
 * @property faceDetector a package string to the selected faceDetectorImpl
 * @property smileDetector a package string to the selected smileDetectorImpl
 */
data class ImageAnalyzerConfig(
    val faceDetector: String,
    val smileDetector: String
) {
    constructor(builder: Builder) : this(builder.faceDetector, builder.smileDetector)

    /**
     * This class implements a [Builder] for [ImageAnalyzerConfig].
     * */
    class Builder(
        var faceDetector: String = "",
        var smileDetector: String = ""
    ) {
        /**
         * This function sets a value for [faceDetector].
         * It is mandatory to call this function before building.
         *
         * @param [faceDetector]
         * */
        fun setFaceDetector(faceDetector: String) = apply { this.faceDetector = faceDetector }

        /**
         * This function sets a value for [smileDetector].
         * It is mandatory to call this function before building.
         *
         * @param [smileDetector]
         * */
        fun setSmileDetector(smileDetector: String) = apply { this.smileDetector = smileDetector }

        /**
         * This function calls the [ImageAnalyzerConfig] constructor.
         *
         * @return [ImageAnalyzerConfig] instance
         * */
        fun build() = ImageAnalyzerConfig(faceDetector, smileDetector)
    }
}
