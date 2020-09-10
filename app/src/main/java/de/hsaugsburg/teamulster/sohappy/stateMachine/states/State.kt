package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action
import de.hsaugsburg.teamulster.sohappy.stateMachine.StateMachine

interface State {

    fun consumeAction(action: Action): State
    fun executeCoreFunctionality(): Action
    fun prepareUi()
    fun tearDownUi()
}
