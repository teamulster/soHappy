package de.hsaugsburg.teamulster.sohappy.stateMachine

import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.*
import kotlin.properties.Delegates

class StateMachine(private val cameraActivity: CameraActivity) {

    private var currentState by Delegates.observable<State>(initialValue = Start()) { _, old, new ->
        handleStateChange(old, new)
    }

    fun consumeAction(action: Action) {
        currentState = currentState.consumeAction(action)
    }

    private fun handleStateChange(newState: State, oldState: State) {
        oldState.tearDownUi()
        newState.prepareUi()
        newState.executeCoreFunctionality(this, cameraActivity)
    }
}
