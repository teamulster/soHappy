package de.hsaugsburg.teamulster.sohappy

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentSettingsBinding
import de.hsaugsburg.teamulster.sohappy.viewmodel.SettingsViewModel

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
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.settingsNotificationSwitch.isActivated = viewModel.notificationsEnabled
        binding.settingsDatabaseSwitch.isActivated = viewModel.databaseEnabled
    }
}
