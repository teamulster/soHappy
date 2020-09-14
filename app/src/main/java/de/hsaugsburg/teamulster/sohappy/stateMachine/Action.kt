package de.hsaugsburg.teamulster.sohappy.stateMachine

/**
 * transitions of the state machine.
 */
sealed class Action {
    /**
     * when the user presses the start button on the start screen.
     * transition between Start and WaitingForFace state.
     */
    object StartButtonPressed : Action()

    /**
     * if there is no face detected after a specific time.
     * transition between WaitingForFace and Start state.
     */
    object WaitingForFaceTimer : Action()

    /**
     * if the Analyser finds a face in the frame.
     * transition between WaitingForFace and TakeABreath state.
     */
    object FaceDetected : Action()

    /**
     * when the specified time for the user to take a breath runs out.
     * transition between TakeABreath and Stimulus state.
     */
    object TakeABreathTimer : Action()

    /**
     * when the specified time for reading the stimulus runs out.
     * transition between Stimulus and WaitingForSmile state.
     */
    object StimulusTimer : Action()

    /**
     * if the Analyser finds a smile in the frame.
     * transition between WaitingForSmile and SmileCountdown state.
     */
    object SmileDetected : Action()

    /**
     * if there is no smile detected after a specific time.
     * transition between WaitingForSmile and noSmile state.
     */
    object WaitingForSmileTimer : Action()

    /**
     * when the specific time for the user to smile (and the analyser to detect a smile) runs out.
     * transition between SmileCountdown and Questions state.
     */
    object SmileCountdownTimer : Action()

    /**
     * when the user finishes the questions phase and returns to start screen (end of circle).
     * transition between Questions and Start state.
     */
    object ReturnToStart : Action()

    /**
     * if the user decide to start the app flow again after no smile was found (restart detection circle).
     * transition between NoSmile and WaitingForFace state.
     */
    object ReturnToWaitingForFace : Action()

    /**
     * if the user decide to end the app flow and continue with the questions after no smile was found.
     * transition between NoSmile and Questions state.
     */
    object QuestionButtonPressed : Action()
}
