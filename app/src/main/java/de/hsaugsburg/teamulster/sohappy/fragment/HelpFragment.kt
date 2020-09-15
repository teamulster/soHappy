package de.hsaugsburg.teamulster.sohappy.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.config.ConfigManager
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentHelpBinding

/**
 * HelpFragment contains a variety of tabs that have miscellaneous purposes.
 */
class HelpFragment : Fragment() {
    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_help,
            container,
            false
        )
        binding.button.setOnClickListener {
            openURL(ConfigManager.aboutConfig.creditsURL)
        }
        binding.button4.setOnClickListener {
            openURL(ConfigManager.aboutConfig.imprintURL)
        }
        binding.button3.setOnClickListener {
            openURL(ConfigManager.aboutConfig.privacyURL)
        }

        return binding.root
    }

    private fun openURL(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}