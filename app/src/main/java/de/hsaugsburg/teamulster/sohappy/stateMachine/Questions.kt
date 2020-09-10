package de.hsaugsburg.teamulster.sohappy.stateMachine

class Questions : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.EndButtonPressed -> Start()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun activity() {
    }
}
