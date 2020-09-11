package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine
import kotlin.concurrent.thread

class WaitingForFace : State {

    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Start()
            is Action.FaceDetected -> TakeABreath()
            else -> {
                Log.d("Invalid action: ", action.toString())
                this
            }
        }
    }

    override fun executeCoreFunctionality(
        stateMachine: StateMachine,
        cameraActivity: CameraActivity
    ) {
        thread {
            //TODO: get sec from config
            Thread.sleep(10 * 1000)
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
