package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.analyzer.collector.Measurement
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentResultsBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.Start

/**
 * ResultsFragment serves as the conclusion of the smile procedure and provides the
 * user with a summary of their session.
 */
class ResultsFragment : Fragment() {
    private val stateMachine: StateMachine by activityViewModels()
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

        (this.requireActivity() as MainActivity).localDatabaseManager.updateMeasurement(measurement)

        stateMachine.addStateChangeListener { _, new ->
            if (this.isResumed) {
                when (new) {
                    is Start -> {
                        (requireActivity() as MainActivity).localDatabaseManager.close()
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

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }
}
