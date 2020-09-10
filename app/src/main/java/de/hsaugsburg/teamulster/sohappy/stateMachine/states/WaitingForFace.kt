package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.util.TransitionTimer
import kotlin.properties.Delegates

class WaitingForFace : State {

    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Start()
            is Action.FacedDetected -> TakeABreath()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality(): Action {
        //TODO: need to get the action wich is faster
        // synchronize ?
        var fasterAction: Action

        fasterAction = startFaceDetection()
        fasterAction = TransitionTimer.startTimer(10)
        return fasterAction
    }

    override fun prepareUi() {
        TODO("Not yet implemented")
        // red filter on
        // show text
    }

    override fun tearDownUi() {
        TODO("Not yet implemented")
        // remove red filter
        // remove text
    }

    private fun startFaceDetection(): Action {
        TODO("Not yet implemented")
    }
}
