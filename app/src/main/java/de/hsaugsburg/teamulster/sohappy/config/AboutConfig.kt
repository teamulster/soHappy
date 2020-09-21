package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores URLs to credits/privacy/imprint references.
 *
 * @property [creditsURL] a URL to credits reference
 * @property [privacyURL] a URL to privacy reference
 * @property [imprintURL] a URL to imprint reference
 * @property [licenseURL] a URL to license reference
 * @property [feedbackURL] a URL to send feedback
 * @property [issueURL] a URL to report Issue
 */
data class AboutConfig(
    val creditsURL: String,
    val privacyURL: String,
    val imprintURL: String,
    val licenseURL: String,
    val feedbackURL: String,
    val issueURL: String
)
