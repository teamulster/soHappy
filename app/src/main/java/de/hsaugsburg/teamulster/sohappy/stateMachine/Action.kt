package de.hsaugsburg.teamulster.sohappy.stateMachine

sealed class Action {
    object StartButtonPressed : Action()
    object ReturnToStart : Action()
    object ReturnToWaitingForFace : Action()
    object QuestionButtonPressed : Action()
    object FaceDetected : Action()
    object SmileDetected : Action()
    object TakeABreathTimer : Action()
    object StimulusTimer : Action()
    object SmileCountdownTimer : Action()
    object WaitingForSmileTimer : Action()
    object WaitingForFaceTimer: Action()
}
