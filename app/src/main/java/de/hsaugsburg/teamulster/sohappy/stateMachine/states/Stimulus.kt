package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

class Stimulus : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.StimulusTimer -> WaitingForSmile()
            else -> {
                Log.d("Invalid action: ", action.toString())
                this
            }
        }
    }
}
