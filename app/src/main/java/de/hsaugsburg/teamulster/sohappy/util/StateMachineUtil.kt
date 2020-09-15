package de.hsaugsburg.teamulster.sohappy.util

import androidx.fragment.app.Fragment
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine

/**
 * Helper to gain access to the state machine.
 */
object StateMachineUtil {

    /**
     * provides the state machine instance through the given fragment.
     *
     * @param fragment to get activity from
     * @return the state machine
     */
    fun getStateMachine(fragment: Fragment): StateMachine =
        (fragment.requireActivity() as CameraActivity).stateMachine

}
