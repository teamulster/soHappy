package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores every timer used during face/smile detection.
 *
 * @property [breathTimer] amount of seconds until stimulus is shown
 * @property [stimulusTimer] amount of seconds until wait for smile screen is shown
 * @property [waitingForSmileTimer] amount of seconds while a user has to start smiling
 * @property [waitingForFaceTimer] amount of seconds while a users face has to be detected
 * @property [smileTimer] amount of seconds a smile should endure
 * */
data class TimerConfig(
    val breathTimer: Long,
    val stimulusTimer: Long,
    val waitingForSmileTimer: Long,
    val waitingForFaceTimer: Long,
    val smileTimer: Long
) {
    constructor(builder: Builder) : this(
        builder.breathTimer!!,
        builder.stimulusTimer!!,
        builder.waitingForSmileTimer!!,
        builder.waitingForFaceTimer!!,
        builder.smileTimer!!
    )

    /**
     * This class implements a [Builder] for [TimerConfig].
     * */
    class Builder(
        var breathTimer: Long? = null,
        var stimulusTimer: Long? = null,
        var waitingForSmileTimer: Long? = null,
        var waitingForFaceTimer: Long? = null,
        var smileTimer: Long? = null
    ) {
        /**
         * This function sets a value for [breathTimer].
         * It is mandatory to call this function before building.
         *
         * @param [breathTimer]
         * */
        fun setBreathTimer(breathTimer: Long) = apply { this.breathTimer = breathTimer }

        /**
         * This function sets a value for [stimulusTimer].
         * It is mandatory to call this function before building.
         *
         * @param [stimulusTimer]
         * */
        fun setStimulusTimer(stimulusTimer: Long) = apply { this.stimulusTimer = stimulusTimer }

        /**
         * This function sets a value for [waitingForSmileTimer].
         * It is mandatory to call this function before building.
         *
         * @param [waitingForSmileTimer]
         * */
        fun setWaitingSmileTimer(waitingForSmileTimer: Long) =
            apply { this.waitingForSmileTimer = waitingForSmileTimer }

        /**
         * This function sets a value for [waitingForFaceTimer].
         * It is mandatory to call this function before building.
         *
         * @param [waitingForFaceTimer]
         * */
        fun setWaitingFaceTimer(waitingForFaceTimer: Long) =
            apply { this.waitingForFaceTimer = waitingForFaceTimer }

        /**
         * This function sets a value for [smileTimer].
         * It is mandatory to call this function before building.
         *
         * @param [smileTimer]
         * */
        fun setSmileTimer(smileTimer: Long) = apply { this.smileTimer = smileTimer }

        /**
         * This function calls the [TimerConfig] constructor.
         *
         * @return [TimerConfig] instance
         * */
        fun build() = TimerConfig(
            breathTimer!!,
            stimulusTimer!!,
            waitingForSmileTimer!!,
            waitingForFaceTimer!!,
            smileTimer!!
        )
    }
}
