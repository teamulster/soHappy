package de.hsaugsburg.teamulster.sohappy.stateMachine

class TakeABreath: State {
    override fun consumeAction(action: State.Action): State {
        TODO("Not yet implemented")
    }

    override fun activity() {
        blueFilterOn()
        showText()
    }

    private fun blueFilterOn() {
        TODO("Not yet implemented")
    }

    private fun showText() {
        TODO("Not yet implemented")
    }
}
