package de.hsaugsburg.teamulster.sohappy.stateMachine

class WaitingForFace : State {

    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Start()
            is Action.FacedDetected -> TakeABreath()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun activity() {
        startFaceDetection()
    }

    private fun startFaceDetection() {
        TODO("Not yet implemented")
    }
}
