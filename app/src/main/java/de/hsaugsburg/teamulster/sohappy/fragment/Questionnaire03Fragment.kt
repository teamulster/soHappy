package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentQuestionnaire03Binding
import de.hsaugsburg.teamulster.sohappy.viewmodel.QuestionnaireViewModel

/**
 * Questionnaire03Fragment contains the question for the third part of the questionnaire.
 */
class Questionnaire03Fragment: Fragment() {
    private lateinit var binding: FragmentQuestionnaire03Binding
    private val viewModel: QuestionnaireViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_questionnaire_03,
            container,
            false
        )

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
            findNavController().navigate(R.id.questionnaire04Fragment)
        }

        return binding.root
    }
}
