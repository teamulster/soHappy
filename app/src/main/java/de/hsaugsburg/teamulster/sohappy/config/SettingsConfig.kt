package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores everything a user can change in the settings section in the app.
 *
 * @property [notifications] determines whether or not app notifications are enabled
 * @property [databaseSync] determines whether or not usage data is synced with the remote site
 * */
data class SettingsConfig(val notifications: Boolean, val databaseSync: Boolean)
