package myapp.net.inspire.notification.receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.AlarmSoundRepository
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.Constants
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.NotificationUtils


/**
 * Created by QPay on 2/24/2019.
 */
class PlanReceiver : BroadcastReceiver() {
    private val soundDrawable = arrayOf(R.raw.alarm_clock, R.raw.soft_alarm_clock, R.raw.heartbeat, R.raw.sunrise, R.raw.jingle, R.raw.inspire, R.raw.marimba, R.raw.guitar)

    override fun onReceive(context: Context?, intent: Intent?) {
        var currentTime = DateTimeUtils.getCurrentTimeAlarm().toUpperCase()
        var requestCode = intent!!.getIntExtra("requestCode", 0)
        var notificationId = intent!!.getIntExtra("notificationId", 0)
        var fireTime = intent!!.getStringExtra("time")
        var mSoundRepository = AlarmSoundRepository(context!!)
        var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())
        var sound = mSoundRepository.getAlarmSoundById(day)


        intent.putExtra("requestCode", requestCode)

        val pm = context
                .getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                        or PowerManager.FULL_WAKE_LOCK
                        or PowerManager.ACQUIRE_CAUSES_WAKEUP, "")

        wl.acquire()
        Repository().setIsNotificationTriggred(context, true)

        when (requestCode) {
            Constants.SUNDAY_EATBY_ONE_HOUR_REQUEST_CODE,
            Constants.MONDAY_EATBY_ONE_HOUR_REQUEST_CODE,
            Constants.TUESDAY_EATBY_ONE_HOUR_REQUEST_CODE,
            Constants.WEDNESDAY_EATBY_ONE_HOUR_REQUEST_CODE,
            Constants.THURSDAY_EATBY_ONE_HOUR_REQUEST_CODE,
            Constants.FRIDAY_EATBY_ONE_HOUR_REQUEST_CODE,
            Constants.SATURDAY_EATBY_ONE_HOUR_REQUEST_CODE->{
                NotificationUtils.setNotificationPlanEatByOneHour(context!!, R.raw.inspire.toString(), "Alarm! It's " + currentTime,
                        "You have dinner after 1 hour", notificationId, intent!!)
            }



            Constants.SUNDAY_EATBY_REQUEST_CODE,
            Constants.MONDAY_EATBY_REQUEST_CODE,
            Constants.TUESDAY_EATBY_REQUEST_CODE,
            Constants.WEDNESDAY_EATBY_REQUEST_CODE,
            Constants.THURSDAY_EATBY_REQUEST_CODE,
            Constants.FRIDAY_EATBY_REQUEST_CODE,
            Constants.SATURDAY_EATBY_REQUEST_CODE -> {


                intent.putExtra("toolbarTitle", "Eat By")
                intent.putExtra("title", "Eat By taken")
                intent.putExtra("des", "Eat by")
                intent.putExtra("time", currentTime)
                intent.putExtra("requestCode", requestCode)
                Repository().setNotificationHandler(context, "Eat By", "Eat By taken", "Eat by", currentTime, requestCode)

                NotificationUtils.setNotificationPlanEatBy(context!!, R.raw.inspire.toString(), "Alarm! It's " + currentTime,
                        "It's time for dinner.", notificationId, intent!!)

            }

            Constants.SUNDAY_DOSE1_REQUEST_CODE,
            Constants.MONDAY_DOSE1_REQUEST_CODE,
            Constants.TUESDAY_DOSE1_REQUEST_CODE,
            Constants.WEDNESDAY_DOSE1_REQUEST_CODE,
            Constants.THURSDAY_DOSE1_REQUEST_CODE,
            Constants.FRIDAY_DOSE1_REQUEST_CODE,
            Constants.SATURDAY_DOSE1_REQUEST_CODE -> {
                intent.putExtra("toolbarTitle", "Dose 1")
                intent.putExtra("title", "Dose 1")
                intent.putExtra("des", "Dose Taken")
                intent.putExtra("time", currentTime)
                intent.putExtra("requestCode", requestCode)
                Repository().setNotificationHandler(context, "Dose 1", "Dose 1", "Dose Taken", currentTime, requestCode)
                NotificationUtils.setNotificationPlan(context!!, soundDrawable.get(sound.soundDoseOne.toInt()).toString(), "Alarm! It's " + currentTime,
                        "It's time to take Dose 1 ", notificationId, intent!!)


            }

            Constants.SUNDAY_DOSE2_REQUEST_CODE,
            Constants.MONDAY_DOSE2_REQUEST_CODE,
            Constants.TUESDAY_DOSE2_REQUEST_CODE,
            Constants.WEDNESDAY_DOSE2_REQUEST_CODE,
            Constants.THURSDAY_DOSE2_REQUEST_CODE,
            Constants.FRIDAY_DOSE2_REQUEST_CODE,
            Constants.SATURDAY_DOSE2_REQUEST_CODE -> {
                intent.putExtra("toolbarTitle", "Dose 2")
                intent.putExtra("title", "Dose 2")
                intent.putExtra("des", "Dose Taken")
                intent.putExtra("time", currentTime)
                intent.putExtra("requestCode", requestCode)
                Repository().setNotificationHandler(context, "Dose 2", "Dose 2", "Dose Taken", currentTime, requestCode)
                NotificationUtils.setNotificationPlan(context!!, soundDrawable.get(sound.soundDoseTwo.toInt()).toString(), "Alarm! It's " + currentTime,
                        "It's time to take Dose 2", notificationId, intent!!)


            }

            Constants.SUNDAY_NAP1_REQUEST_CODE,
            Constants.MONDAY_NAP1_REQUEST_CODE,
            Constants.TUESDAY_NAP1_REQUEST_CODE,
            Constants.WEDNESDAY_NAP1_REQUEST_CODE,
            Constants.THURSDAY_NAP1_REQUEST_CODE,
            Constants.FRIDAY_NAP1_REQUEST_CODE,
            Constants.SATURDAY_NAP1_REQUEST_CODE -> {
                intent.putExtra("toolbarTitle", "Nap")
                intent.putExtra("title", "Nap 1 taken")
                intent.putExtra("des", "Nap 1")
                intent.putExtra("time", currentTime)
                intent.putExtra("requestCode", requestCode)
                Repository().setNotificationHandler(context, "Nap", "Nap 1 taken", "Nap 1", currentTime, requestCode)
                NotificationUtils.setNotificationPlan(context!!, soundDrawable.get(sound.soundNapStart.toInt()).toString(), "Alarm! It's " + currentTime,
                        "It's time to take Nap 1", notificationId, intent!!)

            }

            Constants.SUNDAY_NAP2_REQUEST_CODE,
            Constants.MONDAY_NAP2_REQUEST_CODE,
            Constants.TUESDAY_NAP2_REQUEST_CODE,
            Constants.WEDNESDAY_NAP2_REQUEST_CODE,
            Constants.THURSDAY_NAP2_REQUEST_CODE,
            Constants.FRIDAY_NAP2_REQUEST_CODE,
            Constants.SATURDAY_NAP2_REQUEST_CODE -> {
                intent.putExtra("toolbarTitle", "Nap")
                intent.putExtra("title", "Nap 2 taken")
                intent.putExtra("des", "Nap 2")
                intent.putExtra("time", currentTime)
                intent.putExtra("requestCode", requestCode)
                Repository().setNotificationHandler(context, "Nap", "Nap 2 taken", "Nap 2", currentTime, requestCode)
                NotificationUtils.setNotificationPlan(context!!, soundDrawable.get(sound.soundNapStart.toInt()).toString(), "Alarm! It's " + currentTime,
                        "It's time to take Nap 2", notificationId, intent!!)


            }

            Constants.SUNDAY_NAP3_REQUEST_CODE,
            Constants.MONDAY_NAP3_REQUEST_CODE,
            Constants.TUESDAY_NAP3_REQUEST_CODE,
            Constants.WEDNESDAY_NAP3_REQUEST_CODE,
            Constants.THURSDAY_NAP3_REQUEST_CODE,
            Constants.FRIDAY_NAP3_REQUEST_CODE,
            Constants.SATURDAY_NAP3_REQUEST_CODE -> {
                intent.putExtra("toolbarTitle", "Nap")
                intent.putExtra("title", "Nap 3 taken")
                intent.putExtra("des", "Nap 3")
                intent.putExtra("time", currentTime)
                intent.putExtra("requestCode", requestCode)
                Repository().setNotificationHandler(context, "Nap", "Nap 3 taken", "Nap 3", currentTime, requestCode)
                NotificationUtils.setNotificationPlan(context!!, soundDrawable.get(sound.soundNapStart.toInt()).toString(), "Alarm! It's " + currentTime,
                        "It's time to take Nap 3", notificationId, intent!!)


            }


            Constants.SUNDAY_WAKE_REQUEST_CODE,
            Constants.MONDAY_WAKE_REQUEST_CODE,
            Constants.TUESDAY_WAKE_REQUEST_CODE,
            Constants.WEDNESDAY_WAKE_REQUEST_CODE,
            Constants.THURSDAY_WAKE_REQUEST_CODE,
            Constants.FRIDAY_WAKE_REQUEST_CODE,
            Constants.SATURDAY_WAKE_REQUEST_CODE -> {

                intent.putExtra("toolbarTitle", "Wake up")
                intent.putExtra("title", "Wake up ")
                intent.putExtra("des", "Wake up")
                intent.putExtra("time", currentTime)
                intent.putExtra("requestCode", requestCode)
                Repository().setNotificationHandler(context, "Wake up", "Wake up", "Wake up", currentTime, requestCode)
                NotificationUtils.setNotificationPlan(context!!, soundDrawable.get(sound.soundWakeup.toInt()).toString(), "Alarm! It's " + currentTime,
                        "It's time to Wake Up", notificationId, intent!!)

            }


        }


    }
}