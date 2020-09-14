package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

class WaitingForFace : State {

    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.WaitingForFaceTimer -> Start()
            is Action.FaceDetected -> TakeABreath()
            else -> {
                Log.d("Invalid action: ", action.toString())
                this
            }
        }
    }
/*
    override fun executeCoreFunctionality(
        stateMachine: StateMachine,
        cameraActivity: CameraActivity
    ) {
        thread {
            //TODO: get sec from config
            Thread.sleep(10_000)
            stateMachine.consumeAction(Action.WaitingForFaceTimer)
        }
    }

 */
}
