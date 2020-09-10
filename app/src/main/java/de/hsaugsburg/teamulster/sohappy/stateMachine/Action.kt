package de.hsaugsburg.teamulster.sohappy.stateMachine

sealed class Action {
    object StartButtonPressed : Action()
    object EndButtonPressed : Action()
    object QuestionButtonPressed : Action()
    object FacedDetected : Action()
    object SmileDetected : Action()
    object Timeout : Action()
}
