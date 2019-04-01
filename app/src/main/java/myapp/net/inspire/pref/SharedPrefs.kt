package myapp.net.inspire.pref

import android.content.Context
import android.content.SharedPreferences
import myapp.net.inspire.utils.Constants
import myapp.net.inspire.utils.DateTimeUtils

/**
 * Created by Alucard on 2/9/2019.
 */
class SharedPrefs private constructor(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    fun setPlanSetup(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getPlanSetup(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, false)
    }

    fun setWakeUpPlanSetup(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getWakeUpPlanSetup(propertyName: String): String? {
        return preferences.getString(propertyName, "7:00 AM")
    }

    fun setDoseTwoWakeUp(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getDoseTwoWakeUp(propertyName: String): String? {
        return preferences.getString(propertyName, "6:00 AM")
    }

    fun setTimeBetweenDoses(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getTimeBetweenDoses(propertyName: String): String? {
        return preferences.getString(propertyName, "6:00 AM")
    }

    fun setDose2(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getDose2(propertyName: String): String? {
        return preferences.getString(propertyName, "6:00 AM")
    }

    fun setDose1(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getDose1(propertyName: String): String? {
        return preferences.getString(propertyName, "6:00 AM")
    }

    fun setEatDinnerBy(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getEatDinnerBy(propertyName: String): String? {
        return preferences.getString(propertyName, "11:00 PM")
    }


    fun setNapOne(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getNapOne(propertyName: String): String? {
        return preferences.getString(propertyName, "-1")
    }

    fun setNapTwo(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getNapTwo(propertyName: String): String? {
        return preferences.getString(propertyName, "-1")
    }

    fun setNapThree(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getNapThree(propertyName: String): String? {
        return preferences.getString(propertyName, "-1")
    }

    fun setNapOneInterval(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getNapOneInterval(propertyName: String): String? {
        return preferences.getString(propertyName, "-1")
    }

    fun setNapTwoInterval(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getNapTwoInterval(propertyName: String): String? {
        return preferences.getString(propertyName, "-1")
    }

    fun setNapThreeInterval(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getNapThreeInterval(propertyName: String): String? {
        return preferences.getString(propertyName, "-1")
    }

    fun setEatByReminder(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getEatByReminder(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, true)
    }

    fun setDoseOneReminder(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getDoseOneReminder(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, true)
    }

    fun setNapReminder(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getNapReminder(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, true)
    }

    fun setDoseOneAlarm(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getDoseOneAlarm(propertyName: String): String? {
        return preferences.getString(propertyName, "0")
    }

    fun setDoseTwoAlarm(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getDoseTwoAlarm(propertyName: String): String? {
        return preferences.getString(propertyName, "0")
    }

    fun setWakeAlarm(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getWakeAlarm(propertyName: String): String? {
        return preferences.getString(propertyName, "0")
    }

    fun setNapStartAlarm(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getNapStartAlarm(propertyName: String): String? {
        return preferences.getString(propertyName, "0")
    }

    fun setNapEndAlarm(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getNapEndAlarm(propertyName: String): String? {
        return preferences.getString(propertyName, "0")
    }

    fun setIsSchedulePlan(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getIsSchedulePlan(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, false)
    }

    fun setIsPlanSetup(propertyName: String, propertyValue: Int) {
        preferences.edit().putInt(propertyName, propertyValue).apply()
    }

    fun getIsPlanSetup(propertyName: String): Int? {
        return preferences.getInt(propertyName, 0)
    }

    fun setIsProgress(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getIsProgress(propertyName: String): String? {
        return preferences.getString(propertyName, null)
    }

    fun setIsProgressCataplexy(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getIsProgressCataplexy(propertyName: String): String? {
        return preferences.getString(propertyName, null)
    }

    fun setStartDateRefill(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getStartDateRefill(propertyName: String): String? {
        return preferences.getString(propertyName, null)
    }

    fun setEndDateRefill(propertyName: String, propertyValue: String) {
        preferences.edit().putString(propertyName, propertyValue).apply()
    }

    fun getEndDateRefill(propertyName: String): String? {
        return preferences.getString(propertyName, null)
    }


    fun setIsRefillReminder(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getIsRefillReminder(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, true)
    }

    fun setIsFirstTimeLaunch(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getIsFirstTimeLaunch(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, true)
    }

    fun setIsFirstTimeLearn(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getIsFirstTimeLearn(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, true)
    }

    fun setIsFirstTimePlan(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getIsFirstTimePlan(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, true)
    }

    fun setIsFirstTimeTrack(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getIsFirstTimeTrack(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, true)
    }

    fun setIsDefaultNoteSave(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getIsDefaultNoteSave(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, false)
    }

    fun setIsInEDSSeverity(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun getIsInEDSSeverity(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, false)
    }

    fun setNotificationhandler(toolbarTitle: String, title: String, des: String, time: String, notifyId: Int) {
        preferences.edit().putString("toolbarTitle", toolbarTitle).apply()
        preferences.edit().putString("title", title).apply()
        preferences.edit().putString("des", des).apply()
        preferences.edit().putString("time", time).apply()
        preferences.edit().putInt("notifyId", notifyId).apply()
    }
    fun getNotificationhandler():Map<String,String>{
        var map=HashMap<String,String>()
        map.put("toolbarTitle",preferences.getString("toolbarTitle",null))
        map.put("title",preferences.getString("title",null))
        map.put("des",preferences.getString("des",null))
        map.put("time",preferences.getString("time",null))
        map.put("notifyId",preferences.getInt("notifyId",-1).toString())
        return map!!
    }

    fun setProgressTrack(propertyName: String, propertyValue: Int) {
        preferences.edit().putInt(propertyName, propertyValue).apply()
    }

    fun getProgressTrack(propertyName: String): Int? {
        return preferences.getInt(propertyName, 0)
    }


    fun setIsNotificationTriggred(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }

    fun setConsecutiveDate(date:String,counter:String,isFirst:String) {
        preferences.edit().putString("consecutive_date", date).apply()
        preferences.edit().putString("counter", counter).apply()
        preferences.edit().putString("isFirst",isFirst).apply()

    }
    fun getConsecutiveDate():Map<String,String>{
        var map=HashMap<String,String>()
        map.put("consecutive_date",preferences.getString("consecutive_date",DateTimeUtils.getCurrentDateWithoutTimeForPlan()))
        map.put("counter",preferences.getString("counter","0"))
        map.put("isFirst",preferences.getString("isFirst","1"))

        return map!!
    }

    fun setTipData(date:String,tips:String) {
        preferences.edit().putString("tips_date", date).apply()
        preferences.edit().putString("tips", tips).apply()

    }
    fun getTipsData():Map<String,String>{
        var map=HashMap<String,String>()
        map.put("tips_date",preferences.getString("tips_date",DateTimeUtils.getCurrentDateWithoutTimeForPlan()))
        map.put("tips",preferences.getString("tips", "Got progress to brag about? Share your report with your doctor right from your phone"))

        return map!!
    }



    fun get10Days(propertyName: String): Int? {
        return preferences.getInt(propertyName, 0)
    }


    fun set10Days(propertyName: String, propertyValue: Int) {
        preferences.edit().putInt(propertyName, propertyValue).apply()
    }

    fun getCurrentlySetupPlan(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, false)
    }


    fun setCurrentlySetupPlan(propertyName: String, propertyValue: Boolean) {
        preferences.edit().putBoolean(propertyName, propertyValue).apply()
    }





    fun getIsNotificationTriggred(propertyName: String): Boolean? {
        return preferences.getBoolean(propertyName, false)

    }

        companion object {
        private val sharedPrefs: SharedPrefs? = null


        @Synchronized
        fun getInstance(context: Context): SharedPrefs {
            return sharedPrefs ?: SharedPrefs(context)

        }
    }
}