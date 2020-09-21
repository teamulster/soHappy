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
data class AboutConfig(val creditsURL: String, val privacyURL: String, val imprintURL: String,     val licenseURL: String,
                       val feedbackURL: String,
                       val issueURL: String) {
    class Builder(
        private var creditsURL: String = "",
        private var privacyURL: String = "",
        private var imprintURL: String = "",
        private var licenseURL: String = "",
        private var feedbackURL: String = "",
        private var issueURL: String = ""
    ) {
        fun setCreditsURL(creditsURL: String) = apply { this.creditsURL = creditsURL }
        fun setPrivacyURL(privacyURL: String) = apply { this.privacyURL = privacyURL }
        fun setImprintURL(imprintURL: String) = apply { this.imprintURL = imprintURL }
        fun setLicenseURL(licenseURL: String) = apply { this.licenseURL = licenseURL }
        fun setFeedbackURL(feedbackURL: String) = apply { this.feedbackURL = feedbackURL }
        fun setIssueURL(issueURL: String) = apply { this.issueURL = issueURL }
        fun build(): AboutConfig = AboutConfig(creditsURL, privacyURL, imprintURL, licenseURL, feedbackURL, issueURL)
    }
}
