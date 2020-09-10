package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores URLs to credits/privacy/imprint references.
 *
 * @property [creditsURL] a URL to credits reference
 * @property [privacyURL] a URL to privacy reference
 * @property [imprintURL] a URL to imprint reference
 */
data class AboutConfig(val creditsURL: String, val privacyURL: String, val imprintURL: String)
