package de.hsaugsburg.teamulster.sohappy.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentResultsBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.Start
import de.hsaugsburg.teamulster.sohappy.util.StateMachineUtil
import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel
import kotlin.concurrent.thread

/**
 * ResultsFragment serves as the conclusion of the smile procedure and provides the
 * user with a summary of their session.
 */
class ResultsFragment : Fragment() {
    private lateinit var stateMachine: StateMachine
    private lateinit var binding: FragmentResultsBinding
    private val measurement: MeasurementViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_results,
            container,
            false
        )

        binding.measurement = measurement


        stateMachine = StateMachineUtil.getStateMachine(this)
        stateMachine.addStateChangeListener { _, new ->
            if (this.isResumed) {
                when (new) {
                    is Start -> {
                        (requireActivity() as CameraActivity).localDatabaseManager.close()
                        requireActivity().finish()
                        startActivity(requireActivity().intent)
                    }
                }
            }
        }

        binding.finishButton.setOnClickListener {
            stateMachine.consumeAction(Action.ReturnToStart)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        thread {
            (this.requireActivity() as CameraActivity).localDatabaseManager.updateMeasurement(measurement)

            requireView().post {
                val color = resources.getColor(R.color.colorPrimary, null)
                val enabledBackground = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    intArrayOf(color, color)
                )
                enabledBackground.cornerRadius = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics
                )

                binding.finishButton.background = enabledBackground
                binding.finishButton.setTextColor(
                    resources.getColor(
                        android.R.color.white,
                        null
                    )
                )
                binding.updateProgressBar.visibility = View.GONE
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }
}
