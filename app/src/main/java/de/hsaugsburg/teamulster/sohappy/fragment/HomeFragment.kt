package de.hsaugsburg.teamulster.sohappy.fragment

import android.os.Bundle
import android.view.*
import androidx.core.animation.doOnEnd
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentHomeBinding
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.WaitingForFace
import de.hsaugsburg.teamulster.sohappy.util.StateMachineUtil

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
        stateMachine = StateMachineUtil.createStateMachine(this)
        stateMachine.addStateChangeListener { _, new ->
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
            val startButtonXY = IntArray(2)
            startButton.getLocationOnScreen(startButtonXY)
            val x = startButtonXY[0] + startButton.width / 2
            val y = startButtonXY[1] + startButton.height / 2

            animateCircle(binding.circleAnimView, x, y)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()

        // onNavDestinationSelected() would be an alternative, but Actions defined in the
        // Navigation Graph cannot be leveraged this way, hence the usage of a when() block
        when (item.itemId) {
            R.id.explanationFragment ->
                navController.navigate(R.id.action_homeFragment_to_explanationFragment)
            R.id.historyFragment ->
                navController.navigate(R.id.action_homeFragment_to_historyFragment)
            R.id.jokesFragment ->
                navController.navigate(R.id.action_homeFragment_to_jokesFragment)
            R.id.settingsFragment ->
                navController.navigate(R.id.action_homeFragment_to_settingsFragment)
            R.id.helpFragment ->
                navController.navigate(R.id.action_homeFragment_to_helpFragment)
        }

        return super.onOptionsItemSelected(item)
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
