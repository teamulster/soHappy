package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

/**
 * state where the user can decide whether he wants to restart the app flow or continue with the questions at the end.
 */
class NoSmile : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.ReturnButtonPressed -> Start()
            is Action.QuestionButtonPressed -> Questions()
            else -> {
                Log.d("Ignored action: ", action.toString())
                this
            }
        }
    }
}
