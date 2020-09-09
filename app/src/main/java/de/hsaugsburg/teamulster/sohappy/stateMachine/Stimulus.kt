package de.hsaugsburg.teamulster.sohappy.stateMachine


class Stimulus : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> WaitingForSmile()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun uiChange() {
        TODO("Not yet implemented")
    }

    override fun activity() {
        showText()
    }

    private fun showText() {
        TODO("Not yet implemented")
    }
}
