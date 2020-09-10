package de.hsaugsburg.teamulster.sohappy.util

import android.os.CountDownTimer
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

object TransitionTimer {

    fun startTimer(sec: Long): Action {
        // TODO: solve problem: returns the action before changing its value
        var newAction: Action
        newAction = Action.Initial
        object : CountDownTimer(sec, 1000) {

            @Suppress("EmptyFunctionBlock")
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                newAction = Action.Timeout
            }
        }.start()

        return newAction
    }
}
