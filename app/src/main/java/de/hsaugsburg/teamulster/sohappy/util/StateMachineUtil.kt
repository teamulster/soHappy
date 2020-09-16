package de.hsaugsburg.teamulster.sohappy.util

import androidx.fragment.app.Fragment
import de.hsaugsburg.teamulster.sohappy.CameraActivity
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
        (fragment.requireActivity() as CameraActivity).stateMachine!!

    /**
     * creates a new state machine instance in [CameraActivity]
     * This method should only be used once for every "run".
     *
     * @param fragment to get activity from
     * @return the state machine
     */
    fun createStateMachine(fragment: Fragment): StateMachine {
        val activity = fragment.requireActivity() as CameraActivity
        activity.stateMachine = StateMachine()
        return activity.stateMachine!!
    }

}
