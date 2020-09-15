package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

/**
 * state where a timer countdown is running and the users face is analyzed for smiling.
 */
class SmileCountdown : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.SmileCountdownTimer-> Questions()
            else -> {
                Log.d("Invalid action: ", action.toString())
                this
            }
        }
    }
}
