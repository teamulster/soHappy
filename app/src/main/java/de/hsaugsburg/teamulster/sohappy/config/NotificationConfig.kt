package de.hsaugsburg.teamulster.sohappy.config

/**
 * This data class stores config for NotificationHandler.
 *
 * @property [alarmDelay]
 * */
data class NotificationConfig(val alarmDelay: Long) {
    constructor(builder: Builder) : this(builder.alarmDelay!!)

    /**
     * This class implements a [Builder] for [NotificationConfig].
     * */
    class Builder(var alarmDelay: Long? = null) {
        /**
         * This function sets a value for [alarmDelay].
         * It is mandatory to call this function before building.
         *
         * @param [alarmDelay]
         * */
        fun setAlarmDelay(alarmDelay: Long) = apply { this.alarmDelay = alarmDelay }

        /**
         * This function calls the [NotificationConfig] constructor.
         *
         * @return [NotificationConfig] instance
         * */
        fun build() = NotificationConfig(alarmDelay!!)
    }
}
