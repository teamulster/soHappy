package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine

class Stimulus : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> WaitingForSmile()
            else -> {
                Log.d("Invalid action: ", action.toString())
                this
            }
        }
    }

    @Suppress("EmptyFunctionBlock")
    override fun executeCoreFunctionality(
        stateMachine: StateMachine,
        cameraActivity: CameraActivity
    ) {
    }

    override fun prepareUi() {
        TODO("Not yet implemented")
        // show text
    }

    override fun tearDownUi() {
        TODO("Not yet implemented")
        // remove blue filter
        // remove text
    }
}
