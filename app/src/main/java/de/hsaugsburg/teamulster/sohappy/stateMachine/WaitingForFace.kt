package de.hsaugsburg.teamulster.sohappy.stateMachine

class WaitingForFace : State {

    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Timeout -> Start()
            is Action.FacedDetected -> TakeABreath()
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }

    override fun uiChange() {
        TODO("Not yet implemented")
    }

    override fun activity() {
        redFilterOn()
        showText()
        startFaceDetection()
    }

    private fun redFilterOn() {
        TODO("Not yet implemented")
    }

    private fun showText() {
        TODO("Not yet implemented")
    }

    private fun startFaceDetection() {
        TODO("Not yet implemented")
    }
}
