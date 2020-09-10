package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import kotlin.properties.Delegates

class WaitingForSmile : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.SmileDetected -> SmileCountdown()
            is Action.Timeout -> NoSmile()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality(): Action {
        // synchronize ?
        var fasterAction by Delegates.observable<Action>(initialValue = Action.Initial) { _, _, new ->
            consumeAction(new)
        }
        startSmileDetection()
        // start timer
        return fasterAction
    }

    override fun prepareUi() {
        TODO("Not yet implemented")
        // yellow filter on
        // show text
    }

    override fun tearDownUi() {
        TODO("Not yet implemented")
        // remove yellow filter
        // remove text
    }

    private fun startSmileDetection() {
        TODO("Not yet implemented")
    }
}
