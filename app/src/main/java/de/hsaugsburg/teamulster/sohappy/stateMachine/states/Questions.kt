package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

class Questions : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.EndButtonPressed -> Start()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality(): Action {
        return Action.Initial
    }

    override fun prepareUi() {
        TODO("Not yet implemented")
        // neutral background
        // show text
    }

    override fun tearDownUi() {
        TODO("Not yet implemented")
        // remove background
        // remove text
    }
}
