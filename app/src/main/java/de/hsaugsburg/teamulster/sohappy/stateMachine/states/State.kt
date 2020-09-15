package de.hsaugsburg.teamulster.sohappy.stateMachine.states

import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

/**
 * determines the State's functionality.
 */
interface State {
    /**
     * handles the accepted state changes.
     *
     * @param action to be handled
     * @return the new State or the current state (this) if the action is not allowed
     */
    fun consumeAction(action: Action): State
}
