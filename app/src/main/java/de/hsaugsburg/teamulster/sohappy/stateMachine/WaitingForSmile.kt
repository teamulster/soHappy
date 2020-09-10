package de.hsaugsburg.teamulster.sohappy.stateMachine

class WaitingForSmile : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.SmileDetected -> SmileCountdown()
            is Action.Timeout -> NoSmile()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
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
