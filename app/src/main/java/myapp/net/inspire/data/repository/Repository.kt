package myapp.net.inspire.data.repository

import android.app.Activity
import android.content.Context
import myapp.net.inspire.pref.SharedPrefs
import myapp.net.inspire.utils.Constants

/**
 * Created by QPay on 2/12/2019.
 */
class Repository() {
    lateinit var activity: Activity

    constructor(activity: Activity) : this() {
        this.activity = activity
    }


    fun getPlanSetup(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getPlanSetup(Constants.PLAN_SETUP)
    }

    fun setPlanSetup(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setPlanSetup(Constants.PLAN_SETUP, property)
    }

    fun getWakeUpPlanSetup(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getWakeUpPlanSetup(Constants.WAKEUP_TIME)
    }

    fun setWakeUpPlanSetup(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setWakeUpPlanSetup(Constants.WAKEUP_TIME, property)
    }

    fun getDoseTwoWakeUp(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getDoseTwoWakeUp(Constants.DOSETWO_WAKEUP)
    }

    fun setDoseTwoWakeUp(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setDoseTwoWakeUp(Constants.DOSETWO_WAKEUP, property)
    }

    fun getTimeBetweenDoses(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getTimeBetweenDoses(Constants.TIME_BETWEEN_DOSES)
    }

    fun setTimeBetweenDoses(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setTimeBetweenDoses(Constants.TIME_BETWEEN_DOSES, property)
    }


    fun getDose2(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getDose2(Constants.DOSE_2)
    }

    fun setDose2(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setDose2(Constants.DOSE_2, property)
    }


    fun getDose1(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getDose1(Constants.DOSE_1)
    }

    fun setDose1(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setDose1(Constants.DOSE_1, property)
    }

    fun getEatDinnerBy(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getEatDinnerBy(Constants.EAT_DINNER_BY)
    }

    fun setEatDinnerBy(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setEatDinnerBy(Constants.EAT_DINNER_BY, property)
    }

    fun getNapOne(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getNapOne(Constants.NAP_1)
    }

    fun setNapOne(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setNapOne(Constants.NAP_1, property)
    }

    fun getNapTwo(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getNapTwo(Constants.NAP_2)
    }

    fun setNapTwo(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setNapTwo(Constants.NAP_2, property)
    }

    fun getNapThree(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getNapThree(Constants.NAP_3)
    }

    fun setNapThree(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setNapThree(Constants.NAP_3, property)
    }

    fun getNapOneInterval(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getNapOneInterval(Constants.NAP_1_INTERVAL)
    }

    fun setNapOneInterval(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setNapOneInterval(Constants.NAP_1_INTERVAL, property)
    }

    fun getNapTwoInterval(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getNapTwoInterval(Constants.NAP_2_INTERVAL)
    }

    fun setNapTwoInterval(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setNapTwoInterval(Constants.NAP_2_INTERVAL, property)
    }

    fun getNapThreeInterval(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getNapThreeInterval(Constants.NAP_3_INTERVAL)
    }

    fun setNapThreeInterval(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setNapThreeInterval(Constants.NAP_3_INTERVAL, property)
    }

    fun getEatByReminder(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getEatByReminder(Constants.IS_EATBY_REMINDER)
    }

    fun setEatByReminder(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setEatByReminder(Constants.IS_EATBY_REMINDER, property)
    }

    fun getDoseOneReminder(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getDoseOneReminder(Constants.IS_DOSE1_REMINDER)
    }

    fun setDoseOneReminder(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setDoseOneReminder(Constants.IS_DOSE1_REMINDER, property)
    }

    fun getNapReminder(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getNapReminder(Constants.IS_NAP_REMINDER)
    }

    fun setNapReminder(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setNapReminder(Constants.IS_NAP_REMINDER, property)
    }

    fun getDoseOneAlarm(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getDoseOneAlarm(Constants.DOSE1_ALARM_SOUND)
    }

    fun setDoseOneAlarm(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setDoseOneAlarm(Constants.DOSE1_ALARM_SOUND, property)
    }

    fun setDoseTwoAlarm(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setDoseTwoAlarm(Constants.DOSE2_ALARM_SOUND, property)
    }

    fun getDoseTwoAlarm(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getDoseTwoAlarm(Constants.DOSE2_ALARM_SOUND)
    }


    fun getWakeAlarm(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getWakeAlarm(Constants.WAKE_ALARM_SOUND)
    }

    fun setWakeAlarm(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setWakeAlarm(Constants.WAKE_ALARM_SOUND, property)
    }

    fun getNapStartAlarm(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getNapStartAlarm(Constants.NAP_START_ALARM_SOUND)
    }

    fun setNapStartAlarm(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setNapStartAlarm(Constants.NAP_START_ALARM_SOUND, property)
    }

    fun getNapEndAlarm(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getNapEndAlarm(Constants.NAP_END_ALARM_SOUND)
    }

    fun setNapEndAlarm(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setNapEndAlarm(Constants.NAP_END_ALARM_SOUND, property)
    }

    fun getIsSchedulePlan(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsSchedulePlan(Constants.IS_SCHEDULE_PLAN)
    }

    fun setIsSchedulePlan(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsSchedulePlan(Constants.IS_SCHEDULE_PLAN, property)
    }

    fun getIsPlanSetup(context: Context): Int? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsPlanSetup(Constants.IS_PLAN_SETUP)
    }

    fun setIsPlanSetup(context: Context, property: Int) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsPlanSetup(Constants.IS_PLAN_SETUP, property)
    }

    fun getIsProgress(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsProgress(Constants.IS_PROGRESS_SETUP)
    }

    fun setIsProgress(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsProgress(Constants.IS_PROGRESS_SETUP, property)
    }

    fun getIsProgressCataplexy(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsProgressCataplexy(Constants.IS_PROGRESS_SETUP_CATAPLEXY)
    }

    fun setIsProgressCataplexy(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsProgressCataplexy(Constants.IS_PROGRESS_SETUP_CATAPLEXY, property)
    }

    fun getStartDateRefill(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getStartDateRefill(Constants.START_DATE_REFILL)
    }

    fun setStartDateRefill(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setStartDateRefill(Constants.START_DATE_REFILL, property)
    }

    fun getEndDateRefill(context: Context): String? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getEndDateRefill(Constants.END_DATE_REFILL)
    }

    fun setEndDateRefill(context: Context, property: String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setEndDateRefill(Constants.END_DATE_REFILL, property)
    }

    fun getIsRefillReminder(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsRefillReminder(Constants.IS_REFILL_REMINDER)
    }

    fun setIsRefillReminder(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsRefillReminder(Constants.IS_REFILL_REMINDER, property)
    }

    fun getIsFirstTimeLaunch(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsFirstTimeLaunch(Constants.IS_FIRST_TIME)
    }

    fun setIsFirstTimeLaunch(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsFirstTimeLaunch(Constants.IS_FIRST_TIME, property)
    }


    fun getIsFirstTimeLearn(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsFirstTimeLearn(Constants.IS_FIRST_TIME_LEARN)
    }

    fun setIsFirstTimeLearn(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsFirstTimeLearn(Constants.IS_FIRST_TIME_LEARN, property)
    }

    fun getIsFirstTimePlan(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsFirstTimeLearn(Constants.IS_FIRST_TIME_PLAN)
    }

    fun setIsFirstTimePlan(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsFirstTimeLearn(Constants.IS_FIRST_TIME_PLAN, property)
    }

    fun getIsFirstTimeTrack(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsFirstTimeLearn(Constants.IS_FIRST_TIME_TRACK)
    }

    fun setIsFirstTimeTrack(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsFirstTimeLearn(Constants.IS_FIRST_TIME_TRACK, property)
    }

    fun getIsDefaultNoteSave(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsDefaultNoteSave(Constants.IS_DEFAULT_NOTE_SAVE)
    }

    fun setIsDefaultNoteSave(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsDefaultNoteSave(Constants.IS_DEFAULT_NOTE_SAVE, property)
    }

    fun setIsInEDSSeverity(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsInEDSSeverity(Constants.IS_IN_PROGRESS_EDS, property)
    }

    fun getIsInEDSSeverity(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsInEDSSeverity(Constants.IS_IN_PROGRESS_EDS)
    }

    fun setNotificationHandler(context: Context, toolbarTitle: String, title: String, des: String, time: String, notifyId: Int) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setNotificationhandler(toolbarTitle, title, des, time, notifyId)
    }

    fun getNotificationHandler(context: Context): Map<String, String> {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getNotificationhandler()
    }


    fun setIsNotificationTriggred(context: Context, property: Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setIsNotificationTriggred(Constants.NOTIFICATION_HANDLER, property)
    }

    fun getIsNotificationTriggred(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getIsNotificationTriggred(Constants.NOTIFICATION_HANDLER)
    }

    fun setProgressTrack(context: Context, property: Int) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setProgressTrack(Constants.PROGRESS_TRACK, property)
    }

    fun getProgressTrack(context: Context): Int? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getProgressTrack(Constants.PROGRESS_TRACK)
    }

    fun setConsecutiveDate(context: Context,date:String,counter:String,isFirst:String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setConsecutiveDate(date,counter,isFirst)
    }

    fun getConsecutiveDate(context: Context): Map<String, String> {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getConsecutiveDate()
    }

    fun set10Days(context: Context, property: Int) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setProgressTrack(Constants.DAYS_10, property)
    }

    fun get10Days(context: Context): Int? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getProgressTrack(Constants.DAYS_10)
    }

    fun setCurrentlySetupPlan(context: Context,plan:Boolean) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setCurrentlySetupPlan(Constants.IS_CURRENTLY_SETUP_PLAN,plan)
    }

    fun getCurrentlySetupPlan(context: Context): Boolean? {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getCurrentlySetupPlan(Constants.IS_CURRENTLY_SETUP_PLAN)
    }

    fun setTipsData(context: Context,date:String,tips:String) {
        val prefs = SharedPrefs.getInstance(context)
        prefs.setTipData(date,tips)
    }

    fun getTipsData(context: Context): Map<String, String> {
        val prefs = SharedPrefs.getInstance(context)
        return prefs.getTipsData()
    }


}