package myapp.net.inspire.utils

import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import myapp.net.inspire.data.repository.Repository
import java.io.BufferedInputStream
import java.io.IOException


/**
 * Created by Alucard on 2/9/2019.
 */
class GeneralUtils {

    companion object {
        fun getColorWrapper(context: Context, id: Int): Int {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return context.getColor(id)
            } else {
                return context.resources.getColor(id)
            }
        }

        fun getPosition(sounds: Array<Int>, drawable: Int): Int {
            for (i in 0..sounds.size) {
                if (sounds[i] == drawable) {
                    return i
                }
            }
            return -1
        }

        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm!!.hideSoftInputFromWindow(view!!.windowToken, 0)
        }

        @Throws(IOException::class)
        fun getAssetImage(context: Context, filename: String): Drawable {
            val assets = context.resources.assets
            val buffer = BufferedInputStream(assets.open("drawable/" + filename))
            val bitmap = BitmapFactory.decodeStream(buffer)
            return BitmapDrawable(context.resources, bitmap)
        }

        fun getUserEmail(context: Context): String {
            val manager = AccountManager.get(context)
            val accounts = manager.getAccountsByType("com.google")
            if (accounts != null) {
                return accounts!!.toString()
            } else {
                return ""
            }
        }


        fun resetPlanSetup(activity: Context) {
            Repository().setWakeAlarm(activity!!, "7:00 AM")
            Repository().setDose1(activity!!, "6:00 AM")
            Repository().setDose2(activity!!, "6:00 AM")
            Repository().setNapOne(activity!!, "-1")
            Repository().setNapTwo(activity!!, "-1")
            Repository().setNapThree(activity!!, "-1")
            Repository().setDoseOneAlarm(activity!!, "0")
            Repository().setDoseTwoAlarm(activity!!, "0")
            Repository().setWakeAlarm(activity!!, "0")
            Repository().setNapStartAlarm(activity!!, "0")
            Repository().setNapEndAlarm(activity!!, "0")
            Repository().setEatByReminder(activity!!, true)
            Repository().setDoseOneReminder(activity!!, true)
            Repository().setNapReminder(activity!!, true)

            AppCache.wakeUpTime = "7:00 AM"
            AppCache.doseTwoWakeUp = 18
            AppCache.timeBetweenDoses = 18
            AppCache.napOne = "-1"
            AppCache.napTwo = "-1"
            AppCache.napThree = "-1"
            AppCache.eatBy = true
            AppCache.doseOne = true
            AppCache.napReminder = true
            AppCache.dose1 = 0
            AppCache.dose2 = 0
            AppCache.wake = 0
            AppCache.napStart = 0
            AppCache.napEnd = 0
        }


    }
}