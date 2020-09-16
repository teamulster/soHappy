package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

/**
 * state where the camera image are analyser for smiles.
 */
class WaitingForSmile : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.SmileDetected -> SmileCountdown()
            is Action.WaitingForSmileTimer -> NoSmile()
            else -> {
                Log.d("Ignored action: ", action.toString())
                this
            }
        }
    }
}
