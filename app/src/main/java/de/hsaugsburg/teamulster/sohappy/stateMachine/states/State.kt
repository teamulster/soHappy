package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine

interface State {
    fun consumeAction(action: Action): State
    fun executeCoreFunctionality(stateMachine: StateMachine, cameraActivity: CameraActivity)
    fun prepareUi()
    fun tearDownUi()
}
