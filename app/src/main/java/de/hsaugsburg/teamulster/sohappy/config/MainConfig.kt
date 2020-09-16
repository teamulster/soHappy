package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores the MainConfig.
 *
 * @property [imageAnalyzerConfig]
 * @property [aboutConfig]
 * @property [timerConfig]
 */
data class MainConfig(
    val imageAnalyzerConfig: ImageAnalyzerConfig, val aboutConfig: AboutConfig,
    val timerConfig: TimerConfig
)
