package de.hsaugsburg.teamulster.sohappy.config

import de.hsaugsburg.teamulster.sohappy.config.NotificationConfig.Builder

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
) {
    constructor(builder: Builder) : this(
        builder.creditsURL,
        builder.privacyURL,
        builder.imprintURL,
        builder.licenseURL,
        builder.feedbackURL,
        builder.issueURL
    )

    /**
     * This class implements a [Builder] for [AboutConfig].
     * */
    class Builder(
        var creditsURL: String = "",
        var privacyURL: String = "",
        var imprintURL: String = "",
        var licenseURL: String = "",
        var feedbackURL: String = "",
        var issueURL: String = ""
    ) {
        /**
         * This function sets a value for [creditsURL].
         * It is mandatory to call this function before building.
         *
         * @param [creditsURL]
         * */
        fun setCreditsURL(creditsURL: String) = apply { this.creditsURL = creditsURL }

        /**
         * This function sets a value for [privacyURL].
         * It is mandatory to call this function before building.
         *
         * @param [privacyURL]
         * */
        fun setPrivacyURL(privacyURL: String) = apply { this.privacyURL = privacyURL }

        /**
         * This function sets a value for [imprintURL].
         * It is mandatory to call this function before building.
         *
         * @param [imprintURL]
         * */
        fun setImprintURL(imprintURL: String) = apply { this.imprintURL = imprintURL }

        /**
         * This function sets a value for [licenseURL].
         * It is mandatory to call this function before building.
         *
         * @param [licenseURL]
         * */
        fun setLicenseURL(licenseURL: String) = apply { this.licenseURL = licenseURL }

        /**
         * This function sets a value for [feedbackURL].
         * It is mandatory to call this function before building.
         *
         * @param [feedbackURL]
         * */
        fun setFeedbackURL(feedbackURL: String) = apply { this.feedbackURL = feedbackURL }

        /**
         * This function sets a value for [issueURL].
         * It is mandatory to call this function before building.
         *
         * @param [issueURL]
         * */
        fun setIssueURL(issueURL: String) = apply { this.issueURL = issueURL }

        /**
         * This function calls the [AboutConfig] constructor.
         *
         * @return [AboutConfig] instance
         * */
        fun build(): AboutConfig = AboutConfig(creditsURL, privacyURL, imprintURL, licenseURL, feedbackURL, issueURL)
    }
}
