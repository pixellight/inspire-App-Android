package myapp.net.inspire.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.utils.MediaPlayerUtils


/**
 * Created by QPay on 2/22/2019.
 */
class NotificationReceiver : BroadcastReceiver() {
    private var manager: NotificationManager? = null
    val NOTIFY_id = 11
    override fun onReceive(context: Context, intent: Intent) {
        setNotification(context, "Eat By")
        manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val action = intent.action
        if (action == "Drink") {
            performAction1(context)
        } else if (action == "Snooze") {
            performAction2(context)

        }

    }

    fun performAction1(context: Context) {
        val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        context.sendBroadcast(it)
        manager!!.cancel(NOTIFY_id)
        context.startActivity(Intent(context, MainActivity::class.java))
    }

    fun performAction2(context: Context) {
        val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        context.sendBroadcast(it)
        manager!!.cancel(NOTIFY_id)
    }

    fun setNotification(context: Context, title: String) {
        MediaPlayerUtils.playWaterSound(context, "")
        createNotificationChannel(context, title)
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val drinkWaterIntent = Intent(context, NotificationReceiver::class.java)
        drinkWaterIntent.action = "Drink"
        drinkWaterIntent.putExtra("water", "0")
        val drinkWaterPendingIntent = PendingIntent.getBroadcast(context, 1, drinkWaterIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val snoozeIntent = Intent(context, NotificationReceiver::class.java)
        snoozeIntent.action = "Snooze"
        snoozeIntent.putExtra("water", "1")
        val snoozePendingIntent = PendingIntent.getBroadcast(context, 2, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val mBuilder = NotificationCompat.Builder(context, "jjjj")
                .setSmallIcon(R.drawable.info_icon)
                .setContentTitle(title)
                .setContentText("Plan")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("Plan"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.id.icon, "Cancel", snoozePendingIntent)
                .addAction(R.id.icon, "Ok", drinkWaterPendingIntent)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setOngoing(false)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFY_id, mBuilder.build())
    }

    private fun createNotificationChannel(context: Context, title: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = title
            val description = "Plan"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("jjjj", name, importance)
            channel.description = description

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }

}