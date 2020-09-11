package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentQuestionnaire06Binding
import de.hsaugsburg.teamulster.sohappy.viewmodel.QuestionnaireViewModel

class Questionnaire06Fragment: Fragment() {
    private lateinit var binding: FragmentQuestionnaire06Binding
    private val viewModel: QuestionnaireViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_questionnaire_06,
            container,
            false
        )

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.seekBarProgressLabel.text = seekBar.progress.toString()
                viewModel.questionnaire01Answer = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        binding.continueButton.setOnClickListener {
            findNavController().navigate(R.id.resultsFragment)
        }

        return binding.root
    }
}
