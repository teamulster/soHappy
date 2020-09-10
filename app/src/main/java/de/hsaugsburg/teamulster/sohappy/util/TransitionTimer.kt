package de.hsaugsburg.teamulster.sohappy.util

import android.os.CountDownTimer
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

object TransitionTimer {
    lateinit var action : Action

    fun startTimer(sec: Long): Action {
        // TODO: do not return NewAction before changing its value
        object : CountDownTimer(sec, 1000) {

            @Suppress("EmptyFunctionBlock")
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                action = Action.Timeout
            }
        }.start()

        return action
    }
}
