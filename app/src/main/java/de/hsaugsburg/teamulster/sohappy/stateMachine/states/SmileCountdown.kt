package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

class SmileCountdown : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Questions()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    @Suppress("")
    override fun executeCoreFunctionality() {
    }
}
