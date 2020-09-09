package de.hsaugsburg.teamulster.sohappy.stateMachine

import java.util.*
import kotlin.properties.Delegates

class StateMachine {
    private var currentState by Delegates.observable<State>(initialValue = WaitingForFace()) { _, _, new ->
        handleStateChange(new)
    }

    // initialize here?
    private lateinit var timer: Timer

    // ui changes here or in state class?
    fun handleStateChange(newState: State) {
        newState.uiChange()
        newState.activity()
    }
}
