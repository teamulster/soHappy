package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine

class Start : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.StartButtonPressed -> WaitingForFace()
            // throw exception or return this (current state)?
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun executeCoreFunctionality(stateMachine: StateMachine, cameraActivity: CameraActivity) {
        startCamera()
        // timer ...
    }

    override fun prepareUi() {
        TODO("Not yet implemented")
        // show buttons for start and menu
    }

    override fun tearDownUi() {
        TODO("Not yet implemented")
        // remove text
    }

    private fun startCamera() {
        TODO("Not yet implemented")
    }
}