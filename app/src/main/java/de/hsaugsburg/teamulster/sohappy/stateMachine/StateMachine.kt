package de.hsaugsburg.teamulster.sohappy.stateMachine

import android.util.Log
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.stateMachine.states.*
import kotlin.properties.Delegates

class StateMachine(private val cameraActivity: CameraActivity) {
    val onStateChangeList = ArrayList<(State, State) -> Unit>()

    private var currentState by Delegates.observable<State>(initialValue = Start()) { _, old, new ->
        handleStateChange(old, new)
    }

    fun consumeAction(action: Action) {
        Log.d("current State: ", currentState.toString())
        currentState = currentState.consumeAction(action)
        Log.d("action: ", action.toString())
    }

    private fun handleStateChange(newState: State, oldState: State) {
        oldState.tearDownUi()
        newState.prepareUi()
        newState.executeCoreFunctionality(this, cameraActivity)
        for (r in onStateChangeList) {
            r.invoke(oldState, newState)
        }
    }
}
