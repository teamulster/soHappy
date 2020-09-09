package de.hsaugsburg.teamulster.sohappy.stateMachine

class Start: State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.StartButtonPressed -> WaitingForFace()
            // throw exception or return this (current state)?
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun uiChange() {
        TODO("Not yet implemented")
    }

    override fun activity() {
        TODO("Not yet implemented")
    }
}