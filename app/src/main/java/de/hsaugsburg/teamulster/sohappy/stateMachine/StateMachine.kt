package de.hsaugsburg.teamulster.sohappy.stateMachine

import de.hsaugsburg.teamulster.sohappy.stateMachine.states.*
import kotlin.properties.Delegates

class StateMachine {

    private var currentState by Delegates.observable<State>(initialValue = Start()) { _, old, new ->
        handleStateChange(old, new)
    }

    private fun handleStateChange(newState: State, oldState: State) {
        oldState.tearDownUi()
        newState.prepareUi()
        var action = newState.executeCoreFunctionality()
        currentState = currentState.consumeAction(action)
    }
}
