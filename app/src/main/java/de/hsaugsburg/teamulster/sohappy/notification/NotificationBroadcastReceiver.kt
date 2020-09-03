package de.hsaugsburg.teamulster.sohappy.notification

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import de.hsaugsburg.teamulster.sohappy.R

/**
 * Receives notification alarms from NotificationHandler and uses a NotificationManager
 * to push the notification to the user's device.
 */
class NotificationBroadcastReceiver: BroadcastReceiver() {

    /**
     * Upon receiving an alarm from NotificationHandler, the corresponding notification is
     * retrieved using its intent and pushed to the user's device.
     *
     * @param context Activity that sent the notification
     * @param intent Intent that was attached to the alarm
     */
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
