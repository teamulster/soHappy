package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action


class Stimulus : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> WaitingForSmile()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality() {
    }
}
