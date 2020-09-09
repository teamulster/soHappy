package de.hsaugsburg.teamulster.sohappy.stateMachine

class SmileCountdown : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Questions()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun uiChange() {
        TODO("Not yet implemented")
    }

    override fun activity() {
        blueFilterOn()
        showText()
        showSmileyFeedback()
    }

    private fun blueFilterOn() {
        TODO("Not yet implemented")
    }

    private fun showText() {
        TODO("Not yet implemented")
    }

    private fun showSmileyFeedback() {
        TODO("Not yet implemented")
    }
}
