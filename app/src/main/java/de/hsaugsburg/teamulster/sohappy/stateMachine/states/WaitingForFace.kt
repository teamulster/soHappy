package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.os.CountDownTimer
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import de.hsaugsburg.teamulster.sohappy.util.TransitionTimer
import java.util.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class WaitingForFace : State {

    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Start()
            is Action.FacedDetected -> TakeABreath()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality(stateMachine: StateMachine, cameraActivity: CameraActivity) {
        //TODO: need to get the action wich is faster
        // synchronize?
        thread {
            Thread.sleep(3000)
            stateMachine.consumeAction(Action.Timeout)
        }
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
