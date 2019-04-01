package myapp.net.inspire.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.notification.receiver.RefillReceiver
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Alucard on 2/23/2019.
 */
class NotificationUtils {

    companion object {
        var i = 0
        fun setNotification(context: Context, notificationSound: String, title: String, description: String, notificationId: Int) {
            MediaPlayerUtils.playWaterSound(context, notificationSound!!)
            createNotificationChannel(context, title, description)
            val intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val pendingIntent = PendingIntent.getActivity(context, notificationId, intent, 0)

            val mBuilder = NotificationCompat.Builder(context, "jjjj")
                    .setSmallIcon(R.drawable.inspire_plus_notification)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText(description))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(false)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(notificationId, mBuilder.build())
        }

        fun setNotificationPlan(context: Context, notificationSound: String, title: String,
                                description: String, notificationId: Int, receiverIntent: Intent) {
            MediaPlayerUtils.playWaterSound(context, notificationSound!!)
            createNotificationChannel(context, title, description)
            val intent = Intent(context, MainActivity::class.java)
            var requestCode = receiverIntent.getIntExtra("requestCode", 0)
            intent.putExtra("requestCode", receiverIntent.getIntExtra("requestCode", 0))
            intent.putExtra("toolbarTitle", receiverIntent.getStringExtra("toolbarTitle"))
            intent.putExtra("title", receiverIntent.getStringExtra("title"))
            intent.putExtra("des", receiverIntent.getStringExtra("des"))
            intent.putExtra("time", receiverIntent.getStringExtra("time"))
            intent.putExtra("notifyId", notificationId)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, 0)

            val mBuilder = NotificationCompat.Builder(context, "jjjj")
                    .setSmallIcon(R.drawable.inspire_plus_notification)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText(description))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(false)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(notificationId, mBuilder.build())
        }


        fun setNotificationPlanEatBy(context: Context, notificationSound: String, title: String,
                                     description: String, notificationId: Int, receiverIntent: Intent) {
//            MediaPlayerUtils.playWaterSound(context, notificationSound!!)
            createNotificationChannel(context, title, description)
            val intent = Intent(context, MainActivity::class.java)
            var requestCode = receiverIntent.getIntExtra("requestCode", 0)
            intent.putExtra("requestCode", receiverIntent.getIntExtra("requestCode", 0))
            intent.putExtra("toolbarTitle", receiverIntent.getStringExtra("toolbarTitle"))
            intent.putExtra("title", receiverIntent.getStringExtra("title"))
            intent.putExtra("des", receiverIntent.getStringExtra("des"))
            intent.putExtra("time", receiverIntent.getStringExtra("time"))
            intent.putExtra("notifyId", notificationId)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, 0)

            val mBuilder = NotificationCompat.Builder(context, "jjjj")
                    .setSmallIcon(R.drawable.inspire_plus_notification)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText(description))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(false)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(notificationId, mBuilder.build())
        }


        fun setNotificationPlanEatByOneHour(context: Context, notificationSound: String, title: String,
                                     description: String, notificationId: Int, receiverIntent: Intent) {
            MediaPlayerUtils.playWaterSound(context, notificationSound!!)
            createNotificationChannel(context, title, description)
            val intent = Intent(context, MainActivity::class.java)
            var requestCode = receiverIntent.getIntExtra("requestCode", 0)
//            intent.putExtra("requestCode", receiverIntent.getIntExtra("requestCode", 0))
//            intent.putExtra("toolbarTitle", receiverIntent.getStringExtra("toolbarTitle"))
//            intent.putExtra("title", receiverIntent.getStringExtra("title"))
//            intent.putExtra("des", receiverIntent.getStringExtra("des"))
//            intent.putExtra("time", receiverIntent.getStringExtra("time"))
//            intent.putExtra("notifyId", notificationId)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, 0)

            val mBuilder = NotificationCompat.Builder(context, "jjjj")
                    .setSmallIcon(R.drawable.inspire_plus_notification)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText(description))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(false)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(notificationId, mBuilder.build())
        }


        private fun createNotificationChannel(context: Context, title: String, description: String) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val importance = NotificationManager.IMPORTANCE_LOW
                val channel = NotificationChannel("jjjj", title, importance)
                channel.description = description

                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }
        }


        fun setOnTimeNotificationForRefill(reminderDate: String, context: Context, requestCode: Int) {
            var alarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            var refillBroadcastReceiver = Intent(context, RefillReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", requestCode)
                PendingIntent.getBroadcast(context, Constants.REFILL_BOARDCAST_ID, intent, 0)
            }
            var calendar = Calendar.getInstance()
            var tokens = reminderDate.split("/")
            var day = tokens[0].toInt() - 8
            var month = tokens[1].toInt()
            var year = tokens[2].toInt()



            calendar.set(year, month, day)
            calendar.set(Calendar.HOUR_OF_DAY, 6)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.AM_PM, Calendar.AM)

            alarmManager?.set(
                    AlarmManager.ELAPSED_REALTIME, calendar.timeInMillis!!, refillBroadcastReceiver
            )

        }


        fun setNotificationForPlan(time: String, context: Context, weekNo: Int, requestCode: Int, pendingIntent: PendingIntent, alarmManager: AlarmManager) {
            var tokens = time.split(":")
            var hour = tokens[0]
            var tokens2 = tokens[1].split(" ")
            var mins = tokens2[0]
            var am_pm = tokens2[1]
            if (hour.length == 1) {
                hour = "0" + hour
            }

            var calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_WEEK, weekNo)
            calendar.set(Calendar.HOUR_OF_DAY, hour.toInt())
            calendar.set(Calendar.MINUTE, mins.toInt())
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            if (am_pm.equals("AM", true)) {
                calendar.set(Calendar.AM_PM, Calendar.AM)

            } else {
                calendar.set(Calendar.AM_PM, Calendar.PM)

            }


            var alarm_time = calendar.timeInMillis

            if (calendar.before(Calendar.getInstance()))
                alarm_time += AlarmManager.INTERVAL_DAY * 7



            alarmManager!!.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarm_time, AlarmManager.INTERVAL_DAY * 7, pendingIntent)


        }


        fun setNotificationForEatBy1HourBefore(time: String, context: Context, weekNo: Int, requestCode: Int, pendingIntent: PendingIntent, alarmManager: AlarmManager) {
            var tokens = time.split(":")
            var hour = tokens[0]
            var tokens2 = tokens[1].split(" ")
            var mins = tokens2[0]
            var am_pm = tokens2[1]
            if (hour.length == 1) {
                hour = "0" + hour
            }
            try {

                var dateTime = convertMinutesToHoursMins(time)


                var calendar = Calendar.getInstance()
                calendar.time=dateTime
                calendar.set(Calendar.DAY_OF_WEEK, weekNo)
                calendar.set(Calendar.HOUR_OF_DAY, -1)
//                calendar.set(Calendar.MINUTE, mins.toInt())
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

//                if (am_pm.equals("AM", true)) {
//                    calendar.set(Calendar.AM_PM, Calendar.AM)
//
//                } else {
//                    calendar.set(Calendar.AM_PM, Calendar.PM)
//
//                }


                var alarm_time = calendar.timeInMillis

                if (calendar.before(Calendar.getInstance()))
                    alarm_time += AlarmManager.INTERVAL_DAY * 7



                alarmManager!!.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarm_time, AlarmManager.INTERVAL_DAY * 7, pendingIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }


        fun setNotificationForFuturePlan(time: String, context: Context, weekNo: Int, requestCode: Int, pendingIntent: PendingIntent, alarmManager: AlarmManager) {
            var tokens = time.split(":")
            var hour = tokens[0]
            var tokens2 = tokens[1].split(" ")
            var mins = tokens2[0]
            var am_pm = tokens2[1]
            if (hour.length == 1) {
                hour = "0" + hour
            }
            try {

                var calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_WEEK, weekNo)
                calendar.set(Calendar.HOUR_OF_DAY, hour.toInt())
                calendar.set(Calendar.MINUTE, mins.toInt())
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)

                if (am_pm.equals("AM", true)) {
                    calendar.set(Calendar.AM_PM, Calendar.AM)

                } else {
                    calendar.set(Calendar.AM_PM, Calendar.PM)

                }


                var alarm_time = calendar.timeInMillis

                if (calendar.after(Calendar.getInstance()))
                    alarm_time += AlarmManager.INTERVAL_DAY * 7



                alarmManager!!.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarm_time, AlarmManager.INTERVAL_DAY * 7, pendingIntent)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun convertMinutesToHoursMins(time: String): Date {
            var sdf = SimpleDateFormat("h:mm aa");

            try {
                var dt = sdf.parse(time);
                sdf = SimpleDateFormat("HH:mm")
                return sdf.parse(sdf.format(time))
            } catch (e: Exception) {
                e.printStackTrace();

            }

            return null!!

        }
    }


}