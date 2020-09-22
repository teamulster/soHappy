package de.hsaugsburg.teamulster.sohappy.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentSettingsBinding
import de.hsaugsburg.teamulster.sohappy.sync.RemoteSite
import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel
import de.hsaugsburg.teamulster.sohappy.viewmodel.SettingsViewModel
import java.util.*
import kotlin.concurrent.thread

/**
 * SettingsFragment contains all UI elements concerning app settings.
 */
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val remoteSite = RemoteSite()
    private val viewModel: SettingsViewModel by activityViewModels()

    @SuppressLint("HardwareIds")
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
            val activity = requireActivity() as MainActivity
            if (new) {
                activity.notificationHandler.triggerNotificationAlarm()
            } else {
                activity.notificationHandler.cancelNotificationAlarm()
            }
        }
        binding.settingsDatabaseSwitch.setOnCheckedChangeListener { _, new ->
            viewModel.databaseEnabled = new
        }

        binding.syncButton.setOnClickListener {
            thread {
                val id: String = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
                val measurements = (requireActivity() as MainActivity).localDatabaseManager
                    ?.getMeasurementsByTimeStamp(remoteSite.getLatestSyncTimeStamp(id))
                remoteSite.synchronise(measurements as ArrayList<MeasurementViewModel>)
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.settingsNotificationSwitch.isChecked = viewModel.notificationsEnabled
        binding.settingsDatabaseSwitch.isChecked = viewModel.databaseEnabled
    }
}
