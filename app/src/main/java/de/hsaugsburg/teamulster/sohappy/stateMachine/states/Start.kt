package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

/**
 * state of the start screen.
 */
class Start : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.StartButtonPressed -> WaitingForFace()
            else -> {
                Log.d("Ignored action: ", action.toString())
                this
            }
        }
    }
}
