package de.hsaugsburg.teamulster.sohappy.stateMachine

class WaitingForFace : State {

    override fun consumeAction(action: State.Action): State {
        TODO("Not yet implemented")
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
