package de.hsaugsburg.teamulster.sohappy.stateMachine

interface State {
    fun consumeAction(action: Action): State
    fun uiChange()
    fun activity()
}
