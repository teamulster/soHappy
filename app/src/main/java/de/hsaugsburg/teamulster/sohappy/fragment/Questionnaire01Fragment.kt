package de.hsaugsburg.teamulster.sohappy.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentQuestionnaire01Binding
import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel

/**
 * Questionnaire01Fragment contains the question for the first part of the questionnaire.
 */
class Questionnaire01Fragment : Fragment() {
    private lateinit var binding: FragmentQuestionnaire01Binding
    private val measurement: MeasurementViewModel by activityViewModels()
    private var hasInput: Boolean = false

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

        binding.seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
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

                    measurement.questionnaire.questionnaire01Answer = progress
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
                    R.id.action_questionnaire01Fragment_to_questionnaire02Fragment
                )
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // NO-OP
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
