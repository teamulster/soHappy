package de.hsaugsburg.teamulster.sohappy.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentQuestionnaire05Binding
import de.hsaugsburg.teamulster.sohappy.viewmodel.QuestionnaireViewModel

/**
 * Questionnaire05Fragment contains the question for the fifth part of the questionnaire.
 */
class Questionnaire05Fragment: Fragment() {
    private lateinit var binding: FragmentQuestionnaire05Binding
    private val viewModel: QuestionnaireViewModel by activityViewModels()
    private var hasInput: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_questionnaire_05,
            container,
            false
        )

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUSer: Boolean) {
                hasInput = true
                val color = resources.getColor(R.color.colorPrimary, null)
                val enabledBackground = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(color, color)
                )
                enabledBackground.cornerRadius = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics
                )

                viewModel.questionnaire05Answer = progress
                binding.continueButton.background = enabledBackground
                binding.continueButton.setTextColor(resources.getColor(android.R.color.white, null))
                binding.seekBarProgressLabel.apply {
                    text = progress.toString()
                    visibility = View.VISIBLE
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // no-op
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                // no-op
            }
        })
        binding.continueButton.setOnClickListener {
            if (hasInput) {
                findNavController().navigate(
                    R.id.action_questionnaire05Fragment_to_questionnaire06Fragment
                )
            }
        }

        return binding.root
    }
}
