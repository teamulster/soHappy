package de.hsaugsburg.teamulster.sohappy.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.R

class NotificationHandler private constructor() {
    companion object {
        fun scheduleNotification(context: Context) {
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

            /* with (NotificationManagerCompat.from(context)) {
                notify(0, builder.build())
            } */

            val scheduleIntent = Intent(context, NotificationBroadcastReceiver::class.java)
            scheduleIntent.putExtra(context.getString(R.string.notification_name), notification)

            val schedulePendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                scheduleIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                10000,
                schedulePendingIntent
            )
        }

        fun createNotificationChannel(context: Context) {
            // To ensure compatibility with newer Android versions, a notification channel has
            // to be created for notifications to be pushed into
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
