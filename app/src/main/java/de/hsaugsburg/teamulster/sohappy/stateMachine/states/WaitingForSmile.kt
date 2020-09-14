package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

class WaitingForSmile : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.SmileDetected -> SmileCountdown()
            is Action.WaitingForSmileTimer -> NoSmile()
            else -> {
                Log.d("Invalid action: ", action.toString())
                this
            }
        }
    }
}
