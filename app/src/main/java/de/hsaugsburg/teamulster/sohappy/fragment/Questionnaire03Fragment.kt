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
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentQuestionnaire03Binding
import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel

/**
 * Questionnaire03Fragment contains the question for the third part of the questionnaire.
 */
class Questionnaire03Fragment : Fragment() {
    private lateinit var binding: FragmentQuestionnaire03Binding
    private val measurement: MeasurementViewModel by activityViewModels()
    private var hasInput: Boolean = false

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

        binding.seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUSer: Boolean) {
                    hasInput = true
                    val color = resources.getColor(R.color.colorPrimary, null)
                    val enabledBackground = GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        intArrayOf(color, color)
                    )
                    enabledBackground.cornerRadius = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        5f,
                        resources.displayMetrics
                    )

                    measurement.questionnaire.questionnaire03Answer = progress
                    binding.continueButton.background = enabledBackground
                    binding.continueButton.setTextColor(resources.getColor(android.R.color.white, null))
                    binding.seekBarProgressLabel.apply {
                        text = progress.toString()
                        visibility = View.VISIBLE
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    // NO-OP
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    // NO-OP
                }
            }
        )
        binding.continueButton.setOnClickListener {
            if (hasInput) {
                findNavController().navigate(
                    R.id.action_questionnaire03Fragment_to_questionnaire04Fragment
                )
            }
        }

        return binding.root
    }
}
