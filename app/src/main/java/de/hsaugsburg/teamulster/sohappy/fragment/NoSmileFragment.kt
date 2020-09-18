package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentNosmileBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.Questions
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.Start

/**
 * If the user fails to smile for at least ten consecutive seconds, NoSmileFragment offers
 * them to either retry or continue onto the questionnaire.
 */
class NoSmileFragment : Fragment() {
    private lateinit var binding: FragmentNosmileBinding
    private val stateMachine: StateMachine by activityViewModels()

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

        stateMachine.addStateChangeListener { _, new ->
            if (this.isResumed) {
                when (new) {
                    is Start -> {
                        (requireActivity() as MainActivity).localDatabaseManager.close()
                        requireActivity().finish()
                        startActivity(requireActivity().intent)
                    }
                    is Questions ->
                        requireView().post {
                            findNavController().navigate(R.id.action_noSmileFragment_to_questionnaire01Fragment)
                        }
                }
            }
        }

        binding.retryButton.setOnClickListener {
            stateMachine.consumeAction(Action.ReturnButtonPressed)
        }

        binding.continueButton.setOnClickListener {
            stateMachine.consumeAction(Action.QuestionButtonPressed)
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
}
