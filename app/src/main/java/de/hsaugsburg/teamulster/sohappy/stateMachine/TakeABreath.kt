package de.hsaugsburg.teamulster.sohappy.stateMachine

class TakeABreath : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Stimulus()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun activity() {
    }
}
