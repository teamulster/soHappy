package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

/**
 * state where a stimulus is shown to initialize a users smile.
 */
class Stimulus : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.StimulusTimer -> WaitingForSmile()
            else -> {
                Log.d("Ignored action: ", action.toString())
                this
            }
        }
    }
}
