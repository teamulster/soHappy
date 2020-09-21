package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores URLs to credits/privacy/imprint references.
 *
 * @property [creditsURL] a URL to credits reference
 * @property [privacyURL] a URL to privacy reference
 * @property [imprintURL] a URL to imprint reference
 */
data class AboutConfig(val creditsURL: String, val privacyURL: String, val imprintURL: String) {
    class Builder(
        private var creditsURL: String = "",
        private var privacyURL: String = "",
        private var imprintURL: String = ""
    ) {
        fun setCreditsURL(creditsURL: String) = apply { this.creditsURL = creditsURL }
        fun setPrivacyURL(privacyURL: String) = apply { this.privacyURL = privacyURL }
        fun setImprintURL(imprintURL: String) = apply { this.imprintURL = imprintURL }
        fun build(): AboutConfig = AboutConfig(creditsURL, privacyURL, imprintURL)
    }
}
