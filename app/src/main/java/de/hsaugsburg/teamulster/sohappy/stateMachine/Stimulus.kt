package de.hsaugsburg.teamulster.sohappy.stateMachine


abstract class Stimulus: State {
    override fun consumeAction(action: State.Action): State {
        TODO("Not yet implemented")
    }

    override fun activity() {
        showText()
    }

    private fun showText() {
        TODO("Not yet implemented")
    }
}
