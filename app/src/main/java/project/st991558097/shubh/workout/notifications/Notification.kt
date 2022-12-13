package project.st991558097.shubh.workout.notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import project.st991558097.shubh.R

const val notificationId = 1
const val channelId = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification: BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {

        Log.d("Inside notification", "true")

        val notification = NotificationCompat.Builder(p0, channelId)
            .setSmallIcon(R.drawable.icon_calendar)
            .setContentTitle(p1.getStringExtra(titleExtra))
            .setContentText(p1.getStringExtra(messageExtra))
            .build()

        val manager = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)
    }
}