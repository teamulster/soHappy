package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.analyzer.collector.Measurement
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentResultsBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.Start
import de.hsaugsburg.teamulster.sohappy.util.StateMachineUtil

/**
 * ResultsFragment serves as the conclusion of the smile procedure and provides the
 * user with a summary of their session.
 */
class ResultsFragment : Fragment() {
    private lateinit var stateMachine: StateMachine
    private lateinit var binding: FragmentResultsBinding
    private val measurement: Measurement by activityViewModels()

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

        (this.requireActivity() as CameraActivity).localDatabaseManager.updateMeasurement(measurement)

        stateMachine = StateMachineUtil.getStateMachine(this)
        stateMachine.addStateChangeListener { _, new ->
            if (this.isResumed) {
                when (new) {
                    is Start -> findNavController().navigate(R.id.homeFragment)
                }
            }
        }

        binding.finishButton.setOnClickListener {
            stateMachine.consumeAction(Action.ReturnToStart)
        }

        return binding.root
    }
}
