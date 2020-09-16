package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentNosmileBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.Questions
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.WaitingForFace
import de.hsaugsburg.teamulster.sohappy.util.StateMachineUtil

/**
 * If the user fails to smile for at least ten consecutive seconds, NoSmileFragment offers
 * them to either retry or continue onto the questionnaire.
 */
class NoSmileFragment : Fragment() {
    private lateinit var binding: FragmentNosmileBinding
    private lateinit var stateMachine: StateMachine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_nosmile,
            container,
            false
        )

        stateMachine = StateMachineUtil.getStateMachine(this)

        stateMachine.addStateChangeListener { _, new ->
            when(new) {
                is WaitingForFace ->
                        findNavController().popBackStack(R.id.smileFragment, false)
                is Questions ->
                        findNavController().navigate(R.id.action_noSmileFragment_to_questionnaire01Fragment)
            }
        }

        binding.retryButton.setOnClickListener {
            stateMachine.consumeAction(Action.ReturnToWaitingForFace)
        }

        binding.continueButton.setOnClickListener {
            stateMachine.consumeAction(Action.QuestionButtonPressed)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }
}
