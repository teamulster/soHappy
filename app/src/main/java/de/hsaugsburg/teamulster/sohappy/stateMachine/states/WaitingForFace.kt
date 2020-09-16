package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

/**
 * state where the camera image are analyser for a face.
 */
class WaitingForFace : State {

    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.WaitingForFaceTimer -> Start()
            is Action.FaceDetected -> TakeABreath()
            else -> {
                Log.d("Ignored action: ", action.toString())
                this
            }
        }
    }
}
