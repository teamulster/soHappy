package de.hsaugsburg.teamulster.sohappy.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Global.getString
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.R

class NotificationHandler() {
    companion object {
        fun pushNotification(context: Context) {
            val resultIntent = Intent(context, CameraActivity::class.java)
            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(resultIntent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            val builder = NotificationCompat.Builder(
                context, context.getString(R.string.channel_id)
            ).apply {
                setSmallIcon(R.drawable.ic_launcher_background)
                setContentTitle("Test Title")
                setContentText("Test Text")
                setContentIntent(resultPendingIntent)
                setAutoCancel(true)
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }

            with (NotificationManagerCompat.from(context)) {
                notify(0, builder.build())
            }
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