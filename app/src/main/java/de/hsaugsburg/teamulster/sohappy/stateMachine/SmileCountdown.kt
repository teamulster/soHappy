package de.hsaugsburg.teamulster.sohappy.stateMachine

class SmileCountdown : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Questions()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun activity() {
    }
}

