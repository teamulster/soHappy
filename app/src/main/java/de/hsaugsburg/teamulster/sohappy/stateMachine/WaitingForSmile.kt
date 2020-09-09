package de.hsaugsburg.teamulster.sohappy.stateMachine

class WaitingForSmile : State {
    override fun consumeAction(action: State.Action): State {
        TODO("Not yet implemented")
    }

    override fun uiChange() {
        TODO("Not yet implemented")
    }

    override fun activity() {
        yellowFilterOn()
        showText()
        startSmileDetection()
    }

    private fun yellowFilterOn() {
        TODO("Not yet implemented")
    }

    private fun showText() {
        TODO("Not yet implemented")
    }

    private fun startSmileDetection() {
        TODO("Not yet implemented")
    }
}
