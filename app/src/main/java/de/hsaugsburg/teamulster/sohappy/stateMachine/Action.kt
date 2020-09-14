package de.hsaugsburg.teamulster.sohappy.stateMachine

sealed class Action {
    object StartButtonPressed : Action()
    object EndButtonPressed : Action()
    object QuestionButtonPressed : Action()
    object FaceDetected : Action()
    object SmileDetected : Action()
    object Timeout : Action()
    object SmileCountdownTimeout : Action()
    object NoSmileTimeout : Action()
    object WaitingForFaceTimeout: Action()
}
