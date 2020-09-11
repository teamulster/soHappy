package de.hsaugsburg.teamulster.sohappy.viewmodel

import androidx.lifecycle.ViewModel

/**
 * SettingsViewModel contain information about which settings are enabled / disabled.
 */
class SettingsViewModel: ViewModel() {
    var notificationsEnabled: Boolean = false
    var databaseEnabled: Boolean = false
}
