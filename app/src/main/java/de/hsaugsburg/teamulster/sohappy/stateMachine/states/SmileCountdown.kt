package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

class SmileCountdown : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Questions()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality(): Action {
        return Action.Initial
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
