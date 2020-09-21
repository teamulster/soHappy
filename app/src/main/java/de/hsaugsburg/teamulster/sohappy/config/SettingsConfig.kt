package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores everything a user can change in the settings section in the app.
 *
 * @property [notifications] determines whether or not app notifications are enabled
 * @property [databaseSync] determines whether or not usage data is synced with the remote site
 * */
data class SettingsConfig(val notifications: Boolean, val databaseSync: Boolean) {
    constructor(builder: Builder) : this(builder.notifications!!, builder.databaseSync!!)

    /**
     * This class implements a [Builder] for [SettingsConfig].
     * */
    class Builder(var notifications: Boolean? = null, var databaseSync: Boolean? = null) {
        /**
         * This function sets a value for [notifications].
         * It is mandatory to call this function before building.
         *
         * @param [notifications]
         * */
        fun setNotifications(notifications: Boolean) = apply { this.notifications = notifications }

        /**
         * This function sets a value for [databaseSync].
         * It is mandatory to call this function before building.
         *
         * @param [databaseSync]
         * */
        fun setDatabaseSync(databaseSync: Boolean) = apply { this.databaseSync = databaseSync }

        /**
         * This function calls the [SettingsConfig] constructor.
         *
         * @return [SettingsConfig] instance
         * */
        fun build() = SettingsConfig(notifications!!, databaseSync!!)
    }
}
