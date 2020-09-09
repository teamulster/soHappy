package de.hsaugsburg.teamulster.sohappy.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.R

/**
 * NotificationHandler handles all actions concerning notifications, such as creating
 * Notification Channels or scheduling notifications to remind the user of something.
 */
class NotificationHandler private constructor() {
    companion object {

        /**
         * Schedules a notification telling the user to open the app after the provided
         * delay.
         *
         * @param context Activity that is trying to schedule a notification.
         * @param delay Delay in milliseconds specifying when the alarm should be triggered.
         */
        fun scheduleNotification(context: Context, delay: Long) {
            // Create a PendingIntent that starts the app when triggered
            val activityIntent = Intent(context, CameraActivity::class.java)
            val activityPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(activityIntent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            val notification = NotificationCompat.Builder(
                context, context.getString(R.string.channel_id)
            ).apply {
                setSmallIcon(R.drawable.ic_launcher_background)
                setContentTitle("Test Title")
                setContentText("Test Text")
                setContentIntent(activityPendingIntent)
                setAutoCancel(true)
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }.build()

            // Schedule the notification using a PendingIntent that is passed to the system's
            // AlarmManager and triggered after a certain delay
            val scheduleTime = SystemClock.elapsedRealtime() + delay
            val scheduleIntent = Intent(context, NotificationBroadcastReceiver::class.java)
            scheduleIntent.putExtra(context.getString(R.string.notification_name), notification)

            val schedulePendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                scheduleIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                scheduleTime,
                schedulePendingIntent
            )
        }

        /**
         * Starting from Android 8.0, all notifications must be associated with a Notification
         * Channel. This function's purpose is to create a Notification Channel for this app
         * if it does not already exist.
         *
         * @param context Activity that requests the creation of a new Notification Channel.
         */
        fun createNotificationChannel(context: Context) {
            // Run the code only if Android version is at least 8.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.resources.getString(R.string.channel_name)
                val descriptionText = context.resources.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(
                    context.resources.getString(R.string.channel_id), name, importance
                ).apply {
                    description = descriptionText
                }

                with(NotificationManagerCompat.from(context)) {
                    createNotificationChannel(channel)
                }
            }
        }
    }
}
