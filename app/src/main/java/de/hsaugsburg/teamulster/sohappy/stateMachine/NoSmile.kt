package de.hsaugsburg.teamulster.sohappy.stateMachine

class NoSmile : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.EndButtonPressed -> Start()
            is Action.QuestionButtonPressed -> Questions()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun activity() {
    }
}
