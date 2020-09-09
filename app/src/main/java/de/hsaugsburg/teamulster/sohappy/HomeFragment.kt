package de.hsaugsburg.teamulster.sohappy

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        setHasOptionsMenu(true)

        val startButton = binding.startButton
        startButton.setOnClickListener {
            val x = (startButton.x + startButton.width / 2).toInt()
            val y = (startButton.y + startButton.height / 2).toInt()

            animateCircle(binding.circleAnimView, x, y)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar!!.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item, requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    private fun animateCircle(view: View, centerX: Int, centerY: Int) {
        val radius: Float = view.width.coerceAtLeast(view.height).toFloat()
        val circleAnimation = ViewAnimationUtils.createCircularReveal(
            view,
            centerX,
            view.height - centerY,
            0f,
            radius * 1.1f
        )
        circleAnimation.duration = 300
        view.visibility = View.VISIBLE

        circleAnimation.doOnEnd {
            findNavController().navigate(R.id.smileFragment)
        }

        circleAnimation.start()
    }
}
