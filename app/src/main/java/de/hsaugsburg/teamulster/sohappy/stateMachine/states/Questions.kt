package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

/**
 * state where the user is asked some questions, e. g. about his mood
 */
class Questions : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.ReturnToStart -> Start()
            else -> {
                Log.d("Ignored action: ", action.toString())
                this
            }
        }
    }
}
