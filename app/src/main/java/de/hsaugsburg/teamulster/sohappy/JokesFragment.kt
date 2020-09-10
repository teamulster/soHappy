package de.hsaugsburg.teamulster.sohappy

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentJokesBinding

class JokesFragment : Fragment() {
    private lateinit var binding: FragmentJokesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_jokes,
            container,
            false
        )

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        return binding.root
    }
}
