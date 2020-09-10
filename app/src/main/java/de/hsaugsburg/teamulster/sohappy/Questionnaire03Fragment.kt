package de.hsaugsburg.teamulster.sohappy

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentQuestionnaire03Binding
import de.hsaugsburg.teamulster.sohappy.viewmodel.QuestionnaireViewModel

class Questionnaire03Fragment: Fragment() {
    private lateinit var binding: FragmentQuestionnaire03Binding
    private val viewModel: QuestionnaireViewModel by activityViewModels()

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
        binding.viewModel = viewModel

        binding.continueButton.setOnClickListener {
            findNavController().navigate(R.id.questionnaire04Fragment)
        }

        return binding.root
    }
}
