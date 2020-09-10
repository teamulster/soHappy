package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

class Start : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.StartButtonPressed -> WaitingForFace()
            // throw exception or return this (current state)?
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality(): Action {
        startCamera()
        // timer ...
        return Action.Initial
    }

    override fun prepareUi() {
        TODO("Not yet implemented")
        // show buttons for start and menu
    }

    override fun tearDownUi() {
        TODO("Not yet implemented")
        // remove text
    }

    private fun startCamera() {
        TODO("Not yet implemented")
    }
}