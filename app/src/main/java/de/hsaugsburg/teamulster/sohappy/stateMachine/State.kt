package de.hsaugsburg.teamulster.sohappy.stateMachine

interface State {
    fun consumeAction(action: Action): State
    fun uiChange()
    fun activity()

    sealed class Action {
        class Start : Action()
        class End : Action()
        class FacedDetected : Action()
        class SmileDetected : Action()
        class Timeout : Action()
    }
}
