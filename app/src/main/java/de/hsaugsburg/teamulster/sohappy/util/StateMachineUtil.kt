package de.hsaugsburg.teamulster.sohappy.util

import androidx.fragment.app.Fragment
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine

/**
 * Helper to gain access to the state machine.
 */
object StateMachineUtil {

    /**
     * provides the state machine instance through the given [fragment].
     *
     * @param fragment to get activity from
     * @return the state machine
     */
    fun getStateMachine(fragment: Fragment): StateMachine =
        (fragment.requireActivity() as MainActivity).stateMachine!!

    /**
     * creates a new state machine instance in [MainActivity]
     * This method should only be used once for every "run".
     *
     * @param fragment to get activity from
     * @return the state machine
     */
    fun createStateMachine(fragment: Fragment): StateMachine {
        val activity = fragment.requireActivity() as MainActivity
        activity.stateMachine = StateMachine()
        return activity.stateMachine!!
    }

}
