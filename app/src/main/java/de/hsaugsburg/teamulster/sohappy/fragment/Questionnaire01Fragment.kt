package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentQuestionnaire01Binding
import de.hsaugsburg.teamulster.sohappy.viewmodel.QuestionnaireViewModel

/**
 * Questionnaire01Fragment contains the question for the first part of the questionnaire.
 */
class Questionnaire01Fragment: Fragment() {
    private lateinit var binding: FragmentQuestionnaire01Binding
    private val viewModel: QuestionnaireViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_questionnaire_01,
            container,
            false
        )

        binding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.seekBarProgressLabel.text = seekBar.progress.toString()
                viewModel.questionnaire01Answer = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // no-op
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                // no-op
            }
        })
        binding.continueButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_questionnaire01Fragment_to_questionnaire02Fragment
            )
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
