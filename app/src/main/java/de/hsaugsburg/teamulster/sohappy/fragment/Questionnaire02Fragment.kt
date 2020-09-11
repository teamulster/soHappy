package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentQuestionnaire02Binding
import de.hsaugsburg.teamulster.sohappy.viewmodel.QuestionnaireViewModel

/**
 * Questionnaire02Fragment contains the question for the second part of the questionnaire.
 */
class Questionnaire02Fragment: Fragment() {
    private lateinit var binding: FragmentQuestionnaire02Binding
    private val viewModel: QuestionnaireViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_questionnaire_02,
            container,
            false
        )
        binding.viewModel = viewModel

        binding.continueButton.setOnClickListener {
            findNavController().navigate(R.id.questionnaire03Fragment)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.i(this.javaClass.name, "ViewModel saved value for 01 " + viewModel.questionnaire01Answer)
    }
}
