package de.hsaugsburg.teamulster.sohappy.notification

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import de.hsaugsburg.teamulster.sohappy.R

class NotificationBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notification = intent.getParcelableExtra<Notification>(
            context.getString(R.string.notification_name)
        )
        if (notification != null) {
            with(NotificationManagerCompat.from(context)) {
                notify(0, notification)
            }
        }
    }
}
