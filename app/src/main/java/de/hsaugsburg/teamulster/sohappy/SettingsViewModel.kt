package de.hsaugsburg.teamulster.sohappy

import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {
    var notificationsEnabled: Boolean = false
    var databaseEnabled: Boolean = false
}
