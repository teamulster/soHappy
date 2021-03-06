package de.hsaugsburg.teamulster.sohappy.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentQuestionnaire04Binding
import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel

/**
 * Questionnaire04Fragment contains the question for the fourth part of the questionnaire.
 */
class Questionnaire04Fragment : Fragment() {
    private lateinit var binding: FragmentQuestionnaire04Binding
    private val measurement: MeasurementViewModel by activityViewModels()
    private var hasInput: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_questionnaire_04,
            container,
            false
        )

        var initialItem = false
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (initialItem) {
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

                    binding.continueButton.background = enabledBackground
                    binding.continueButton.setTextColor(
                        resources.getColor(
                            android.R.color.white,
                            null
                        )
                    )
                    measurement.questionnaire.questionnaire04Answer = p2
                }
                initialItem = true
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // NO-OP
            }
        }

        binding.continueButton.setOnClickListener {
            if (hasInput) {
                findNavController().navigate(
                    R.id.action_questionnaire04Fragment_to_questionnaire05Fragment
                )
            }
        }

        return binding.root
    }
}
