package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import kotlin.properties.Delegates

class WaitingForSmile : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.SmileDetected -> SmileCountdown()
            is Action.Timeout -> NoSmile()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality(stateMachine: StateMachine, cameraActivity: CameraActivity) {
        // synchronize ?
        startSmileDetection()
    }

    override fun prepareUi() {
        TODO("Not yet implemented")
        // yellow filter on
        // show text
    }

    override fun tearDownUi() {
        TODO("Not yet implemented")
        // remove yellow filter
        // remove text
    }

    private fun startSmileDetection() {
        TODO("Not yet implemented")
    }
}
