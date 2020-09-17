package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentSettingsBinding
import de.hsaugsburg.teamulster.sohappy.viewmodel.SettingsViewModel

/**
 * SettingsFragment contains all UI elements concerning app settings.
 */
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )

        binding.settingsNotificationSwitch.setOnCheckedChangeListener { _, new ->
            viewModel.notificationsEnabled = new
            val activity = requireActivity() as CameraActivity
            if (new) {
                activity.notificationHandler.triggerNotificationAlarm()
            } else {
                activity.notificationHandler.cancelNotificationAlarm()
            }
        }
        binding.settingsDatabaseSwitch.setOnCheckedChangeListener { _, new ->
            viewModel.databaseEnabled = new
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.settingsNotificationSwitch.isChecked = viewModel.notificationsEnabled
        binding.settingsDatabaseSwitch.isChecked = viewModel.databaseEnabled
    }
}
