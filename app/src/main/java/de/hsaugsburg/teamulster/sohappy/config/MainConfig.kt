package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores the MainConfig.
 *
 * @property [imageAnalyzerConfig]
 * @property [aboutConfig]
 * @property [timerConfig]
 */
data class MainConfig(
    val imageAnalyzerConfig: ImageAnalyzerConfig,
    val aboutConfig: AboutConfig,
    val timerConfig: TimerConfig,
    val notificationConfig: NotificationConfig
) {
    constructor(builder: Builder) : this(
        builder.imageAnalyzerConfig!!,
        builder.aboutConfig!!,
        builder.timerConfig!!,
        builder.notificationConfig!!
    )

    /**
     * This class implements a [Builder] for [MainConfig].
     * */
    class Builder(
        var imageAnalyzerConfig: ImageAnalyzerConfig? = null,
        var aboutConfig: AboutConfig? = null,
        var timerConfig: TimerConfig? = null,
        var notificationConfig: NotificationConfig? = null
    ) {
        /**
         * This function sets a value for [imageAnalyzerConfig].
         * It is mandatory to call this function before building.
         *
         * @param [imageAnalyzerConfig]
         * */
        fun setImageAnalyzerConfig(imageAnalyzerConfig: ImageAnalyzerConfig) =
            apply { this.imageAnalyzerConfig = imageAnalyzerConfig }

        /**
         * This function sets a value for [aboutConfig].
         * It is mandatory to call this function before building.
         *
         * @param [aboutConfig]
         * */
        fun setAboutConfig(aboutConfig: AboutConfig) = apply { this.aboutConfig = aboutConfig }

        /**
         * This function sets a value for [timerConfig].
         * It is mandatory to call this function before building.
         *
         * @param [timerConfig]
         * */
        fun setTimerConfig(timerConfig: TimerConfig) = apply { this.timerConfig = timerConfig }

        /**
         * This function sets a value for [notificationConfig].
         * It is mandatory to call this function before building.
         *
         * @param [notificationConfig]
         * */
        fun setNotificationConfig(notificationConfig: NotificationConfig) =
            apply { this.notificationConfig = notificationConfig }

        /**
         * This function calls the [MainConfig] constructor.
         *
         * @return [MainConfig] instance
         * */
        fun build() = MainConfig(imageAnalyzerConfig!!, aboutConfig!!, timerConfig!!, notificationConfig!!)
    }
}
