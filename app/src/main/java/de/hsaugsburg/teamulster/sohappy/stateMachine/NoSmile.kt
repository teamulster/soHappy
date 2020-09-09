package de.hsaugsburg.teamulster.sohappy.stateMachine

class NoSmile : State {
    override fun consumeAction(action: State.Action): State {
        TODO("Not yet implemented")
    }

    override fun uiChange() {
        TODO("Not yet implemented")
    }

    override fun activity() {
        neutralBackground()
        showText()
    }

    private fun neutralBackground() {
        TODO("Not yet implemented")
    }

    private fun showText() {
        TODO("Not yet implemented")
    }
}
