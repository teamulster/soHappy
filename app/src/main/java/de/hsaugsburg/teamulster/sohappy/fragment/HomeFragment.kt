package de.hsaugsburg.teamulster.sohappy.fragment

import android.graphics.Camera
import android.os.Bundle
import android.view.*
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentHomeBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.WaitingForFace

/**
 * HomeFragment is the entry point for the app.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var stateMachine : StateMachine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        stateMachine = (this.requireActivity() as CameraActivity).stateMachine
        // TODO: extract (this.requireActivity() as CameraActivity).stateMachine into own, shared function
        stateMachine.onStateChangeList.add { _, new ->
            if (new is WaitingForFace) {
                findNavController().navigate(R.id.smileFragment)
            }

        }

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
           stateMachine.consumeAction(Action.StartButtonPressed)
        }

        circleAnimation.start()
    }
}
