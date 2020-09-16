package de.hsaugsburg.teamulster.sohappy.stateMachine

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.Start
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.State
import kotlin.properties.Delegates

/**
 * State machine to handle the different states and actions of the application.
 */
class StateMachine {
    private val onStateChangeList = ArrayList<(State, State) -> Unit>()
    private var currentState by Delegates.observable<State>(initialValue = Start()) { _, old, new ->
        handleStateChange(old, new)
    }

    /**
     * given action will be consumed by the current state and leads to a state change.
     *
     * @param action to be consumed
     */
    fun consumeAction(action: Action) {
        Log.d("current State: ", currentState.toString())
        currentState = currentState.consumeAction(action)
        Log.d("action: ", action.toString())
    }

    /**
     * adds a function to the StateChangeList, which is called after a state transition.
     *
     * @param function to be added
     */
    fun addStateChangeListener(function: (old: State, new: State) -> Unit) {
        onStateChangeList.add(function)
    }

    /**
     * @returns the current state
     */
    fun getCurrentMachineState(): State = currentState

    /**
     * calls all methods from (observable) changeList, which need to be handle after a change.
     *
     * @param oldState: state before transition
     * @param newState: state after transition
     */
    private fun handleStateChange(oldState: State, newState: State) {
        for (r in onStateChangeList) {
            r.invoke(oldState, newState)
        }
    }
}
