package de.hsaugsburg.teamulster.sohappy.stateMachine

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.Start
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.State
import kotlin.properties.Delegates

class StateMachine {
    private val onStateChangeList = ArrayList<(State, State) -> Unit>()

    // TODO: Read only by outside
    private var currentState by Delegates.observable<State>(initialValue = Start()) { _, old, new ->
        handleStateChange(old, new)
    }

    fun consumeAction(action: Action) {
        Log.d("current State: ", currentState.toString())
        currentState = currentState.consumeAction(action)
        Log.d("action: ", action.toString())
    }

    fun addStateChangeListener(function: (old: State, new: State) -> Unit) {
        onStateChangeList.add(function)
    }

    fun getCurrentMachineState(): State = currentState

    private fun handleStateChange(oldState: State, newState: State) {
        for (r in onStateChangeList) {
            r.invoke(oldState, newState)
        }
    }
}
