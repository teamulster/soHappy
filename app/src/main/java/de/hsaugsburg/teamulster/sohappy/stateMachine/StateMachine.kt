package de.hsaugsburg.teamulster.sohappy.stateMachine

import java.util.*
import kotlin.properties.Delegates

class StateMachine {
    private var currentState by Delegates.observable<State>(initialValue = WaitingForFace()) { _, old, new ->
        handleStateChange(old, new)
    }

    // initialize here?
    // general question: where to put the ui staff and shall it be separated from function (face and smile detection)?
    private lateinit var timer: Timer

    private fun handleStateChange(newState: State, oldState: State) {
        when (oldState) {
            is Start -> tidyUpStartScreen()
            is WaitingForFace -> tidyUpWaitingForFaceScreen()
            is TakeABreath -> tidyUpTakeABreathScreen()
            is Stimulus -> tidyUpStimulusScreen()
            is WaitingForSmile -> tidyUpWaitingForSmileScreen()
            is SmileCountdown -> tidyUpSmileCountdownScreen()
            is Questions -> tidyUpQuestionsScreen()
            is NoSmile -> tidyUpNoSmileScreen()
        }
        when (newState) {
            is Start -> prepareStartScreen()
            is WaitingForFace -> {
                prepareWaitingForFaceScreen()
                newState.activity()
                // start Timer (10s)
            }
            is TakeABreath -> {
                prepareTakeABreathScreen()
                newState.activity()
                // start Timer (3s)
            }
            is Stimulus -> {
                prepareStimulusScreen()
                // start Timer (5s)
            }
            is WaitingForSmile -> {
                prepareWaitingForSmileScreen()
                newState.activity()
                // start Timer (10s)
            }
            is SmileCountdown -> {
                prepareSmileCountdownScreen()
                // start Timer (30s)
            }
            is Questions -> prepareQuestionsScreen()
            is NoSmile -> prepareNoSmileScreen()
        }
    }

    private fun tidyUpStartScreen() {
        TODO("Not yet implemented")
        // remove text
    }

    private fun tidyUpWaitingForFaceScreen() {
        TODO("Not yet implemented")
        // remove red filter
        // remove text
    }

    private fun tidyUpTakeABreathScreen() {
        TODO("Not yet implemented")
        // remove text
    }

    private fun tidyUpStimulusScreen() {
        TODO("Not yet implemented")
        // remove blue filter
        // remove text
    }

    private fun tidyUpWaitingForSmileScreen() {
        TODO("Not yet implemented")
        // remove yellow filter
        // remove text
    }

    private fun tidyUpSmileCountdownScreen() {
        TODO("Not yet implemented")
        // remove blue filter
        // remove text
        // remove smiley feedback
    }

    private fun tidyUpQuestionsScreen() {
        TODO("Not yet implemented")
        // remove background
        // remove text
    }

    private fun tidyUpNoSmileScreen() {
        TODO("Not yet implemented")
        // remove background
        // remove text
    }

    private fun prepareStartScreen() {
        TODO("Not yet implemented")
        // show buttons for start and menu
    }

    private fun prepareWaitingForFaceScreen() {
        TODO("Not yet implemented")
        // red filter on
        // show text
    }

    private fun prepareTakeABreathScreen() {
        TODO("Not yet implemented")
    }

    private fun prepareStimulusScreen() {
        TODO("Not yet implemented")
        // show text
    }

    private fun prepareWaitingForSmileScreen() {
        TODO("Not yet implemented")
        // yellow filter on
        // show text
    }

    private fun prepareSmileCountdownScreen() {
        TODO("Not yet implemented")
        // blue filter on
        // show text
        // show smiley feedback
    }

    private fun prepareQuestionsScreen() {
        TODO("Not yet implemented")
        // neutral background
        // show text
    }

    private fun prepareNoSmileScreen() {
        TODO("Not yet implemented")
        // neutral background
        // show text
    }

    private fun startTimer () {
        // from config or hard coded?
    }
}
