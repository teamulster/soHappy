package de.hsaugsburg.teamulster.sohappy.stateMachine

import java.util.*
import kotlin.properties.Delegates

class StateMachine {
    private var currentState by Delegates.observable<State>(initialValue = WaitingForFace()) { _, _, new ->
        handleStateChange(new)
    }

    // initialize here?
    // general question: where to put the ui staff and shall it be separated from function (face and smile detection)?
    private lateinit var timer: Timer

    // ui changes here or in state class?
    private fun handleStateChange(newState: State) {
        newState.uiChange()
        newState.activity()
    }
}
