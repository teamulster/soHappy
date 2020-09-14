package de.hsaugsburg.teamulster.sohappy.util

import androidx.fragment.app.Fragment
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine

object StateMachineSupport {

    fun getStateMachine(fragment: Fragment): StateMachine =
        (fragment.requireActivity() as CameraActivity).stateMachine

}