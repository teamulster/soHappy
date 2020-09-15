package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores the MainConfig.
 *
 * @property [imageAnalyzerConfig]
 * @property [aboutConfig]
 * @property [secondsConfig]
 */
data class MainConfig(
    val imageAnalyzerConfig: ImageAnalyzerConfig, val aboutConfig: AboutConfig,
    val secondsConfig: SecondsConfig
)
