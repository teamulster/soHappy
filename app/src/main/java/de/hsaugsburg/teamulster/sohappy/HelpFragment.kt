package de.hsaugsburg.teamulster.sohappy

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentHelpBinding
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentHomeBinding

class HelpFragment : Fragment() {
    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_help,
            container,
            false
        )

        return binding.root
    }
}
