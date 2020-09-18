package de.hsaugsburg.teamulster.sohappy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import de.hsaugsburg.teamulster.sohappy.config.ConfigManager
import de.hsaugsburg.teamulster.sohappy.config.SettingsConfig
import kotlin.properties.Delegates.observable

/**
 * SettingsViewModel contain information about which settings are enabled / disabled.
 */
class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    var notificationsEnabled: Boolean by observable(ConfigManager.settingsConfig.notifications) { _, _, new ->
        ConfigManager.store(
            application,
            ConfigManager.mainConfig,
            SettingsConfig(
                new,
                databaseEnabled
            )
        )
    }
    var databaseEnabled: Boolean by observable(ConfigManager.settingsConfig.databaseSync) { _, _, new ->
        ConfigManager.store(
            application,
            ConfigManager.mainConfig,
            SettingsConfig(
                notificationsEnabled,
                new
            )
        )
    }

    init {
        notificationsEnabled = ConfigManager.settingsConfig.notifications
        databaseEnabled = ConfigManager.settingsConfig.databaseSync
    }
}
