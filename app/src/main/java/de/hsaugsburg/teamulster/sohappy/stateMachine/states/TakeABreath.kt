package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

class TakeABreath : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Stimulus()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality(): Action {
        // lggging
        return Action.Initial
    }

    override fun prepareUi() {
        TODO("Not yet implemented")
        // blue filter on
        // show text
    }

    override fun tearDownUi() {
        TODO("Not yet implemented")
        // remove text
    }
}
