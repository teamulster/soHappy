package de.hsaugsburg.teamulster.sohappy.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import de.hsaugsburg.teamulster.sohappy.MainActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.config.ConfigManager

/**
 * NotificationHandler handles all actions concerning notifications, such as creating
 * Notification Channels or scheduling notifications to remind the user of something.
 */
class NotificationHandler(private val context: Context) {
    /**
     * Schedules an alarm telling the user to open the app every three hours.
     *
     * @param [context] Activity that is trying to schedule a notification.
     * @param [delay] Delay in milliseconds specifying when the alarm should be triggered.
     */
    fun triggerNotificationAlarm() {
        val notificationIntent = createNotificationIntent()

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val delay: Long = ConfigManager.notificationConfig.alarmDelay

        alarmManager.cancel(notificationIntent)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            SystemClock.elapsedRealtime() + delay,
            delay,
            notificationIntent
        )
    }

    /**
     * Cancels the notification alarm for this app, regardless of whether it's actually running
     * or not.
     */
    fun cancelNotificationAlarm() {
        val notificationIntent = createNotificationIntent()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(notificationIntent)
    }

    /**
     * Creates a notification Intent that reminds the user to open the app.
     *
     * @return [PendingIntent] Notification intent.
     */
    private fun createNotificationIntent(): PendingIntent {
        // Create a PendingIntent that starts the app when triggered
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(activityIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(
            context,
            context.getString(R.string.channel_id)
        ).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(context.resources.getString(R.string.notification_title))
            setContentText(context.resources.getString(R.string.notification_description))
            setContentIntent(activityPendingIntent)
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }.build()

        val scheduleIntent = Intent(context, NotificationBroadcastReceiver::class.java)
        scheduleIntent.putExtra(context.getString(R.string.notification_name), notification)

        return PendingIntent.getBroadcast(
            context,
            0,
            scheduleIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    /**
     * Starting from Android 8.0, all notifications must be associated with a Notification
     * Channel. This function's purpose is to create a Notification Channel for this app
     * if it does not already exist.
     *
     * @param [context] Activity that requests the creation of a new Notification Channel.
     */
    fun createNotificationChannel() {
        // Run the code only if Android version is at least 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.resources.getString(R.string.channel_name)
            val descriptionText = context.resources.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                context.resources.getString(R.string.channel_id),
                name,
                importance
            ).apply {
                description = descriptionText
            }

            with(NotificationManagerCompat.from(context)) {
                createNotificationChannel(channel)
            }
        }
    }
}
