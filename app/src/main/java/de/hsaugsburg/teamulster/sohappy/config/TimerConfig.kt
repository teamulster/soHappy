package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores every timer used during face/smile detection.
 *
 * @property [breathTimer] amount of seconds until stimulus is shown
 * @property [stimulusTimer] amount of seconds until wait for smile screen is shown
 * @property [waitingForSmileTimer] amount of seconds a user has to start smiling
 * @property [smileTimer] amount of seconds a smile should endure
 * */
data class TimerConfig(
    val breathTimer: Long, val stimulusTimer: Long,
    val waitingForSmileTimer: Long, val waitingForFaceTimer: Long, val smileTimer: Long
)
