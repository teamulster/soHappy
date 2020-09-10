package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

class WaitingForSmile : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.SmileDetected -> SmileCountdown()
            is Action.Timeout -> NoSmile()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality() {
        startSmileDetection()
    }

    private fun startSmileDetection() {
        TODO("Not yet implemented")
    }
}
