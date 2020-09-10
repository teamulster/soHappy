package de.hsaugsburg.teamulster.sohappy.util

import android.os.CountDownTimer
import de.hsaugsburg.teamulster.sohappy.stateMachine.Action

object TransitionTimer {

    fun startTimer(sec: Long): Action.Timeout {
        var isTimeOut = false
        object : CountDownTimer(sec, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                TODO("Not yet implemented")
            }

            override fun onFinish() {
                isTimeOut = true
            }
        }.start()
        while (!isTimeOut) {
            if (isTimeOut)
                return Action.Timeout
        }
    }
}
