package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentNosmileBinding

/**
 * If the user fails to smile for at least ten consecutive seconds, NoSmileFragment offers
 * them to either retry or continue onto the questionnaire.
 */
class NoSmileFragment : Fragment() {
    private lateinit var binding: FragmentNosmileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_nosmile,
            container,
            false
        )

        binding.retryButton.setOnClickListener {
            findNavController().navigate(R.id.action_noSmileFragment_to_smileFragment)
        }

        binding.continueButton.setOnClickListener {
            findNavController().navigate(R.id.action_noSmileFragment_to_questionnaire01Fragment)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }
}
