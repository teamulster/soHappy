package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

class NoSmile : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.ReturnToWaitingForFace -> Start()
            is Action.QuestionButtonPressed -> Questions()
            else -> {
                Log.d("Invalid action: ", action.toString())
                this
            }
        }
    }
}
