package myapp.net.inspire.notification.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import myapp.net.inspire.R
import myapp.net.inspire.utils.NotificationUtils

/**
 * Created by Alucard on 2/23/2019.
 */
class RefillReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var requestCode = intent!!.getIntExtra("requestCode", 0)
        val pm = context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                        or PowerManager.FULL_WAKE_LOCK
                        or PowerManager.ACQUIRE_CAUSES_WAKEUP, "")

        wl.acquire()
        NotificationUtils.setNotification(context!!, R.raw.inspire.toString(), "Refill Reminder",
                "This is your refill reminder 7 day supply remaining", requestCode)

    }
}