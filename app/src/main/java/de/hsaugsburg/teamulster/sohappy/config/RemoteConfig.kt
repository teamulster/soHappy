package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores the RemoteConfig.
 *
 * @property [url]
 */
// TODO: Add String property: class which implements RemoteSync
data class RemoteConfig(val url: String) {
    constructor(builder: Builder) : this(builder.url)

    /**
     * This class implements a [Builder] for [RemoteConfig].
     * */
    class Builder(var url: String = "") {
        /**
         * This function sets a value for [url].
         * It is mandatory to call this function before building.
         *
         * @param [url]
         * */
        fun setURL(url: String) = apply { this.url = url }

        /**
         * This function calls the [RemoteConfig] constructor.
         *
         * @return [RemoteConfig] instance
         * */
        fun build() = RemoteConfig(url)
    }
}
