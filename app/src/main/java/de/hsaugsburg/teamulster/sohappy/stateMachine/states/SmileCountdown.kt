package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine

class SmileCountdown : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Questions()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality(stateMachine: StateMachine, cameraActivity: CameraActivity) {
    }

    override fun prepareUi() {
        TODO("Not yet implemented")
        // blue filter on
        // show text
        // show smiley feedback
    }

    override fun tearDownUi() {
        TODO("Not yet implemented")
        // remove blue filter
        // remove text
        // remove smiley feedback
    }
}
