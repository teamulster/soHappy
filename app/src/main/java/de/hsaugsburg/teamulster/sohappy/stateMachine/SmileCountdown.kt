package de.hsaugsburg.teamulster.sohappy.stateMachine

class SmileCountdown : State {
    override fun consumeAction(action: State.Action): State {
        TODO("Not yet implemented")
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
