package myapp.net.inspire.plan

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.*
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.fragment_plansetup_ten.*
import kotlinx.android.synthetic.main.planset_picker_friday.*
import kotlinx.android.synthetic.main.planset_picker_monday.*
import kotlinx.android.synthetic.main.planset_picker_saturday.*
import kotlinx.android.synthetic.main.planset_picker_sunday.*
import kotlinx.android.synthetic.main.planset_picker_thursday.*
import kotlinx.android.synthetic.main.planset_picker_tuesday.*
import kotlinx.android.synthetic.main.planset_picker_wednesday.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.alarm.AlarmFragment
import myapp.net.inspire.data.entity.*
import myapp.net.inspire.data.repository.*
import myapp.net.inspire.fragment.MainFragment
import myapp.net.inspire.nap.NapFragment
import myapp.net.inspire.notification.receiver.PlanReceiver
import myapp.net.inspire.reminder.ReminderFragment
import myapp.net.inspire.utils.*
import java.util.*


/**
 * Created by QPay on 2/14/2019.
 */
class PlanSetupTenFragment : Fragment() {

    companion object {
        val TAG = "PlanSetupTenFragment"
    }

    private var color = R.color.darkThemeBackground
    private var eatByPref: String? = null
    private var doseOnePref: String? = null
    private var doseTwoPref: String? = null
    private var wakePref: String? = null

    private var isSunlayout: Boolean? = false
    private var isMonlayout: Boolean? = false
    private var isTuelayout: Boolean? = false
    private var isWedlayout: Boolean? = false
    private var isThurlayout: Boolean? = false
    private var isFrilayout: Boolean? = false
    private var isSatlayout: Boolean? = false
    private var mPlanRepository: PlanRepository? = null
    private var isPlanSetup: Boolean? = false
    private var cancel: TextView? = null
    private var topicTitle: TextView? = null
    private val TIME_PICKER_INTERVAL = 5

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_plansetup_ten, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topicTitle = (activity as AppCompatActivity).findViewById<TextView>(R.id.title)
        var reset = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        cancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.cross)
        try {


            reset.text = getString(R.string.reset)
            cancel!!.setOnClickListener {

                startActivity(Intent(activity!!, MainActivity::class.java))
                activity!!.finishAffinity()
            }

            reset.setOnClickListener {
                dialogReset()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        isPlanSetup = arguments.getBoolean("isSetup")
        if (isPlanSetup!!) {
            topicTitle!!.text = "PLAN SETUP [10/10]"
            var leftCancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
            leftCancel.text = "X"
            leftCancel.textSize = 24f
        } else {

            topicTitle!!.text = "Change Plan"
            (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft).visibility = View.GONE
            cancel!!.visibility = View.VISIBLE
            cancel!!.text = "X"
            cancel!!.textSize = 24f
            var bottomView = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomLayoutMain)
            bottomView.visibility = View.GONE
            doneButton?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.button_save))
        }

        mPlanRepository = PlanRepository(activity)





        eatByPref = Repository().getEatDinnerBy(activity!!)
        doseOnePref = Repository().getDose1(activity!!)
        doseTwoPref = Repository().getDose2(activity!!)
        wakePref = Repository().getWakeUpPlanSetup(activity!!)
        initComponents()
        weekLayoutClick()
        rescheduleLayoutSunday()



        setCurrentDaySelection(DateTimeUtils.getCurrentDay().toLowerCase())

        handleNapSoundReminder()
        handleSoundsReminder()
        handleNap()
        increaseDecreaeByOne()

        doneButton?.setOnClickListener {
            dialogReminder()

        }
    }


    fun setCurrentDaySelection(day: String) {
        when (day) {
            "su" -> {
                sundayLeft.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
                sunRight.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
            }

            "mo" -> {
                monLeft.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
                moRight.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
            }

            "tu" -> {
                tuLeft.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
                tuRight.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
            }

            "we" -> {
                weLeft.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
                weRight.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
            }

            "th" -> {
                thLeft.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
                thRight.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
            }

            "fr" -> {
                frLeft.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
                frRight.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
            }

            "sa" -> {
                saLeft.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
                saRight.setBackgroundColor(GeneralUtils.getColorWrapper(activity, color))
            }

        }
    }

    fun dialogReminder() {
        val builder = android.support.v7.app.AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val v = inflater.inflate(R.layout.dialog_reminder, null)
        builder.setView(v)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        v.findViewById<TextView>(R.id.ok).setOnClickListener(View.OnClickListener {
            saveData()
            Repository(activity!!).setCurrentlySetupPlan(activity!!, true)
            dialog.dismiss()
        })
        dialog.show()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        val density = activity.resources.displayMetrics.density
        lp.width = (300 * density).toInt()
        lp.height = (325 * density).toInt()
        lp.gravity = Gravity.CENTER
        dialog.window!!.attributes = lp
    }

    fun dialogReset() {
        val builder = android.support.v7.app.AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val v = inflater.inflate(R.layout.dialog_reset, null)
        builder.setView(v)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        v.findViewById<TextView>(R.id.resetPlan).setOnClickListener(View.OnClickListener {
            Repository().setIsSchedulePlan(activity, true)
            Repository().setIsPlanSetup(activity, PlanEnum.NOPLAN.ordinal)
            GeneralUtils.resetPlanSetup(activity!!)
            resetCurrentPlan()
            startActivity(Intent(activity!!, PlanSetupFristActivity::class.java))
            activity!!.finishAffinity()
            dialog.dismiss()
        })
        v.findViewById<TextView>(R.id.cancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        val density = activity.resources.displayMetrics.density
        lp.width = (300 * density).toInt()
        lp.height = (325 * density).toInt()
        lp.gravity = Gravity.CENTER
        dialog.window!!.attributes = lp
    }

    private fun resetCurrentPlan() {
        var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())
        try {
//            AlarmSoundRepository(activity).deleteAlarmSound(day)
            var doseOne = ""
            var doseTwo = ""
            var napOne = ""
            var napTwo = ""
            var napThree = ""
            var wake = ""
            var eatBy = ""
            var napOneInterval = "30"
            var napTwoInterval = "30"
            var napThreeInterval = "30"
            var doeseHisotry = DoseHistory(day.toLong()!!, doseOne!!, doseTwo!!, DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
            var napHistory = Nap(day.toLong()!!, napOne, napTwo, napThree, day!!, napOneInterval, napTwoInterval, napThreeInterval)
            var eatByHistory = EatByHistory(day.toLong()!!, eatBy, DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
            var wakeHistory = WakeHistory(day.toLong()!!, wake, DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
            DoseHistoryRepository(activity!!).deleteDoseHistory(day!!)
            NapHistoryRepository(activity!!).deleteNapHistory(day!!)
            EatByRepository(activity!!).deleteEatBy(day!!)
            WakeHistoryRepository(activity!!).deleteWakeHistory(day!!)
            if (!isPlanSetup!!) {
                val am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                var nm = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                for (i in 101..516) {
                    var intent = Intent(activity, MainActivity::class.java)
                    var pendingIntent = PendingIntent.getBroadcast(activity, i, intent, 0)
                    am.cancel(pendingIntent)
                }

                for (i in 11..66) {
                    nm.cancel(i)
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    private fun initComponents() {
        try {
            if (Repository().getIsPlanSetup(activity) == PlanEnum.NOPLAN.ordinal) {
                sundayEatByText?.text = eatByPref
                sundayDoseOneText?.text = doseOnePref
                sundayDoseTwoText?.text = doseTwoPref
                sundayWakeText?.text = wakePref

                mondayEatByText?.text = eatByPref
                mondayDoseOneText?.text = doseOnePref
                mondayDoseTwoText?.text = doseTwoPref
                mondayWakeText?.text = wakePref

                tuesdayEatByText?.text = eatByPref
                tuesdayDoseOneText?.text = doseOnePref
                tuesdayDoseTwoText?.text = doseTwoPref
                tuesdayWakeText?.text = wakePref

                wednesdayEatByText?.text = eatByPref
                wednesdayDoseOneText?.text = doseOnePref
                wednesdayDoseTwoText?.text = doseTwoPref
                wednesdayWakeText?.text = wakePref

                thursdayEatByText?.text = eatByPref
                thursdayDoseOneText?.text = doseOnePref
                thursdayDoseTwoText?.text = doseTwoPref
                thursdayWakeText?.text = wakePref

                fridayEatByText?.text = eatByPref
                fridayDoseOneText?.text = doseOnePref
                fridayDoseTwoText?.text = doseTwoPref
                fridayWakeText?.text = wakePref

                saturdayEatByText?.text = eatByPref
                saturdayDoseOneText?.text = doseOnePref
                saturdayDoseTwoText?.text = doseTwoPref
                saturdayWakeText?.text = wakePref
            } else {
                var sundayPlan = mPlanRepository!!.getPlanById(1)
                var mondayPlan = mPlanRepository!!.getPlanById(2)
                var tuesdayPlan = mPlanRepository!!.getPlanById(3)
                var wednesdayPlan = mPlanRepository!!.getPlanById(4)
                var thursdayPlan = mPlanRepository!!.getPlanById(5)
                var fridayPlan = mPlanRepository!!.getPlanById(6)
                var saturdayPlan = mPlanRepository!!.getPlanById(7)

                sundayEatByText?.text = sundayPlan.eatBy
                sundayDoseOneText?.text = sundayPlan.doseOne
                sundayDoseTwoText?.text = sundayPlan.doseTwo
                sundayWakeText?.text = sundayPlan.wakeUp

                mondayEatByText?.text = mondayPlan.eatBy
                mondayDoseOneText?.text = mondayPlan.doseOne
                mondayDoseTwoText?.text = mondayPlan.doseTwo
                mondayWakeText?.text = mondayPlan.wakeUp

                tuesdayEatByText?.text = tuesdayPlan.eatBy
                tuesdayDoseOneText?.text = tuesdayPlan.doseOne
                tuesdayDoseTwoText?.text = tuesdayPlan.doseTwo
                tuesdayWakeText?.text = tuesdayPlan.wakeUp

                wednesdayEatByText?.text = wednesdayPlan.eatBy
                wednesdayDoseOneText?.text = wednesdayPlan.doseOne
                wednesdayDoseTwoText?.text = wednesdayPlan.doseTwo
                wednesdayWakeText?.text = wednesdayPlan.wakeUp

                thursdayEatByText?.text = thursdayPlan.eatBy
                thursdayDoseOneText?.text = thursdayPlan.doseOne
                thursdayDoseTwoText?.text = thursdayPlan.doseTwo
                thursdayWakeText?.text = thursdayPlan.wakeUp

                fridayEatByText?.text = fridayPlan.eatBy
                fridayDoseOneText?.text = fridayPlan.doseOne
                fridayDoseTwoText?.text = fridayPlan.doseTwo
                fridayWakeText?.text = fridayPlan.wakeUp

                saturdayEatByText?.text = saturdayPlan.eatBy
                saturdayDoseOneText?.text = saturdayPlan.doseOne
                saturdayDoseTwoText?.text = saturdayPlan.doseTwo
                saturdayWakeText?.text = saturdayPlan.wakeUp
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }




        setPickerTime()

    }

    private fun weekLayoutClick() {
        sunday.setOnClickListener {
            if (isSunlayout!!) {
                sundayLayoutSetData?.visibility = View.GONE
                isSunlayout = false
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {

                    topicTitle!!.text = "Change Plan"
                }
                sundayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                sundayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                sundayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                sundayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))


            } else {
                if (isPlanSetup!!) {
                    (activity as PlanSetupFristActivity).bottomView?.visibility = View.GONE

                } else {
                    var bottomView = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomLayoutMain)
                    bottomView.visibility = View.GONE
                }
                sundayLayoutSetData?.visibility = View.VISIBLE
                mondayLayoutSetData?.visibility = View.GONE
                tuesdayLayoutSetData?.visibility = View.GONE
                wednesdayLayoutSetData?.visibility = View.GONE
                thursdayLayoutSetData?.visibility = View.GONE
                fridayLayoutSetData?.visibility = View.GONE
                saturdayLayoutSetData?.visibility = View.GONE
                isSunlayout = true

                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {
                    topicTitle!!.text = "Change Plan"

                }
                sundayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            }


        }
        monday.setOnClickListener {
            if (isMonlayout!!) {
                isMonlayout = false
                mondayLayoutSetData?.visibility = View.GONE
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {

                    topicTitle!!.text = "Change Plan"
                }
                mondayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                mondayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                mondayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                mondayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))

            } else {
                if (isPlanSetup!!) {
                    (activity as PlanSetupFristActivity).bottomView?.visibility = View.GONE

                } else {
                    var bottomView = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomLayoutMain)
                    bottomView.visibility = View.GONE
                }
                mondayLayoutSetData?.visibility = View.VISIBLE
                sundayLayoutSetData?.visibility = View.GONE
                tuesdayLayoutSetData?.visibility = View.GONE
                wednesdayLayoutSetData?.visibility = View.GONE
                thursdayLayoutSetData?.visibility = View.GONE
                fridayLayoutSetData?.visibility = View.GONE
                saturdayLayoutSetData?.visibility = View.GONE
                isMonlayout = true
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {
                    topicTitle!!.text = "Change Plan"

                }
                mondayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            }

        }

        wensday.setOnClickListener {
            if (isWedlayout!!) {
                isWedlayout = false
                wednesdayLayoutSetData?.visibility = View.GONE
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {
                    topicTitle!!.text = "Change Plan"

                }
                wednesdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                wednesdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                wednesdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                wednesdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            } else {
                if (isPlanSetup!!) {
                    (activity as PlanSetupFristActivity).bottomView?.visibility = View.GONE

                } else {
                    var bottomView = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomLayoutMain)
                    bottomView.visibility = View.GONE
                }
                wednesdayLayoutSetData?.visibility = View.VISIBLE
                mondayLayoutSetData?.visibility = View.GONE
                sundayLayoutSetData?.visibility = View.GONE
                tuesdayLayoutSetData?.visibility = View.GONE
                thursdayLayoutSetData?.visibility = View.GONE
                fridayLayoutSetData?.visibility = View.GONE
                saturdayLayoutSetData?.visibility = View.GONE
                isWedlayout = true
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {

                    topicTitle!!.text = "Change Plan"

                }
                wednesdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))


            }

        }

        thursday.setOnClickListener {
            if (isThurlayout!!) {
                isThurlayout = false
                thursdayLayoutSetData?.visibility = View.GONE
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {
                    topicTitle!!.text = "Change Plan"

                }
                thursdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                thursdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                thursdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                thursdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))

            } else {
                if (isPlanSetup!!) {
                    (activity as PlanSetupFristActivity).bottomView?.visibility = View.GONE

                } else {
                    var bottomView = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomLayoutMain)
                    bottomView.visibility = View.GONE
                }
                thursdayLayoutSetData?.visibility = View.VISIBLE
                wednesdayLayoutSetData?.visibility = View.GONE
                mondayLayoutSetData?.visibility = View.GONE
                sundayLayoutSetData?.visibility = View.GONE
                tuesdayLayoutSetData?.visibility = View.GONE
                fridayLayoutSetData?.visibility = View.GONE
                saturdayLayoutSetData?.visibility = View.GONE
                isThurlayout = true
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {

                    topicTitle!!.text = "Change Plan"

                }
                thursdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            }

        }

        friday.setOnClickListener {
            if (isFrilayout!!) {
                isFrilayout = false
                fridayLayoutSetData?.visibility = View.GONE
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {
                    topicTitle!!.text = "Change Plan"

                }
                fridayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                fridayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                fridayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                fridayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            } else {
                if (isPlanSetup!!) {
                    (activity as PlanSetupFristActivity).bottomView?.visibility = View.GONE

                } else {
                    var bottomView = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomLayoutMain)
                    bottomView.visibility = View.GONE
                }
                fridayLayoutSetData?.visibility = View.VISIBLE
                wednesdayLayoutSetData?.visibility = View.GONE
                mondayLayoutSetData?.visibility = View.GONE
                sundayLayoutSetData?.visibility = View.GONE
                tuesdayLayoutSetData?.visibility = View.GONE
                thursdayLayoutSetData?.visibility = View.GONE
                saturdayLayoutSetData?.visibility = View.GONE
                isFrilayout = true
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {

                    topicTitle!!.text = "Change Plan"

                }
                fridayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            }

        }
        view!!.findViewById<LinearLayout>(R.id.tuesday).setOnClickListener {
            if (isTuelayout!!) {
                tuesdayLayoutSetData?.visibility = View.GONE
                isTuelayout = false
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {
                    topicTitle!!.text = "Change Plan"

                }

                tuesdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                tuesdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                tuesdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                tuesdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            } else {
                if (isPlanSetup!!) {
                    (activity as PlanSetupFristActivity).bottomView?.visibility = View.GONE

                } else {
                    var bottomView = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomLayoutMain)
                    bottomView.visibility = View.GONE
                }
                tuesdayLayoutSetData?.visibility = View.VISIBLE
                sundayLayoutSetData?.visibility = View.GONE
                mondayLayoutSetData?.visibility = View.GONE
                wednesdayLayoutSetData?.visibility = View.GONE
                thursdayLayoutSetData?.visibility = View.GONE
                fridayLayoutSetData?.visibility = View.GONE
                saturdayLayoutSetData?.visibility = View.GONE
                isTuelayout = true
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {

                    topicTitle!!.text = "Change Plan"

                }
                tuesdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            }

        }

        saturday.setOnClickListener {
            if (isSatlayout!!) {
                isSatlayout = false
                saturdayLayoutSetData?.visibility = View.GONE
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {
                    topicTitle!!.text = "Change Plan"

                }
                saturdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                saturdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                saturdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
                saturdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            } else {
                if (isPlanSetup!!) {
                    (activity as PlanSetupFristActivity).bottomView?.visibility = View.GONE

                } else {
                    var bottomView = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomLayoutMain)
                    bottomView.visibility = View.GONE
                }
                saturdayLayoutSetData?.visibility = View.VISIBLE
                wednesdayLayoutSetData?.visibility = View.GONE
                mondayLayoutSetData?.visibility = View.GONE
                sundayLayoutSetData?.visibility = View.GONE
                tuesdayLayoutSetData?.visibility = View.GONE
                fridayLayoutSetData?.visibility = View.GONE
                thursdayLayoutSetData?.visibility = View.GONE
                isSatlayout = true
                if (isPlanSetup!!) {
                    topicTitle!!.text = "PLAN SETUP [10/10]"
                } else {

                    topicTitle!!.text = "Change Plan"

                }
                saturdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            }

        }

    }

    private fun rescheduleLayoutSunday() {

        //sunday start
        eatBySunday?.setOnClickListener {
            eatByTimePickerSunday?.visibility = View.VISIBLE
            doseOneTimePickerSunday?.visibility = View.GONE
            doseTwoTimePickerSunday?.visibility = View.GONE
            wakeTimePickerSunday?.visibility = View.GONE

            eatBySunday.setBackgroundColor(resources.getColor(R.color.planBackground))
            eatBySunday.setTextColor(resources.getColor(R.color.white))

            doseOneSunday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneSunday.setTextColor(resources.getColor(R.color.black))

            doseTwoSunday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoSunday.setTextColor(resources.getColor(R.color.black))

            wakeSunday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeSunday.setTextColor(resources.getColor(R.color.black))
            sundayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))

        }

        doseOneSunday?.setOnClickListener {
            eatByTimePickerSunday?.visibility = View.GONE
            doseOneTimePickerSunday?.visibility = View.VISIBLE
            doseTwoTimePickerSunday?.visibility = View.GONE
            wakeTimePickerSunday?.visibility = View.GONE

            doseOneSunday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseOneSunday.setTextColor(resources.getColor(R.color.white))

            doseTwoSunday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoSunday.setTextColor(resources.getColor(R.color.black))

            eatBySunday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatBySunday.setTextColor(resources.getColor(R.color.black))

            wakeSunday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeSunday.setTextColor(resources.getColor(R.color.black))

            sundayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))

        }

        doseTwoSunday?.setOnClickListener {
            eatByTimePickerSunday?.visibility = View.GONE
            doseOneTimePickerSunday?.visibility = View.GONE
            doseTwoTimePickerSunday?.visibility = View.VISIBLE
            wakeTimePickerSunday?.visibility = View.GONE

            doseTwoSunday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseTwoSunday.setTextColor(resources.getColor(R.color.white))

            doseOneSunday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneSunday.setTextColor(resources.getColor(R.color.black))

            eatBySunday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatBySunday.setTextColor(resources.getColor(R.color.black))

            wakeSunday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeSunday.setTextColor(resources.getColor(R.color.black))
            sundayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        wakeSunday?.setOnClickListener {
            eatByTimePickerSunday?.visibility = View.GONE
            doseOneTimePickerSunday?.visibility = View.GONE
            doseTwoTimePickerSunday?.visibility = View.GONE
            wakeTimePickerSunday?.visibility = View.VISIBLE

            wakeSunday.setBackgroundColor(resources.getColor(R.color.planBackground))
            wakeSunday.setTextColor(resources.getColor(R.color.white))

            doseOneSunday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneSunday.setTextColor(resources.getColor(R.color.black))

            doseTwoSunday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoSunday.setTextColor(resources.getColor(R.color.black))

            eatBySunday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatBySunday.setTextColor(resources.getColor(R.color.black))
            sundayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            sundayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }
        //sunday end


        //monday start
        eatByMonday?.setOnClickListener {
            eatByTimePickerMonday?.visibility = View.VISIBLE
            doseOneTimePickerMonday?.visibility = View.GONE
            doseTwoTimePickerMonday?.visibility = View.GONE
            wakeTimePickerMonday?.visibility = View.GONE

            eatByMonday.setBackgroundColor(resources.getColor(R.color.planBackground))
            eatByMonday.setTextColor(resources.getColor(R.color.white))

            doseOneMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneMonday.setTextColor(resources.getColor(R.color.black))

            doseTwoMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoSunday.setTextColor(resources.getColor(R.color.black))

            wakeMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeMonday.setTextColor(resources.getColor(R.color.black))
            mondayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))

        }

        doseOneMonday?.setOnClickListener {
            eatByTimePickerMonday?.visibility = View.GONE
            doseOneTimePickerMonday?.visibility = View.VISIBLE
            doseTwoTimePickerMonday?.visibility = View.GONE
            wakeTimePickerMonday?.visibility = View.GONE

            doseOneMonday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseOneMonday.setTextColor(resources.getColor(R.color.white))

            doseTwoMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoMonday.setTextColor(resources.getColor(R.color.black))

            eatByMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByMonday.setTextColor(resources.getColor(R.color.black))

            wakeMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeMonday.setTextColor(resources.getColor(R.color.black))
            mondayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        doseTwoMonday?.setOnClickListener {
            eatByTimePickerMonday?.visibility = View.GONE
            doseOneTimePickerMonday?.visibility = View.GONE
            doseTwoTimePickerMonday?.visibility = View.VISIBLE
            wakeTimePickerMonday?.visibility = View.GONE

            doseTwoMonday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseTwoMonday.setTextColor(resources.getColor(R.color.white))

            doseOneMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneMonday.setTextColor(resources.getColor(R.color.black))

            eatByMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByMonday.setTextColor(resources.getColor(R.color.black))

            wakeMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeMonday.setTextColor(resources.getColor(R.color.black))
            mondayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        wakeMonday?.setOnClickListener {
            eatByTimePickerMonday?.visibility = View.GONE
            doseOneTimePickerMonday?.visibility = View.GONE
            doseTwoTimePickerMonday?.visibility = View.GONE
            wakeTimePickerMonday?.visibility = View.VISIBLE

            wakeMonday.setBackgroundColor(resources.getColor(R.color.planBackground))
            wakeMonday.setTextColor(resources.getColor(R.color.white))

            doseOneMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneMonday.setTextColor(resources.getColor(R.color.black))

            doseTwoMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoMonday.setTextColor(resources.getColor(R.color.black))

            eatByMonday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByMonday.setTextColor(resources.getColor(R.color.black))
            mondayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            mondayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }
        //monday end


        //tuesday start
        eatByTuesday?.setOnClickListener {
            eatByTimePickerTuesday?.visibility = View.VISIBLE
            doseOneTimePickerTuesday?.visibility = View.GONE
            doseTwoTimePickerTuesday?.visibility = View.GONE
            WakeTimePickerTuesday?.visibility = View.GONE

            eatByTuesday.setBackgroundColor(resources.getColor(R.color.planBackground))
            eatByTuesday.setTextColor(resources.getColor(R.color.white))

            doseOneTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneTuesday.setTextColor(resources.getColor(R.color.black))

            doseTwoTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoTuesday.setTextColor(resources.getColor(R.color.black))

            wakeTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeTuesday.setTextColor(resources.getColor(R.color.black))

            tuesdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        doseOneTuesday?.setOnClickListener {
            eatByTimePickerTuesday?.visibility = View.GONE
            doseOneTimePickerTuesday?.visibility = View.VISIBLE
            doseTwoTimePickerTuesday?.visibility = View.GONE
            WakeTimePickerTuesday?.visibility = View.GONE

            doseOneTuesday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseOneTuesday.setTextColor(resources.getColor(R.color.white))

            doseTwoTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoTuesday.setTextColor(resources.getColor(R.color.black))

            eatByTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByTuesday.setTextColor(resources.getColor(R.color.black))

            wakeTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeTuesday.setTextColor(resources.getColor(R.color.black))
            tuesdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        doseTwoTuesday?.setOnClickListener {
            eatByTimePickerTuesday?.visibility = View.GONE
            doseOneTimePickerTuesday?.visibility = View.GONE
            doseTwoTimePickerTuesday?.visibility = View.VISIBLE
            WakeTimePickerTuesday?.visibility = View.GONE

            doseTwoTuesday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseTwoTuesday.setTextColor(resources.getColor(R.color.white))

            doseOneTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneTuesday.setTextColor(resources.getColor(R.color.black))

            eatByTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByTuesday.setTextColor(resources.getColor(R.color.black))

            wakeTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeTuesday.setTextColor(resources.getColor(R.color.black))
            tuesdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        wakeTuesday?.setOnClickListener {
            eatByTimePickerTuesday?.visibility = View.GONE
            doseOneTimePickerTuesday?.visibility = View.GONE
            doseTwoTimePickerTuesday?.visibility = View.GONE
            WakeTimePickerTuesday?.visibility = View.VISIBLE

            wakeTuesday.setBackgroundColor(resources.getColor(R.color.planBackground))
            wakeTuesday.setTextColor(resources.getColor(R.color.white))

            doseOneTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneTuesday.setTextColor(resources.getColor(R.color.black))

            doseTwoTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoTuesday.setTextColor(resources.getColor(R.color.black))

            eatByTuesday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByTuesday.setTextColor(resources.getColor(R.color.black))
            tuesdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            tuesdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }
        //tuesday end


        //wednesday start
        eatByWednesday?.setOnClickListener {
            eatByTimePickerWednesday?.visibility = View.VISIBLE
            doseOneTimePickerWednesday?.visibility = View.GONE
            doseTwoTimePickerWednesday?.visibility = View.GONE
            wakeTimePickerWednesday?.visibility = View.GONE

            eatByWednesday.setBackgroundColor(resources.getColor(R.color.planBackground))
            eatByWednesday.setTextColor(resources.getColor(R.color.white))

            doseOneWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneWednesday.setTextColor(resources.getColor(R.color.black))

            doseTwoWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoWednesday.setTextColor(resources.getColor(R.color.black))

            wakeWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeWednesday.setTextColor(resources.getColor(R.color.black))
            wednesdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))

        }

        doseOneWednesday.setOnClickListener {
            eatByTimePickerWednesday?.visibility = View.GONE
            doseOneTimePickerWednesday?.visibility = View.VISIBLE
            doseTwoTimePickerWednesday?.visibility = View.GONE
            wakeTimePickerWednesday?.visibility = View.GONE

            doseOneWednesday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseOneWednesday.setTextColor(resources.getColor(R.color.white))

            doseTwoWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoWednesday.setTextColor(resources.getColor(R.color.black))

            eatByWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByWednesday.setTextColor(resources.getColor(R.color.black))

            wakeWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeWednesday.setTextColor(resources.getColor(R.color.black))
            wednesdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        doseTwoWednesday?.setOnClickListener {
            eatByTimePickerWednesday?.visibility = View.GONE
            doseOneTimePickerWednesday?.visibility = View.GONE
            doseTwoTimePickerWednesday?.visibility = View.VISIBLE
            wakeTimePickerWednesday?.visibility = View.GONE

            doseTwoWednesday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseTwoWednesday.setTextColor(resources.getColor(R.color.white))

            doseOneWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneWednesday.setTextColor(resources.getColor(R.color.black))

            eatByWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByWednesday.setTextColor(resources.getColor(R.color.black))

            wakeWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeWednesday.setTextColor(resources.getColor(R.color.black))
            wednesdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        wakeWednesday?.setOnClickListener {
            eatByTimePickerWednesday?.visibility = View.GONE
            doseOneTimePickerWednesday.visibility = View.GONE
            doseTwoTimePickerWednesday?.visibility = View.GONE
            wakeTimePickerWednesday?.visibility = View.VISIBLE

            wakeWednesday.setBackgroundColor(resources.getColor(R.color.planBackground))
            wakeWednesday.setTextColor(resources.getColor(R.color.white))

            doseOneWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneWednesday.setTextColor(resources.getColor(R.color.black))

            doseTwoWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoWednesday.setTextColor(resources.getColor(R.color.black))

            eatByWednesday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByWednesday.setTextColor(resources.getColor(R.color.black))
            wednesdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            wednesdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }
        //wednesday end


        //thursday start
        eatByThursday?.setOnClickListener {
            eatByTimePickerThursday?.visibility = View.VISIBLE
            doseOneTimePickerThursday?.visibility = View.GONE
            doseTwoTimePickerThursday?.visibility = View.GONE
            wakeTimePickerThursday?.visibility = View.GONE

            eatByThursday.setBackgroundColor(resources.getColor(R.color.planBackground))
            eatByThursday.setTextColor(resources.getColor(R.color.white))

            doseOneThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneThursday.setTextColor(resources.getColor(R.color.black))

            doseTwoThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoThursday.setTextColor(resources.getColor(R.color.black))

            wakeThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeThursday.setTextColor(resources.getColor(R.color.black))
            thursdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))

        }

        doseOneThursday.setOnClickListener {
            eatByTimePickerThursday?.visibility = View.GONE
            doseOneTimePickerThursday?.visibility = View.VISIBLE
            doseTwoTimePickerThursday?.visibility = View.GONE
            wakeTimePickerThursday?.visibility = View.GONE

            doseOneThursday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseOneThursday.setTextColor(resources.getColor(R.color.white))

            doseTwoThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoThursday.setTextColor(resources.getColor(R.color.black))

            eatByThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByThursday.setTextColor(resources.getColor(R.color.black))

            wakeThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeThursday.setTextColor(resources.getColor(R.color.black))
            thursdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        doseTwoThursday?.setOnClickListener {
            eatByTimePickerThursday?.visibility = View.GONE
            doseOneTimePickerThursday?.visibility = View.GONE
            doseTwoTimePickerThursday?.visibility = View.VISIBLE
            wakeTimePickerThursday?.visibility = View.GONE

            doseTwoThursday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseTwoThursday.setTextColor(resources.getColor(R.color.white))

            doseOneThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneThursday.setTextColor(resources.getColor(R.color.black))

            eatByThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByThursday.setTextColor(resources.getColor(R.color.black))

            wakeThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeThursday.setTextColor(resources.getColor(R.color.black))
            thursdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        wakeThursday?.setOnClickListener {
            eatByTimePickerThursday?.visibility = View.GONE
            doseOneTimePickerThursday.visibility = View.GONE
            doseTwoTimePickerThursday?.visibility = View.GONE
            wakeTimePickerThursday?.visibility = View.VISIBLE

            wakeThursday.setBackgroundColor(resources.getColor(R.color.planBackground))
            wakeThursday.setTextColor(resources.getColor(R.color.white))

            doseOneThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneThursday.setTextColor(resources.getColor(R.color.black))

            doseTwoThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoThursday.setTextColor(resources.getColor(R.color.black))

            eatByThursday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByThursday.setTextColor(resources.getColor(R.color.black))
            thursdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            thursdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }
        //thursday end


        //friday start
        eatByFriday?.setOnClickListener {
            eatByTimePickerFriday?.visibility = View.VISIBLE
            doseOneTimePickerFriday?.visibility = View.GONE
            doseTwoTimePickerFriday?.visibility = View.GONE
            wakeTimePickerFriday?.visibility = View.GONE

            eatByFriday.setBackgroundColor(resources.getColor(R.color.planBackground))
            eatByFriday.setTextColor(resources.getColor(R.color.white))

            doseOneFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneFriday.setTextColor(resources.getColor(R.color.black))

            doseTwoFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoFriday.setTextColor(resources.getColor(R.color.black))

            wakeFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeFriday.setTextColor(resources.getColor(R.color.black))

            fridayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        doseOneFriday.setOnClickListener {
            eatByTimePickerFriday?.visibility = View.GONE
            doseOneTimePickerFriday?.visibility = View.VISIBLE
            doseTwoTimePickerFriday?.visibility = View.GONE
            wakeTimePickerFriday?.visibility = View.GONE

            doseOneFriday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseOneFriday.setTextColor(resources.getColor(R.color.white))

            doseTwoFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoFriday.setTextColor(resources.getColor(R.color.black))

            eatByFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByFriday.setTextColor(resources.getColor(R.color.black))

            wakeFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeFriday.setTextColor(resources.getColor(R.color.black))
            fridayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        doseTwoFriday?.setOnClickListener {
            eatByTimePickerFriday?.visibility = View.GONE
            doseOneTimePickerFriday?.visibility = View.GONE
            doseTwoTimePickerFriday?.visibility = View.VISIBLE
            wakeTimePickerFriday?.visibility = View.GONE

            doseTwoFriday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseTwoFriday.setTextColor(resources.getColor(R.color.white))

            doseOneFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneFriday.setTextColor(resources.getColor(R.color.black))

            eatByFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByFriday.setTextColor(resources.getColor(R.color.black))

            wakeFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeFriday.setTextColor(resources.getColor(R.color.black))
            fridayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        wakeFriday?.setOnClickListener {
            eatByTimePickerFriday?.visibility = View.GONE
            doseOneTimePickerFriday?.visibility = View.GONE
            doseTwoTimePickerFriday?.visibility = View.GONE
            wakeTimePickerFriday?.visibility = View.VISIBLE

            wakeFriday.setBackgroundColor(resources.getColor(R.color.planBackground))
            wakeFriday.setTextColor(resources.getColor(R.color.white))

            doseOneFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneFriday.setTextColor(resources.getColor(R.color.black))

            doseTwoFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoFriday.setTextColor(resources.getColor(R.color.black))

            eatByFriday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatByFriday.setTextColor(resources.getColor(R.color.black))
            fridayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            fridayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }
        //friday end


        //saturday start
        eatBySaturday?.setOnClickListener {
            eatByTimePickerSaturday?.visibility = View.VISIBLE
            doseOneTimePickerSaturday?.visibility = View.GONE
            doseTwoTimePickerSaturday?.visibility = View.GONE
            wakeTimePickerSaturday?.visibility = View.GONE

            eatBySaturday.setBackgroundColor(resources.getColor(R.color.planBackground))
            eatBySaturday.setTextColor(resources.getColor(R.color.white))

            doseOneSaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneSaturday.setTextColor(resources.getColor(R.color.black))

            doseTwoSaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoSaturday.setTextColor(resources.getColor(R.color.black))

            wakeSaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeSaturday.setTextColor(resources.getColor(R.color.black))

            saturdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        doseOneSaturday.setOnClickListener {
            eatByTimePickerSaturday?.visibility = View.GONE
            doseOneTimePickerSaturday?.visibility = View.VISIBLE
            doseTwoTimePickerSaturday?.visibility = View.GONE
            wakeTimePickerSaturday?.visibility = View.GONE

            doseOneSaturday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseOneSaturday.setTextColor(resources.getColor(R.color.white))

            doseTwoSaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoSaturday.setTextColor(resources.getColor(R.color.black))

            eatBySaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatBySaturday.setTextColor(resources.getColor(R.color.black))

            wakeSaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeSaturday.setTextColor(resources.getColor(R.color.black))
            saturdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        doseTwoSaturday?.setOnClickListener {
            eatByTimePickerSaturday?.visibility = View.GONE
            doseOneTimePickerSaturday?.visibility = View.GONE
            doseTwoTimePickerSaturday?.visibility = View.VISIBLE
            wakeTimePickerSaturday?.visibility = View.GONE

            doseTwoSaturday.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseTwoSaturday.setTextColor(resources.getColor(R.color.white))

            doseOneSaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneSaturday.setTextColor(resources.getColor(R.color.black))

            eatBySaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatBySaturday.setTextColor(resources.getColor(R.color.black))

            wakeSaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            wakeSaturday.setTextColor(resources.getColor(R.color.black))
            saturdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }

        wakeSaturday?.setOnClickListener {
            eatByTimePickerSaturday?.visibility = View.GONE
            doseOneTimePickerSaturday?.visibility = View.GONE
            doseTwoTimePickerSaturday?.visibility = View.GONE
            wakeTimePickerSaturday?.visibility = View.VISIBLE

            wakeSaturday.setBackgroundColor(resources.getColor(R.color.planBackground))
            wakeSaturday.setTextColor(resources.getColor(R.color.white))

            doseOneSaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseOneSaturday.setTextColor(resources.getColor(R.color.black))

            doseTwoSaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwoSaturday.setTextColor(resources.getColor(R.color.black))

            eatBySaturday.setBackgroundColor(resources.getColor(R.color.transparent))
            eatBySaturday.setTextColor(resources.getColor(R.color.black))
            saturdayEatByText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayDoseOneText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayDoseTwoText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
            saturdayWakeText?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.colorPrimaryDark))
        }
        //saturday end
    }

    private fun setPickerTime() {
        try {
            //sunday picker set
            var tokens = DateTimeUtils.convert12HrsTo24Hrs(sundayEatByText.text.toString()).split(":")
            var hours = tokens[0].toInt()
            var minutes = tokens[1].split(" ")[0].toInt()
            var calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            var hour = calendar.get(Calendar.HOUR_OF_DAY)
            var minute = calendar.get(Calendar.MINUTE)
            var am_pm = calendar.get(Calendar.AM_PM)
            setTimePickerInterVal(eatByTimePickerSunday!!)
            // Configure displayed time
            if (minute % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minute + TIME_PICKER_INTERVAL - minute % TIME_PICKER_INTERVAL
                minute = minuteFloor + if (minute === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minute >= 60) {
                    minute = minute % 60
                    hour++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    eatByTimePickerSunday?.hour = hour
                    eatByTimePickerSunday?.minute = (minute / TIME_PICKER_INTERVAL)
                } else {
                    eatByTimePickerSunday?.currentHour = hour
                    eatByTimePickerSunday?.currentMinute = (minute / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                eatByTimePickerSunday?.hour = hours
                eatByTimePickerSunday?.minute = (minutes / TIME_PICKER_INTERVAL)
            } else {
                eatByTimePickerSunday?.currentHour = hours
                eatByTimePickerSunday?.currentMinute = (minutes / TIME_PICKER_INTERVAL)
            }
            eatByTimePickerSunday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    sundayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)!!.toUpperCase()
                    sundayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    sundayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)!!.toUpperCase()
                    sundayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }


            }


            var tokensDoseOne = DateTimeUtils.convert12HrsTo24Hrs(sundayDoseOneText.text.toString()).split(":")
            var hoursDoseOne = tokensDoseOne[0].toInt()
            var minutesDoseOne = tokensDoseOne[1].split(" ")[0].toInt()
            var calendarDoseOn = Calendar.getInstance()
            calendarDoseOn.timeInMillis = System.currentTimeMillis()
            var hourDoseOne = calendarDoseOn.get(Calendar.HOUR_OF_DAY)
            var minuteDoseOne = calendarDoseOn.get(Calendar.MINUTE)
            setTimePickerInterVal(doseOneTimePickerSunday!!)
            // Configure displayed time
            if (minuteDoseOne % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteDoseOne + TIME_PICKER_INTERVAL - minuteDoseOne % TIME_PICKER_INTERVAL
                minuteDoseOne = minuteFloor + if (minuteDoseOne === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteDoseOne >= 60) {
                    minuteDoseOne = minuteDoseOne % 60
                    hourDoseOne++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseOneTimePickerSunday?.hour = hourDoseOne
                    doseOneTimePickerSunday?.minute = (minuteDoseOne / TIME_PICKER_INTERVAL)
                } else {
                    doseOneTimePickerSunday?.currentHour = hourDoseOne
                    doseOneTimePickerSunday?.currentMinute = (minuteDoseOne / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseOneTimePickerSunday?.hour = hoursDoseOne
                doseOneTimePickerSunday?.minute = (minutesDoseOne / TIME_PICKER_INTERVAL)
            } else {
                doseOneTimePickerSunday?.currentHour = hoursDoseOne
                doseOneTimePickerSunday?.currentMinute = (minutesDoseOne / TIME_PICKER_INTERVAL)
            }

            doseOneTimePickerSunday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    sundayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    sundayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    sundayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    sundayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                }

            }


            var tokensDoseTwo = DateTimeUtils.convert12HrsTo24Hrs(sundayDoseTwoText.text.toString()).split(":")
            var hoursDoseTwo = tokensDoseTwo[0].toInt()
            var minutesDoseTwo = tokensDoseTwo[1].split(" ")[0].toInt()

            var calendarDoseTwo = Calendar.getInstance()
            calendarDoseTwo.timeInMillis = System.currentTimeMillis()
            var hourDoseTwo = calendarDoseTwo.get(Calendar.HOUR_OF_DAY)
            var minuteDoseTwo = calendarDoseTwo.get(Calendar.MINUTE)
            setTimePickerInterVal(doseTwoTimePickerSunday!!)
            // Configure displayed time
            if (minuteDoseTwo % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteDoseTwo + TIME_PICKER_INTERVAL - minuteDoseTwo % TIME_PICKER_INTERVAL
                minuteDoseTwo = minuteFloor + if (minuteDoseTwo === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteDoseTwo >= 60) {
                    minuteDoseTwo = minuteDoseTwo % 60
                    hourDoseTwo++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseTwoTimePickerSunday?.hour = hourDoseTwo
                    doseTwoTimePickerSunday?.minute = (minuteDoseTwo / TIME_PICKER_INTERVAL)
                } else {
                    doseTwoTimePickerSunday?.currentHour = hourDoseTwo
                    doseTwoTimePickerSunday?.currentMinute = (minuteDoseTwo / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseTwoTimePickerSunday?.hour = hoursDoseTwo
                doseTwoTimePickerSunday?.minute = (minutesDoseTwo / TIME_PICKER_INTERVAL)
            } else {
                doseTwoTimePickerSunday?.currentHour = hoursDoseTwo
                doseTwoTimePickerSunday?.currentMinute = (minutesDoseTwo / TIME_PICKER_INTERVAL)
            }

            doseTwoTimePickerSunday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    sundayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    sundayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))


                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    sundayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    sundayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))


                }

            }


            var tokensWake = DateTimeUtils.convert12HrsTo24Hrs(sundayWakeText.text.toString()).split(":")
            var hoursWake = tokensWake[0].toInt()
            var minutesWake = tokensWake[1].split(" ")[0].toInt()

            var calendarWake = Calendar.getInstance()
            calendarWake.timeInMillis = System.currentTimeMillis()
            var hourWake = calendarWake.get(Calendar.HOUR_OF_DAY)
            var minuteWake = calendarWake.get(Calendar.MINUTE)
            setTimePickerInterVal(wakeTimePickerSunday!!)
            // Configure displayed time
            if (minuteWake % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteWake + TIME_PICKER_INTERVAL - minuteDoseTwo % TIME_PICKER_INTERVAL
                minuteWake = minuteFloor + if (minuteWake === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteWake >= 60) {
                    minuteWake = minuteDoseTwo % 60
                    hourWake++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    wakeTimePickerSunday?.hour = hourWake
                    wakeTimePickerSunday?.minute = (minuteWake / TIME_PICKER_INTERVAL)
                } else {
                    wakeTimePickerSunday?.currentHour = hourWake
                    wakeTimePickerSunday?.currentMinute = (minuteWake / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                wakeTimePickerSunday?.hour = hoursWake
                wakeTimePickerSunday?.minute = (minutesWake / TIME_PICKER_INTERVAL)
            } else {
                wakeTimePickerSunday?.currentHour = hoursWake
                wakeTimePickerSunday?.currentMinute = (minutesWake / TIME_PICKER_INTERVAL)
            }

            wakeTimePickerSunday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    sundayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    sundayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    sundayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    sundayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }

            //sundayPicker set end


            //mondayPicker set
            var tokensMonday = DateTimeUtils.convert12HrsTo24Hrs(mondayEatByText.text.toString()).split(":")
            var hoursMonday = tokensMonday[0].toInt()
            var minutesMonday = tokensMonday[1].split(" ")[0].toInt()

            var calendarMondayEatBy = Calendar.getInstance()
            calendarMondayEatBy.timeInMillis = System.currentTimeMillis()
            var hourMondayEatBy = calendarMondayEatBy.get(Calendar.HOUR_OF_DAY)
            var minuteMondayEatBy = calendarMondayEatBy.get(Calendar.MINUTE)
            setTimePickerInterVal(eatByTimePickerMonday!!)
            // Configure displayed time
            if (minuteMondayEatBy % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteMondayEatBy + TIME_PICKER_INTERVAL - minuteMondayEatBy % TIME_PICKER_INTERVAL
                minuteMondayEatBy = minuteFloor + if (minuteMondayEatBy === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteMondayEatBy >= 60) {
                    minuteMondayEatBy = minuteMondayEatBy % 60
                    hourMondayEatBy++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    eatByTimePickerMonday?.hour = hourMondayEatBy
                    eatByTimePickerMonday?.minute = (minuteMondayEatBy / TIME_PICKER_INTERVAL)
                } else {
                    eatByTimePickerMonday?.currentHour = hourMondayEatBy
                    eatByTimePickerMonday?.currentMinute = (minuteMondayEatBy / TIME_PICKER_INTERVAL)
                }

            }


            if (Build.VERSION.SDK_INT >= 23) {
                eatByTimePickerMonday?.hour = hoursMonday
                eatByTimePickerMonday?.minute = (minutesMonday / TIME_PICKER_INTERVAL)
            } else {
                eatByTimePickerMonday?.currentHour = hoursMonday
                eatByTimePickerMonday?.currentMinute = (minutesMonday / TIME_PICKER_INTERVAL)
            }
            eatByTimePickerMonday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    mondayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    mondayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))


                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    mondayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    mondayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                }

            }


            var tokensDoseOneMonday = DateTimeUtils.convert12HrsTo24Hrs(mondayDoseOneText.text.toString()).split(":")
            var hoursDoseOneMonday = tokensDoseOneMonday[0].toInt()
            var minutesDoseOneMonday = tokensDoseOneMonday[1].split(" ")[0].toInt()

            var calendarMondayDoseOne = Calendar.getInstance()
            calendarMondayDoseOne.timeInMillis = System.currentTimeMillis()
            var hourMondayDoseOne = calendarMondayDoseOne.get(Calendar.HOUR_OF_DAY)
            var minuteMondayDoseOne = calendarMondayDoseOne.get(Calendar.MINUTE)
            setTimePickerInterVal(doseOneTimePickerMonday!!)
            // Configure displayed time
            if (minuteMondayDoseOne % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteMondayDoseOne + TIME_PICKER_INTERVAL - minuteMondayDoseOne % TIME_PICKER_INTERVAL
                minuteMondayDoseOne = minuteFloor + if (minuteMondayDoseOne === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteMondayDoseOne >= 60) {
                    minuteMondayDoseOne = minuteMondayDoseOne % 60
                    hourMondayDoseOne++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseOneTimePickerMonday?.hour = hourMondayDoseOne
                    doseOneTimePickerMonday?.minute = (minuteMondayDoseOne / TIME_PICKER_INTERVAL)
                } else {
                    doseOneTimePickerMonday?.currentHour = hourMondayDoseOne
                    doseOneTimePickerMonday?.currentMinute = (minuteMondayDoseOne / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseOneTimePickerMonday?.hour = hoursDoseOneMonday
                doseOneTimePickerMonday?.minute = (minutesDoseOneMonday / TIME_PICKER_INTERVAL)
            } else {
                doseOneTimePickerMonday?.currentHour = hoursDoseOneMonday
                doseOneTimePickerMonday?.currentMinute = (minutesDoseOneMonday / TIME_PICKER_INTERVAL)
            }
            doseOneTimePickerMonday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    mondayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    mondayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    mondayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    mondayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }
//                mondayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hourOfDay.toString() + ":" + minute)

            }

            var tokensDoseTwoMonday = DateTimeUtils.convert12HrsTo24Hrs(mondayDoseTwoText.text.toString()).split(":")
            var hoursDoseTwoMonday = tokensDoseTwoMonday[0].toInt()
            var minutesDoseTwoMonday = tokensDoseTwoMonday[1].split(" ")[0].toInt()

            var calendarMondayDoseTwo = Calendar.getInstance()
            calendarMondayDoseTwo.timeInMillis = System.currentTimeMillis()
            var hourMondayDoseTwo = calendarMondayDoseTwo.get(Calendar.HOUR_OF_DAY)
            var minuteMondayDoseTwo = calendarMondayDoseTwo.get(Calendar.MINUTE)
            setTimePickerInterVal(doseTwoTimePickerMonday!!)
            // Configure displayed time
            if (minuteMondayDoseTwo % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteMondayDoseTwo + TIME_PICKER_INTERVAL - minuteMondayDoseTwo % TIME_PICKER_INTERVAL
                minuteMondayDoseTwo = minuteFloor + if (minuteMondayDoseTwo === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteMondayDoseTwo >= 60) {
                    minuteMondayDoseTwo = minuteMondayDoseTwo % 60
                    hourMondayDoseTwo++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseTwoTimePickerMonday?.hour = hourMondayDoseTwo
                    doseTwoTimePickerMonday?.minute = (minuteMondayDoseTwo / TIME_PICKER_INTERVAL)
                } else {
                    doseTwoTimePickerMonday?.currentHour = hourMondayDoseTwo
                    doseTwoTimePickerMonday?.currentMinute = (minuteMondayDoseTwo / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseTwoTimePickerMonday?.hour = hoursDoseTwoMonday
                doseTwoTimePickerMonday?.minute = (minutesDoseTwoMonday / TIME_PICKER_INTERVAL)
            } else {
                doseTwoTimePickerMonday?.currentHour = hoursDoseTwoMonday
                doseTwoTimePickerMonday?.currentMinute = (minutesDoseTwoMonday / TIME_PICKER_INTERVAL)
            }

            doseTwoTimePickerMonday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    mondayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    mondayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    mondayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    mondayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }

            var tokensWakeMonday = DateTimeUtils.convert12HrsTo24Hrs(mondayWakeText.text.toString()).split(":")
            var hoursWakeMonday = tokensWakeMonday[0].toInt()
            var minutesWakeMonday = tokensWakeMonday[1].split(" ")[0].toInt()

            var calendarMondayWake = Calendar.getInstance()
            calendarMondayWake.timeInMillis = System.currentTimeMillis()
            var hourMondayWake = calendarMondayWake.get(Calendar.HOUR_OF_DAY)
            var minuteMondayWake = calendarMondayWake.get(Calendar.MINUTE)
            setTimePickerInterVal(wakeTimePickerMonday!!)
            // Configure displayed time
            if (minuteMondayWake % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteMondayWake + TIME_PICKER_INTERVAL - minuteMondayWake % TIME_PICKER_INTERVAL
                minuteMondayWake = minuteFloor + if (minuteMondayWake === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteMondayWake >= 60) {
                    minuteMondayWake = minuteMondayWake % 60
                    hourMondayWake++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    wakeTimePickerMonday?.hour = hourMondayWake
                    wakeTimePickerMonday?.minute = (minuteMondayWake / TIME_PICKER_INTERVAL)
                } else {
                    wakeTimePickerMonday?.currentHour = hourMondayWake
                    wakeTimePickerMonday?.currentMinute = (minuteMondayWake / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                wakeTimePickerMonday?.hour = hoursWakeMonday
                wakeTimePickerMonday?.minute = (minutesWakeMonday / TIME_PICKER_INTERVAL)
            } else {
                wakeTimePickerMonday?.currentHour = hoursWakeMonday
                wakeTimePickerMonday?.currentMinute = (minutesWakeMonday / TIME_PICKER_INTERVAL)
            }
            wakeTimePickerMonday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    mondayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    mondayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    mondayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    mondayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }

            //mondayPicker set end


            //tuesdayPicker set
            var tokensTuesday = DateTimeUtils.convert12HrsTo24Hrs(tuesdayEatByText.text.toString()).split(":")
            var hoursTuesday = tokensTuesday[0].toInt()
            var minutesTuesday = tokensTuesday[1].split(" ")[0].toInt()
            var calendarTuesdayEatBy = Calendar.getInstance()
            calendarTuesdayEatBy.timeInMillis = System.currentTimeMillis()
            var hourTuesdayEatBy = calendarTuesdayEatBy.get(Calendar.HOUR_OF_DAY)
            var minuteTuesdayEatBy = calendarTuesdayEatBy.get(Calendar.MINUTE)
            setTimePickerInterVal(eatByTimePickerTuesday!!)
            // Configure displayed time
            if (minuteTuesdayEatBy % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteTuesdayEatBy + TIME_PICKER_INTERVAL - minuteTuesdayEatBy % TIME_PICKER_INTERVAL
                minuteTuesdayEatBy = minuteFloor + if (minuteTuesdayEatBy === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteTuesdayEatBy >= 60) {
                    minuteTuesdayEatBy = minuteTuesdayEatBy % 60
                    hourTuesdayEatBy++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    eatByTimePickerTuesday?.hour = hourTuesdayEatBy
                    eatByTimePickerTuesday?.minute = (minuteTuesdayEatBy / TIME_PICKER_INTERVAL)
                } else {
                    eatByTimePickerTuesday?.currentHour = hourTuesdayEatBy
                    eatByTimePickerTuesday?.currentMinute = (minuteTuesdayEatBy / TIME_PICKER_INTERVAL)
                }

            }


            if (Build.VERSION.SDK_INT >= 23) {
                eatByTimePickerTuesday?.hour = hoursTuesday
                eatByTimePickerTuesday?.minute = (minutesTuesday / TIME_PICKER_INTERVAL)
            } else {
                eatByTimePickerTuesday?.currentHour = hoursTuesday
                eatByTimePickerTuesday?.currentMinute = (minutesTuesday / TIME_PICKER_INTERVAL)
            }

            eatByTimePickerTuesday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    tuesdayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    tuesdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    tuesdayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    tuesdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }


            }

            var tokensDoseTuesday = DateTimeUtils.convert12HrsTo24Hrs(tuesdayDoseOneText.text.toString()).split(":")
            var hoursDoseTuesday = tokensDoseTuesday[0].toInt()
            var minutesDoseTuesday = tokensDoseTuesday[1].split(" ")[0].toInt()

            var calendarTuesdayDoseOne = Calendar.getInstance()
            calendarTuesdayDoseOne.timeInMillis = System.currentTimeMillis()
            var hourTuesdayDoseOne = calendarTuesdayDoseOne.get(Calendar.HOUR_OF_DAY)
            var minuteTuesdayDoseOne = calendarTuesdayDoseOne.get(Calendar.MINUTE)
            setTimePickerInterVal(doseOneTimePickerTuesday!!)
            // Configure displayed time
            if (minuteTuesdayDoseOne % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteTuesdayDoseOne + TIME_PICKER_INTERVAL - minuteTuesdayDoseOne % TIME_PICKER_INTERVAL
                minuteTuesdayDoseOne = minuteFloor + if (minuteTuesdayDoseOne === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteTuesdayDoseOne >= 60) {
                    minuteTuesdayDoseOne = minuteTuesdayDoseOne % 60
                    hourTuesdayDoseOne++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseOneTimePickerTuesday?.hour = hourTuesdayDoseOne
                    doseOneTimePickerTuesday?.minute = (minuteTuesdayDoseOne / TIME_PICKER_INTERVAL)
                } else {
                    doseOneTimePickerTuesday?.currentHour = hourTuesdayDoseOne
                    doseOneTimePickerTuesday?.currentMinute = (minuteTuesdayDoseOne / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseOneTimePickerTuesday?.hour = hoursDoseTuesday
                doseOneTimePickerTuesday?.minute = (minutesDoseTuesday / TIME_PICKER_INTERVAL)
            } else {
                doseOneTimePickerTuesday?.currentHour = hoursDoseTuesday
                doseOneTimePickerTuesday?.currentMinute = (minutesDoseTuesday / TIME_PICKER_INTERVAL)
            }
            doseOneTimePickerTuesday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    tuesdayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    tuesdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    tuesdayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    tuesdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }


            }

            var tokensDoseTwoTuesday = DateTimeUtils.convert12HrsTo24Hrs(tuesdayDoseTwoText.text.toString()).split(":")
            var hoursDoseTwoTuesday = tokensDoseTwoTuesday[0].toInt()
            var minutesDoseTwoTuesday = tokensDoseTwoTuesday[1].split(" ")[0].toInt()

            var calendarTuesdayDoseTwo = Calendar.getInstance()
            calendarTuesdayDoseTwo.timeInMillis = System.currentTimeMillis()
            var hourTuesdayDoseTwo = calendarTuesdayDoseTwo.get(Calendar.HOUR_OF_DAY)
            var minuteTuesdayDoseTwo = calendarTuesdayDoseTwo.get(Calendar.MINUTE)
            setTimePickerInterVal(doseTwoTimePickerTuesday!!)
            // Configure displayed time
            if (minuteTuesdayDoseTwo % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteTuesdayDoseTwo + TIME_PICKER_INTERVAL - minuteTuesdayDoseTwo % TIME_PICKER_INTERVAL
                minuteTuesdayDoseTwo = minuteFloor + if (minuteTuesdayDoseTwo === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteTuesdayDoseTwo >= 60) {
                    minuteTuesdayDoseTwo = minuteTuesdayDoseTwo % 60
                    hourTuesdayDoseTwo++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseTwoTimePickerTuesday?.hour = hourTuesdayDoseTwo
                    doseTwoTimePickerTuesday?.minute = (minuteTuesdayDoseTwo / TIME_PICKER_INTERVAL)
                } else {
                    doseTwoTimePickerTuesday?.currentHour = hourTuesdayDoseTwo
                    doseTwoTimePickerTuesday?.currentMinute = (minuteTuesdayDoseTwo / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseTwoTimePickerTuesday?.hour = hoursDoseTwoTuesday
                doseTwoTimePickerTuesday?.minute = (minutesDoseTwoTuesday / TIME_PICKER_INTERVAL)
            } else {
                doseTwoTimePickerTuesday?.currentHour = hoursDoseTwoTuesday
                doseTwoTimePickerTuesday?.currentMinute = (minutesDoseTwoTuesday / TIME_PICKER_INTERVAL)
            }
            doseTwoTimePickerTuesday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    tuesdayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    tuesdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    tuesdayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    tuesdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }


            }


            var tokensWakeTuesday = DateTimeUtils.convert12HrsTo24Hrs(tuesdayWakeText.text.toString()).split(":")
            var hoursWakeTuesday = tokensWakeTuesday[0].toInt()
            var minutesWakeTuesday = tokensWakeTuesday[1].split(" ")[0].toInt()

            var calendarTuesdayWake = Calendar.getInstance()
            calendarTuesdayWake.timeInMillis = System.currentTimeMillis()
            var hourTuesdayWake = calendarTuesdayWake.get(Calendar.HOUR_OF_DAY)
            var minuteTuesdayWake = calendarTuesdayWake.get(Calendar.MINUTE)
            setTimePickerInterVal(WakeTimePickerTuesday!!)
            // Configure displayed time
            if (minuteTuesdayWake % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteTuesdayWake + TIME_PICKER_INTERVAL - minuteTuesdayWake % TIME_PICKER_INTERVAL
                minuteTuesdayWake = minuteFloor + if (minuteTuesdayWake === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteTuesdayWake >= 60) {
                    minuteTuesdayWake = minuteTuesdayWake % 60
                    hourTuesdayWake++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    WakeTimePickerTuesday?.hour = hourTuesdayWake
                    WakeTimePickerTuesday?.minute = (minuteTuesdayWake / TIME_PICKER_INTERVAL)
                } else {
                    WakeTimePickerTuesday?.currentHour = hourTuesdayWake
                    WakeTimePickerTuesday?.currentMinute = (minuteTuesdayWake / TIME_PICKER_INTERVAL)
                }

            }


            if (Build.VERSION.SDK_INT >= 23) {
                WakeTimePickerTuesday?.hour = hoursWakeTuesday
                WakeTimePickerTuesday?.minute = (minutesWakeTuesday / TIME_PICKER_INTERVAL)
            } else {
                WakeTimePickerTuesday?.currentHour = hoursWakeTuesday
                WakeTimePickerTuesday?.currentMinute = (minutesWakeTuesday / TIME_PICKER_INTERVAL)
            }
            WakeTimePickerTuesday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    tuesdayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    tuesdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    tuesdayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    tuesdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }
            //tuesdayPicker set end

            //wednesDayPicker set
            var tokensWednesday = DateTimeUtils.convert12HrsTo24Hrs(wednesdayEatByText.text.toString()).split(":")
            var hoursWednesday = tokensWednesday[0].toInt()
            var minutesWednesday = tokensWednesday[1].split(" ")[0].toInt()

            var calendarWednesdayEatBy = Calendar.getInstance()
            calendarWednesdayEatBy.timeInMillis = System.currentTimeMillis()
            var hourWednesdayEatBy = calendarWednesdayEatBy.get(Calendar.HOUR_OF_DAY)
            var minuteWednesdayEatBy = calendarWednesdayEatBy.get(Calendar.MINUTE)
            setTimePickerInterVal(eatByTimePickerWednesday!!)
            // Configure displayed time
            if (minuteWednesdayEatBy % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteWednesdayEatBy + TIME_PICKER_INTERVAL - minuteWednesdayEatBy % TIME_PICKER_INTERVAL
                minuteWednesdayEatBy = minuteFloor + if (minuteWednesdayEatBy === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteWednesdayEatBy >= 60) {
                    minuteWednesdayEatBy = minuteWednesdayEatBy % 60
                    hourWednesdayEatBy++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    eatByTimePickerWednesday?.hour = hourWednesdayEatBy
                    eatByTimePickerWednesday?.minute = (minuteWednesdayEatBy / TIME_PICKER_INTERVAL)
                } else {
                    eatByTimePickerWednesday?.currentHour = hourWednesdayEatBy
                    eatByTimePickerWednesday?.currentMinute = (minuteWednesdayEatBy / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                eatByTimePickerWednesday?.hour = hoursWednesday
                eatByTimePickerWednesday?.minute = (minutesWednesday / TIME_PICKER_INTERVAL)
            } else {
                eatByTimePickerWednesday?.currentHour = hoursWednesday
                eatByTimePickerWednesday?.currentMinute = (minutesWednesday / TIME_PICKER_INTERVAL)
            }
            eatByTimePickerWednesday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    wednesdayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    wednesdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    wednesdayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    wednesdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }

            var tokensDoseWednesday = DateTimeUtils.convert12HrsTo24Hrs(wednesdayDoseOneText.text.toString()).split(":")
            var hoursDoseWednesday = tokensDoseWednesday[0].toInt()
            var minutesDoseWednesday = tokensDoseWednesday[1].split(" ")[0].toInt()

            var calendarWednesdayDoseOne = Calendar.getInstance()
            calendarWednesdayDoseOne.timeInMillis = System.currentTimeMillis()
            var hourWednesdayDoseOne = calendarWednesdayDoseOne.get(Calendar.HOUR_OF_DAY)
            var minuteWednesdayDoseOne = calendarWednesdayDoseOne.get(Calendar.MINUTE)
            setTimePickerInterVal(doseOneTimePickerWednesday!!)
            // Configure displayed time
            if (minuteWednesdayDoseOne % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteWednesdayDoseOne + TIME_PICKER_INTERVAL - minuteWednesdayDoseOne % TIME_PICKER_INTERVAL
                minuteWednesdayDoseOne = minuteFloor + if (minuteWednesdayDoseOne === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteWednesdayDoseOne >= 60) {
                    minuteWednesdayDoseOne = minuteWednesdayDoseOne % 60
                    hourWednesdayDoseOne++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseOneTimePickerWednesday?.hour = hourWednesdayDoseOne
                    doseOneTimePickerWednesday?.minute = (minuteWednesdayDoseOne / TIME_PICKER_INTERVAL)
                } else {
                    doseOneTimePickerWednesday?.currentHour = hourWednesdayDoseOne
                    doseOneTimePickerWednesday?.currentMinute = (minuteWednesdayDoseOne / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseOneTimePickerWednesday?.hour = hoursDoseWednesday
                doseOneTimePickerWednesday?.minute = (minutesDoseWednesday / TIME_PICKER_INTERVAL)
            } else {
                doseOneTimePickerWednesday?.currentHour = hoursDoseWednesday
                doseOneTimePickerWednesday?.currentMinute = (minutesDoseWednesday / TIME_PICKER_INTERVAL)
            }

            doseOneTimePickerWednesday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    wednesdayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    wednesdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    wednesdayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    wednesdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }

            var tokensDoseTwoWednesday = DateTimeUtils.convert12HrsTo24Hrs(wednesdayDoseTwoText.text.toString()).split(":")
            var hoursDoseTwoWednesday = tokensDoseTwoWednesday[0].toInt()
            var minutesDoseTwoWednesday = tokensDoseTwoWednesday[1].split(" ")[0].toInt()

            var calendarWednesdayDoseTwo = Calendar.getInstance()
            calendarWednesdayDoseTwo.timeInMillis = System.currentTimeMillis()
            var hourWednesdayDoseTwo = calendarWednesdayDoseTwo.get(Calendar.HOUR_OF_DAY)
            var minuteWednesdayDoseTwo = calendarWednesdayDoseTwo.get(Calendar.MINUTE)
            setTimePickerInterVal(doseTwoTimePickerWednesday!!)
            // Configure displayed time
            if (minuteWednesdayDoseTwo % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteWednesdayDoseTwo + TIME_PICKER_INTERVAL - minuteWednesdayDoseTwo % TIME_PICKER_INTERVAL
                minuteWednesdayDoseTwo = minuteFloor + if (minuteWednesdayDoseTwo === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteWednesdayDoseTwo >= 60) {
                    minuteWednesdayDoseTwo = minuteWednesdayDoseTwo % 60
                    hourWednesdayDoseTwo++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseTwoTimePickerWednesday?.hour = hourWednesdayDoseTwo
                    doseTwoTimePickerWednesday?.minute = (minuteWednesdayDoseTwo / TIME_PICKER_INTERVAL)
                } else {
                    doseTwoTimePickerWednesday?.currentHour = hourWednesdayDoseTwo
                    doseTwoTimePickerWednesday?.currentMinute = (minuteWednesdayDoseTwo / TIME_PICKER_INTERVAL)
                }

            }


            if (Build.VERSION.SDK_INT >= 23) {
                doseTwoTimePickerWednesday?.hour = hoursDoseTwoWednesday
                doseTwoTimePickerWednesday?.minute = (minutesDoseTwoWednesday / TIME_PICKER_INTERVAL)
            } else {
                doseTwoTimePickerWednesday?.currentHour = hoursDoseTwoWednesday
                doseTwoTimePickerWednesday?.currentMinute = (minutesDoseTwoWednesday / TIME_PICKER_INTERVAL)
            }
            doseTwoTimePickerWednesday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    wednesdayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    wednesdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    wednesdayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    wednesdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }


            var tokensWakeWednesday = DateTimeUtils.convert12HrsTo24Hrs(wednesdayWakeText.text.toString()).split(":")
            var hoursWakeWednesday = tokensWakeWednesday[0].toInt()
            var minutesWakeWednesday = tokensWakeWednesday[1].split(" ")[0].toInt()


            var calendarWednesdayWake = Calendar.getInstance()
            calendarWednesdayWake.timeInMillis = System.currentTimeMillis()
            var hourWednesdayWake = calendarWednesdayWake.get(Calendar.HOUR_OF_DAY)
            var minuteWednesdayWake = calendarWednesdayWake.get(Calendar.MINUTE)
            setTimePickerInterVal(wakeTimePickerWednesday!!)
            // Configure displayed time
            if (minuteWednesdayWake % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteWednesdayWake + TIME_PICKER_INTERVAL - minuteWednesdayWake % TIME_PICKER_INTERVAL
                minuteWednesdayWake = minuteFloor + if (minuteWednesdayWake === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteWednesdayWake >= 60) {
                    minuteWednesdayWake = minuteWednesdayWake % 60
                    hourWednesdayWake++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    wakeTimePickerWednesday?.hour = hourWednesdayWake
                    wakeTimePickerWednesday?.minute = (minuteWednesdayWake / TIME_PICKER_INTERVAL)
                } else {
                    wakeTimePickerWednesday?.currentHour = hourWednesdayWake
                    wakeTimePickerWednesday?.currentMinute = (minuteWednesdayWake / TIME_PICKER_INTERVAL)
                }

            }


            if (Build.VERSION.SDK_INT >= 23) {
                wakeTimePickerWednesday?.hour = hoursWakeWednesday
                wakeTimePickerWednesday?.minute = (minutesWakeWednesday / TIME_PICKER_INTERVAL)
            } else {
                wakeTimePickerWednesday?.currentHour = hoursWakeWednesday
                wakeTimePickerWednesday?.currentMinute = (minutesWakeWednesday / TIME_PICKER_INTERVAL)
            }
            wakeTimePickerWednesday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    wednesdayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    wednesdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    wednesdayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    wednesdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }
            //wednesDayPicker set end


            //thursdayPicker set
            var tokensThursday = DateTimeUtils.convert12HrsTo24Hrs(thursdayEatByText.text.toString()).split(":")
            var hoursThursday = tokensThursday[0].toInt()
            var minutesThursday = tokensThursday[1].split(" ")[0].toInt()

            var calendarThursdayEatBy = Calendar.getInstance()
            calendarThursdayEatBy.timeInMillis = System.currentTimeMillis()
            var hourThursdayEatBy = calendarThursdayEatBy.get(Calendar.HOUR_OF_DAY)
            var minuteThursdayEatBy = calendarThursdayEatBy.get(Calendar.MINUTE)
            setTimePickerInterVal(eatByTimePickerThursday!!)
            // Configure displayed time
            if (minuteThursdayEatBy % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteThursdayEatBy + TIME_PICKER_INTERVAL - minuteThursdayEatBy % TIME_PICKER_INTERVAL
                minuteThursdayEatBy = minuteFloor + if (minuteThursdayEatBy === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteThursdayEatBy >= 60) {
                    minuteThursdayEatBy = minuteThursdayEatBy % 60
                    hourThursdayEatBy++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    eatByTimePickerThursday?.hour = hourThursdayEatBy
                    eatByTimePickerThursday?.minute = (minuteThursdayEatBy / TIME_PICKER_INTERVAL)
                } else {
                    eatByTimePickerThursday?.currentHour = hourThursdayEatBy
                    eatByTimePickerThursday?.currentMinute = (minuteThursdayEatBy / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                eatByTimePickerThursday?.hour = hoursThursday
                eatByTimePickerThursday?.minute = (minutesThursday / TIME_PICKER_INTERVAL)
            } else {
                eatByTimePickerThursday?.currentHour = hoursThursday
                eatByTimePickerThursday?.currentMinute = (minutesThursday / TIME_PICKER_INTERVAL)
            }
            eatByTimePickerThursday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    thursdayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    thursdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    thursdayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    thursdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }


            var tokensDoseThursday = DateTimeUtils.convert12HrsTo24Hrs(thursdayDoseOneText.text.toString()).split(":")
            var hoursDoseThursday = tokensDoseThursday[0].toInt()
            var minutesDoseThursday = tokensDoseThursday[1].split(" ")[0].toInt()

            var calendarThursdayDoseOne = Calendar.getInstance()
            calendarThursdayDoseOne.timeInMillis = System.currentTimeMillis()
            var hourThursdayDoseOne = calendarThursdayDoseOne.get(Calendar.HOUR_OF_DAY)
            var minuteThursdayDoseOne = calendarThursdayDoseOne.get(Calendar.MINUTE)
            setTimePickerInterVal(doseOneTimePickerThursday!!)
            // Configure displayed time
            if (minuteThursdayDoseOne % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteThursdayDoseOne + TIME_PICKER_INTERVAL - minuteThursdayDoseOne % TIME_PICKER_INTERVAL
                minuteThursdayDoseOne = minuteFloor + if (minuteThursdayDoseOne === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteThursdayDoseOne >= 60) {
                    minuteThursdayDoseOne = minuteThursdayDoseOne % 60
                    hourThursdayDoseOne++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseOneTimePickerThursday?.hour = hourThursdayDoseOne
                    doseOneTimePickerThursday?.minute = (minuteThursdayDoseOne / TIME_PICKER_INTERVAL)
                } else {
                    doseOneTimePickerThursday?.currentHour = hourThursdayDoseOne
                    doseOneTimePickerThursday?.currentMinute = (minuteThursdayDoseOne / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseOneTimePickerThursday?.hour = hoursDoseThursday
                doseOneTimePickerThursday?.minute = (minutesDoseThursday / TIME_PICKER_INTERVAL)
            } else {
                doseOneTimePickerThursday?.currentHour = hoursDoseThursday
                doseOneTimePickerThursday?.currentMinute = (minutesDoseThursday / TIME_PICKER_INTERVAL)
            }
            doseOneTimePickerThursday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    thursdayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    thursdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    thursdayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    thursdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }

            var tokensDoseTwoThursday = DateTimeUtils.convert12HrsTo24Hrs(thursdayDoseTwoText.text.toString()).split(":")
            var hoursDoseTwoThursday = tokensDoseTwoThursday[0].toInt()
            var minutesDoseTwoThursday = tokensDoseTwoThursday[1].split(" ")[0].toInt()

            var calendarThursdayDoseTwo = Calendar.getInstance()
            calendarThursdayDoseTwo.timeInMillis = System.currentTimeMillis()
            var hourThursdayDoseTwo = calendarThursdayDoseTwo.get(Calendar.HOUR_OF_DAY)
            var minuteThursdayDoseTwo = calendarThursdayDoseTwo.get(Calendar.MINUTE)
            setTimePickerInterVal(doseTwoTimePickerThursday!!)
            // Configure displayed time
            if (minuteThursdayDoseTwo % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteThursdayDoseTwo + TIME_PICKER_INTERVAL - minuteThursdayDoseTwo % TIME_PICKER_INTERVAL
                minuteThursdayDoseTwo = minuteFloor + if (minuteThursdayDoseTwo === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteThursdayDoseTwo >= 60) {
                    minuteThursdayDoseTwo = minuteThursdayDoseTwo % 60
                    hourThursdayDoseTwo++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseTwoTimePickerThursday?.hour = hourThursdayDoseTwo
                    doseTwoTimePickerThursday?.minute = (minuteThursdayDoseTwo / TIME_PICKER_INTERVAL)
                } else {
                    doseTwoTimePickerThursday?.currentHour = hourThursdayDoseTwo
                    doseTwoTimePickerThursday?.currentMinute = (minuteThursdayDoseTwo / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseTwoTimePickerThursday?.hour = hoursDoseTwoThursday
                doseTwoTimePickerThursday?.minute = (minutesDoseTwoThursday / TIME_PICKER_INTERVAL)
            } else {
                doseTwoTimePickerThursday?.currentHour = hoursDoseTwoThursday
                doseTwoTimePickerThursday?.currentMinute = (minutesDoseTwoThursday / TIME_PICKER_INTERVAL)
            }
            doseTwoTimePickerThursday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    thursdayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    thursdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    thursdayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    thursdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }

            var tokensWakeThursday = DateTimeUtils.convert12HrsTo24Hrs(thursdayWakeText.text.toString()).split(":")
            var hoursWakeThursday = tokensWakeThursday[0].toInt()
            var minutesWakeThursday = tokensWakeThursday[1].split(" ")[0].toInt()

            var calendarThursdayWake = Calendar.getInstance()
            calendarThursdayWake.timeInMillis = System.currentTimeMillis()
            var hourThursdayWake = calendarThursdayWake.get(Calendar.HOUR_OF_DAY)
            var minuteThursdayWake = calendarThursdayWake.get(Calendar.MINUTE)
            setTimePickerInterVal(wakeTimePickerThursday!!)
            // Configure displayed time
            if (minuteThursdayWake % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteThursdayWake + TIME_PICKER_INTERVAL - minuteThursdayWake % TIME_PICKER_INTERVAL
                minuteThursdayWake = minuteFloor + if (minuteThursdayWake === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteThursdayWake >= 60) {
                    minuteThursdayWake = minuteThursdayWake % 60
                    hourThursdayWake++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    wakeTimePickerThursday?.hour = hourThursdayWake
                    wakeTimePickerThursday?.minute = (minuteThursdayWake / TIME_PICKER_INTERVAL)
                } else {
                    wakeTimePickerThursday?.currentHour = hourThursdayWake
                    wakeTimePickerThursday?.currentMinute = (minuteThursdayWake / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                wakeTimePickerThursday?.hour = hoursWakeThursday
                wakeTimePickerThursday?.minute = (minutesWakeThursday / TIME_PICKER_INTERVAL)
            } else {
                wakeTimePickerThursday?.currentHour = hoursWakeThursday
                wakeTimePickerThursday?.currentMinute = (minutesWakeThursday / TIME_PICKER_INTERVAL)
            }
            wakeTimePickerThursday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    thursdayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    thursdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    thursdayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    thursdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }
            //thursdayPicker set end

            //fridayPicker set
            var tokensFriday = DateTimeUtils.convert12HrsTo24Hrs(fridayEatByText.text.toString()).split(":")
            var hoursFriday = tokensFriday[0].toInt()
            var minutesFriday = tokensFriday[1].split(" ")[0].toInt()

            var calendarFridayEatBy = Calendar.getInstance()
            calendarFridayEatBy.timeInMillis = System.currentTimeMillis()
            var hourFridayEatBy = calendarFridayEatBy.get(Calendar.HOUR_OF_DAY)
            var minuteFridayEatBy = calendarFridayEatBy.get(Calendar.MINUTE)
            setTimePickerInterVal(eatByTimePickerFriday!!)
            // Configure displayed time
            if (minuteFridayEatBy % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteFridayEatBy + TIME_PICKER_INTERVAL - minuteFridayEatBy % TIME_PICKER_INTERVAL
                minuteFridayEatBy = minuteFloor + if (minuteFridayEatBy === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteFridayEatBy >= 60) {
                    minuteFridayEatBy = minuteFridayEatBy % 60
                    hourFridayEatBy++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    eatByTimePickerFriday?.hour = hourFridayEatBy
                    eatByTimePickerFriday?.minute = (minuteFridayEatBy / TIME_PICKER_INTERVAL)
                } else {
                    eatByTimePickerFriday?.currentHour = hourFridayEatBy
                    eatByTimePickerFriday?.currentMinute = (minuteFridayEatBy / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                eatByTimePickerFriday?.hour = hoursFriday
                eatByTimePickerFriday?.minute = (minutesFriday / TIME_PICKER_INTERVAL)
            } else {
                eatByTimePickerFriday?.currentHour = hoursFriday
                eatByTimePickerFriday?.currentMinute = (minutesFriday / TIME_PICKER_INTERVAL)
            }
            eatByTimePickerFriday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    fridayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    fridayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    fridayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    fridayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }


            var tokensDoseFriday = DateTimeUtils.convert12HrsTo24Hrs(fridayDoseOneText.text.toString()).split(":")
            var hoursDoseFriday = tokensDoseFriday[0].toInt()
            var minutesDoseFriday = tokensDoseFriday[1].split(" ")[0].toInt()

            var calendarFridayDoseOne = Calendar.getInstance()
            calendarFridayDoseOne.timeInMillis = System.currentTimeMillis()
            var hourFridayDoseOne = calendarFridayDoseOne.get(Calendar.HOUR_OF_DAY)
            var minuteFridayDoseOne = calendarFridayDoseOne.get(Calendar.MINUTE)
            setTimePickerInterVal(doseOneTimePickerFriday!!)
            // Configure displayed time
            if (minuteFridayDoseOne % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteFridayDoseOne + TIME_PICKER_INTERVAL - minuteFridayDoseOne % TIME_PICKER_INTERVAL
                minuteFridayDoseOne = minuteFloor + if (minuteFridayDoseOne === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteFridayDoseOne >= 60) {
                    minuteFridayDoseOne = minuteFridayDoseOne % 60
                    hourFridayDoseOne++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseOneTimePickerFriday?.hour = hourFridayDoseOne
                    doseOneTimePickerFriday?.minute = (minuteFridayDoseOne / TIME_PICKER_INTERVAL)
                } else {
                    doseOneTimePickerFriday?.currentHour = hourFridayDoseOne
                    doseOneTimePickerFriday?.currentMinute = (minuteFridayDoseOne / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseOneTimePickerFriday?.hour = hoursDoseFriday
                doseOneTimePickerFriday?.minute = (minutesDoseFriday / TIME_PICKER_INTERVAL)
            } else {
                doseOneTimePickerFriday?.currentHour = hoursDoseFriday
                doseOneTimePickerFriday?.currentMinute = (minutesDoseFriday / TIME_PICKER_INTERVAL)
            }

            doseOneTimePickerFriday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    fridayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    fridayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    fridayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    fridayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }

            var tokensDoseTwoFriday = DateTimeUtils.convert12HrsTo24Hrs(fridayDoseTwoText.text.toString()).split(":")
            var hoursDoseTwoFriday = tokensDoseTwoFriday[0].toInt()
            var minutesDoseTwoFriday = tokensDoseTwoFriday[1].split(" ")[0].toInt()

            var calendarFridayDoseTwo = Calendar.getInstance()
            calendarFridayDoseTwo.timeInMillis = System.currentTimeMillis()
            var hourFridayDoseTwo = calendarFridayDoseTwo.get(Calendar.HOUR_OF_DAY)
            var minuteFridayDoseTwo = calendarFridayDoseTwo.get(Calendar.MINUTE)
            setTimePickerInterVal(doseTwoTimePickerFriday!!)
            // Configure displayed time
            if (minuteFridayDoseTwo % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteFridayDoseTwo + TIME_PICKER_INTERVAL - minuteFridayDoseTwo % TIME_PICKER_INTERVAL
                minuteFridayDoseTwo = minuteFloor + if (minuteFridayDoseTwo === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteFridayDoseTwo >= 60) {
                    minuteFridayDoseTwo = minuteFridayDoseTwo % 60
                    hourFridayDoseTwo++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseTwoTimePickerFriday?.hour = hourFridayDoseTwo
                    doseTwoTimePickerFriday?.minute = (minuteFridayDoseTwo / TIME_PICKER_INTERVAL)
                } else {
                    doseTwoTimePickerFriday?.currentHour = hourFridayDoseTwo
                    doseTwoTimePickerFriday?.currentMinute = (minuteFridayDoseTwo / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseTwoTimePickerFriday?.hour = hoursDoseTwoFriday
                doseTwoTimePickerFriday?.minute = (minutesDoseTwoFriday / TIME_PICKER_INTERVAL)
            } else {
                doseTwoTimePickerFriday?.currentHour = hoursDoseTwoFriday
                doseTwoTimePickerFriday?.currentMinute = (minutesDoseTwoFriday / TIME_PICKER_INTERVAL)
            }
            doseTwoTimePickerFriday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    fridayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    fridayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    fridayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    fridayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }


            var tokensWakeFriday = DateTimeUtils.convert12HrsTo24Hrs(fridayWakeText.text.toString()).split(":")
            var hoursWakeFriday = tokensWakeFriday[0].toInt()
            var minutesWakeFriday = tokensWakeFriday[1].split(" ")[0].toInt()

            var calendarFridayWake = Calendar.getInstance()
            calendarFridayWake.timeInMillis = System.currentTimeMillis()
            var hourFridayWake = calendarFridayWake.get(Calendar.HOUR_OF_DAY)
            var minuteFridayWake = calendarFridayWake.get(Calendar.MINUTE)
            setTimePickerInterVal(wakeTimePickerFriday!!)
            // Configure displayed time
            if (minuteFridayWake % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteFridayWake + TIME_PICKER_INTERVAL - minuteFridayWake % TIME_PICKER_INTERVAL
                minuteFridayWake = minuteFloor + if (minuteFridayWake === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteFridayWake >= 60) {
                    minuteFridayWake = minuteFridayWake % 60
                    hourFridayWake++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    wakeTimePickerFriday?.hour = hourFridayWake
                    wakeTimePickerFriday?.minute = (minuteFridayWake / TIME_PICKER_INTERVAL)
                } else {
                    wakeTimePickerFriday?.currentHour = hourFridayWake
                    wakeTimePickerFriday?.currentMinute = (minuteFridayWake / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                wakeTimePickerFriday?.hour = hoursWakeFriday
                wakeTimePickerFriday?.minute = (minutesWakeFriday / TIME_PICKER_INTERVAL)
            } else {
                wakeTimePickerFriday?.currentHour = hoursWakeFriday
                wakeTimePickerFriday?.currentMinute = (minutesWakeFriday / TIME_PICKER_INTERVAL)
            }
            wakeTimePickerFriday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    fridayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    fridayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    fridayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    fridayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }
            //fridayPicker set end

            //saturdayPicker set
            var tokensSaturday = DateTimeUtils.convert12HrsTo24Hrs(saturdayEatByText.text.toString()).split(":")
            var hoursSaturday = tokensSaturday[0].toInt()
            var minutesSaturday = tokensSaturday[1].split(" ")[0].toInt()

            var calendarSaturdayEatBy = Calendar.getInstance()
            calendarSaturdayEatBy.timeInMillis = System.currentTimeMillis()
            var hourSaturdayEatBy = calendarSaturdayEatBy.get(Calendar.HOUR_OF_DAY)
            var minuteSaturdayEatBy = calendarSaturdayEatBy.get(Calendar.MINUTE)
            setTimePickerInterVal(eatByTimePickerSaturday!!)
            // Configure displayed time
            if (minuteSaturdayEatBy % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteSaturdayEatBy + TIME_PICKER_INTERVAL - minuteSaturdayEatBy % TIME_PICKER_INTERVAL
                minuteSaturdayEatBy = minuteFloor + if (minuteSaturdayEatBy === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteSaturdayEatBy >= 60) {
                    minuteSaturdayEatBy = minuteSaturdayEatBy % 60
                    hourSaturdayEatBy++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    eatByTimePickerSaturday?.hour = hourSaturdayEatBy
                    eatByTimePickerSaturday?.minute = (minuteSaturdayEatBy / TIME_PICKER_INTERVAL)
                } else {
                    eatByTimePickerSaturday?.currentHour = hourSaturdayEatBy
                    eatByTimePickerSaturday?.currentMinute = (minuteSaturdayEatBy / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                eatByTimePickerSaturday?.hour = hoursSaturday
                eatByTimePickerSaturday?.minute = (minutesSaturday / TIME_PICKER_INTERVAL)
            } else {
                eatByTimePickerSaturday?.currentHour = hoursSaturday
                eatByTimePickerSaturday?.currentMinute = (minutesSaturday / TIME_PICKER_INTERVAL)
            }
            eatByTimePickerSaturday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    saturdayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    saturdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    saturdayEatByText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    saturdayEatByText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }


            var tokensDoseSaturday = DateTimeUtils.convert12HrsTo24Hrs(saturdayDoseOneText.text.toString()).split(":")
            var hoursDoseSaturday = tokensDoseSaturday[0].toInt()
            var minutesDoseSaturday = tokensDoseSaturday[1].split(" ")[0].toInt()

            var calendarSaturdayDoseOne = Calendar.getInstance()
            calendarSaturdayDoseOne.timeInMillis = System.currentTimeMillis()
            var hourSaturdayDoseOne = calendarSaturdayDoseOne.get(Calendar.HOUR_OF_DAY)
            var minuteSaturdayDoseOne = calendarSaturdayDoseOne.get(Calendar.MINUTE)
            setTimePickerInterVal(doseOneTimePickerSaturday!!)
            // Configure displayed time
            if (minuteSaturdayDoseOne % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteSaturdayDoseOne + TIME_PICKER_INTERVAL - minuteSaturdayDoseOne % TIME_PICKER_INTERVAL
                minuteSaturdayDoseOne = minuteFloor + if (minuteSaturdayDoseOne === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteSaturdayDoseOne >= 60) {
                    minuteSaturdayDoseOne = minuteSaturdayDoseOne % 60
                    hourSaturdayDoseOne++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseOneTimePickerSaturday?.hour = hourSaturdayDoseOne
                    doseOneTimePickerSaturday?.minute = (minuteSaturdayDoseOne / TIME_PICKER_INTERVAL)
                } else {
                    doseOneTimePickerSaturday?.currentHour = hourSaturdayDoseOne
                    doseOneTimePickerSaturday?.currentMinute = (minuteSaturdayDoseOne / TIME_PICKER_INTERVAL)
                }

            }


            if (Build.VERSION.SDK_INT >= 23) {
                doseOneTimePickerSaturday?.hour = hoursDoseSaturday
                doseOneTimePickerSaturday?.minute = (minutesDoseSaturday / TIME_PICKER_INTERVAL)
            } else {
                doseOneTimePickerSaturday?.currentHour = hoursDoseSaturday
                doseOneTimePickerSaturday?.currentMinute = (minutesDoseSaturday / TIME_PICKER_INTERVAL)
            }
            doseOneTimePickerSaturday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    saturdayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    saturdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    saturdayDoseOneText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    saturdayDoseOneText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }

            var tokensDoseTwoSaturday = DateTimeUtils.convert12HrsTo24Hrs(saturdayDoseTwoText.text.toString()).split(":")
            var hoursDoseTwoSaturday = tokensDoseTwoSaturday[0].toInt()
            var minutesDoseTwoSaturday = tokensDoseTwoSaturday[1].split(" ")[0].toInt()

            var calendarSaturdayDoseTwo = Calendar.getInstance()
            calendarSaturdayDoseTwo.timeInMillis = System.currentTimeMillis()
            var hourSaturdayDoseTwo = calendarSaturdayDoseTwo.get(Calendar.HOUR_OF_DAY)
            var minuteSaturdayDoseTwo = calendarSaturdayDoseTwo.get(Calendar.MINUTE)
            setTimePickerInterVal(doseTwoTimePickerSaturday!!)
            // Configure displayed time
            if (minuteSaturdayDoseTwo % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteSaturdayDoseTwo + TIME_PICKER_INTERVAL - minuteSaturdayDoseTwo % TIME_PICKER_INTERVAL
                minuteSaturdayDoseTwo = minuteFloor + if (minuteSaturdayDoseTwo === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteSaturdayDoseTwo >= 60) {
                    minuteSaturdayDoseTwo = minuteSaturdayDoseTwo % 60
                    hourSaturdayDoseTwo++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    doseTwoTimePickerSaturday?.hour = hourSaturdayDoseTwo
                    doseTwoTimePickerSaturday?.minute = (minuteSaturdayDoseTwo / TIME_PICKER_INTERVAL)
                } else {
                    doseTwoTimePickerSaturday?.currentHour = hourSaturdayDoseTwo
                    doseTwoTimePickerSaturday?.currentMinute = (minuteSaturdayDoseTwo / TIME_PICKER_INTERVAL)
                }

            }

            if (Build.VERSION.SDK_INT >= 23) {
                doseTwoTimePickerSaturday?.hour = hoursDoseTwoSaturday
                doseTwoTimePickerSaturday?.minute = (minutesDoseTwoSaturday / TIME_PICKER_INTERVAL)
            } else {
                doseTwoTimePickerSaturday?.currentHour = hoursDoseTwoSaturday
                doseTwoTimePickerSaturday?.currentMinute = (minutesDoseTwoSaturday / TIME_PICKER_INTERVAL)
            }
            doseTwoTimePickerSaturday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    saturdayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    saturdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    saturdayDoseTwoText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    saturdayDoseTwoText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }

            }


            var tokensWakeSaturday = DateTimeUtils.convert12HrsTo24Hrs(saturdayWakeText.text.toString()).split(":")
            var hoursWakeSaturday = tokensWakeSaturday[0].toInt()
            var minutesWakeSaturday = tokensWakeSaturday[1].split(" ")[0].toInt()

            var calendarSaturdayWake = Calendar.getInstance()
            calendarSaturdayWake.timeInMillis = System.currentTimeMillis()
            var hourSaturdayWake = calendarSaturdayWake.get(Calendar.HOUR_OF_DAY)
            var minuteSaturdayWake = calendarSaturdayWake.get(Calendar.MINUTE)
            setTimePickerInterVal(wakeTimePickerSaturday!!)
            // Configure displayed time
            if (minuteSaturdayWake % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteSaturdayWake + TIME_PICKER_INTERVAL - minuteSaturdayWake % TIME_PICKER_INTERVAL
                minuteSaturdayWake = minuteFloor + if (minuteSaturdayWake === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteSaturdayWake >= 60) {
                    minuteSaturdayWake = minuteSaturdayWake % 60
                    hourSaturdayWake++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    wakeTimePickerSaturday?.hour = hourSaturdayWake
                    wakeTimePickerSaturday?.minute = (minuteSaturdayWake / TIME_PICKER_INTERVAL)
                } else {
                    wakeTimePickerSaturday?.currentHour = hourSaturdayWake
                    wakeTimePickerSaturday?.currentMinute = (minuteSaturdayWake / TIME_PICKER_INTERVAL)
                }

            }


            if (Build.VERSION.SDK_INT >= 23) {
                wakeTimePickerSaturday?.hour = hoursWakeSaturday
                wakeTimePickerSaturday?.minute = (minutesWakeSaturday / TIME_PICKER_INTERVAL)
            } else {
                wakeTimePickerSaturday?.currentHour = hoursWakeSaturday
                wakeTimePickerSaturday?.currentMinute = (minutesWakeSaturday / TIME_PICKER_INTERVAL)
            }
            wakeTimePickerSaturday?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    saturdayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    saturdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    saturdayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                    saturdayWakeText?.setTextColor(ContextCompat.getColor(activity!!, R.color.redText))
                }


            }
            //saturdayPicker set end
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun handleNapSoundReminder() {
        reminderLayoutSunday?.setOnClickListener {

            var intent = Intent(activity!!, ReminderFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 1)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        reminderLayoutMonday?.setOnClickListener {
            var intent = Intent(activity!!, ReminderFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 2)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        reminderLayoutTuesday?.setOnClickListener {
            var intent = Intent(activity!!, ReminderFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 3)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        reminderLayoutWednesday?.setOnClickListener {
            var intent = Intent(activity!!, ReminderFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 4)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        reminderLayoutThursday?.setOnClickListener {
            var intent = Intent(activity!!, ReminderFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 5)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        reminderLayoutFriday?.setOnClickListener {
            var intent = Intent(activity!!, ReminderFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 6)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        reminderLayoutSaturday?.setOnClickListener {
            var intent = Intent(activity!!, ReminderFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 7)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)

        }


    }

    fun handleSoundsReminder() {
        soundsLayoutSunday?.setOnClickListener {
            var intent = Intent(activity!!, AlarmFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 1)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        soundsLayoutMonday?.setOnClickListener {
            var intent = Intent(activity!!, AlarmFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 2)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        soundsLayoutTuesday?.setOnClickListener {
            var intent = Intent(activity!!, AlarmFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 3)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        soundsLayoutWednesday?.setOnClickListener {
            var intent = Intent(activity!!, AlarmFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 4)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        soundsLayoutThursday?.setOnClickListener {
            var intent = Intent(activity!!, AlarmFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 5)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        soundsLayoutFriday?.setOnClickListener {
            var intent = Intent(activity!!, AlarmFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 6)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        soundsLayoutSaturday?.setOnClickListener {
            var intent = Intent(activity!!, AlarmFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 7)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    fun handleNap() {
        napScheduleLayoutSunday?.setOnClickListener {
            var intent = Intent(activity!!, NapFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 1)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle!!)

            startActivity(intent)

        }
        napLayoutScheduleMonday?.setOnClickListener {
            var intent = Intent(activity!!, NapFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 2)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle!!)

            startActivity(intent)

        }
        napLayoutScheduleTuesday?.setOnClickListener {
            var intent = Intent(activity!!, NapFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 3)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle!!)
            startActivity(intent)

        }
        napLayoutScheduleWednesday?.setOnClickListener {
            var intent = Intent(activity!!, NapFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 4)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle!!)

            startActivity(intent)

        }
        napLayoutScheduleThursday?.setOnClickListener {
            var intent = Intent(activity!!, NapFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 5)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle!!)
            startActivity(intent)

        }

        napLayoutScheduleFriday?.setOnClickListener {
            var intent = Intent(activity!!, NapFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 6)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle!!)

            startActivity(intent)

        }

        napLayoutScheduleSaturday?.setOnClickListener {
            var intent = Intent(activity!!, NapFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("week", 7)
            bundle.putBoolean("isSetup", isPlanSetup!!)
            intent.putExtras(bundle!!)

            startActivity(intent)

        }
    }


    private fun increaseDecreaeByOne() {
        increaseByOneSunday?.setOnClickListener {
            var eatBy = sundayEatByText?.text.toString()
            var doseOne = sundayDoseOneText?.text.toString()
            var doseTwo = sundayDoseTwoText?.text.toString()
            var wake = sundayWakeText?.text.toString()

            sundayEatByText?.text = DateTimeUtils.increaseTimeByOne(eatBy)!!
            sundayDoseOneText?.text = DateTimeUtils.increaseTimeByOne(doseOne)!!
            sundayDoseTwoText?.text = DateTimeUtils.increaseTimeByOne(doseTwo)!!
            sundayWakeText?.text = DateTimeUtils.increaseTimeByOne(wake)


            increaseTimeOfSpinnerByOne(sundayEatByText?.text!!.toString(), eatByTimePickerSunday!!)
            increaseTimeOfSpinnerByOne(sundayDoseOneText?.text!!.toString(), doseOneTimePickerSunday!!)
            increaseTimeOfSpinnerByOne(sundayDoseTwoText?.text!!.toString(), doseTwoTimePickerSunday!!)
            increaseTimeOfSpinnerByOne(sundayWakeText?.text!!.toString(), wakeTimePickerSunday!!)


        }

        decreaseByOneSunday?.setOnClickListener {
            var eatBy = sundayEatByText?.text.toString()
            var doseOne = sundayDoseOneText?.text.toString()
            var doseTwo = sundayDoseTwoText?.text.toString()
            var wake = sundayWakeText?.text.toString()


            sundayEatByText?.text = DateTimeUtils.decreaseTimeByOne(eatBy)!!
            sundayDoseOneText?.text = DateTimeUtils.decreaseTimeByOne(doseOne)!!
            sundayDoseTwoText?.text = DateTimeUtils.decreaseTimeByOne(doseTwo)!!
            sundayWakeText?.text = DateTimeUtils.decreaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(sundayEatByText?.text!!.toString(), eatByTimePickerSunday!!)
            increaseTimeOfSpinnerByOne(sundayDoseOneText?.text!!.toString(), doseOneTimePickerSunday!!)
            increaseTimeOfSpinnerByOne(sundayDoseTwoText?.text!!.toString(), doseTwoTimePickerSunday!!)
            increaseTimeOfSpinnerByOne(sundayWakeText?.text!!.toString(), wakeTimePickerSunday!!)
        }


        increaseByOneMonday?.setOnClickListener {
            var eatBy = mondayEatByText?.text.toString()
            var doseOne = mondayDoseOneText?.text.toString()
            var doseTwo = mondayDoseTwoText?.text.toString()
            var wake = mondayWakeText?.text.toString()

            mondayEatByText?.text = DateTimeUtils.increaseTimeByOne(eatBy)!!
            mondayDoseOneText?.text = DateTimeUtils.increaseTimeByOne(doseOne)!!
            mondayDoseTwoText?.text = DateTimeUtils.increaseTimeByOne(doseTwo)!!
            mondayWakeText?.text = DateTimeUtils.increaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(mondayEatByText?.text!!.toString(), eatByTimePickerMonday!!)
            increaseTimeOfSpinnerByOne(mondayDoseOneText?.text!!.toString(), doseOneTimePickerMonday!!)
            increaseTimeOfSpinnerByOne(mondayDoseTwoText?.text!!.toString(), doseTwoTimePickerMonday!!)
            increaseTimeOfSpinnerByOne(mondayWakeText?.text!!.toString(), wakeTimePickerMonday!!)
        }

        decreaseByOneMonday?.setOnClickListener {
            var eatBy = mondayEatByText?.text.toString()
            var doseOne = mondayDoseOneText?.text.toString()
            var doseTwo = mondayDoseTwoText?.text.toString()
            var wake = mondayWakeText?.text.toString()

            mondayEatByText?.text = DateTimeUtils.decreaseTimeByOne(eatBy)!!
            mondayDoseOneText?.text = DateTimeUtils.decreaseTimeByOne(doseOne)!!
            mondayDoseTwoText?.text = DateTimeUtils.decreaseTimeByOne(doseTwo)!!
            mondayWakeText?.text = DateTimeUtils.decreaseTimeByOne(wake)


            increaseTimeOfSpinnerByOne(mondayEatByText?.text!!.toString(), eatByTimePickerMonday!!)
            increaseTimeOfSpinnerByOne(mondayDoseOneText?.text!!.toString(), doseOneTimePickerMonday!!)
            increaseTimeOfSpinnerByOne(mondayDoseTwoText?.text!!.toString(), doseTwoTimePickerMonday!!)
            increaseTimeOfSpinnerByOne(mondayWakeText?.text!!.toString(), wakeTimePickerMonday!!)
        }


        increaseByOneTuesday?.setOnClickListener {
            var eatBy = tuesdayEatByText?.text.toString()
            var doseOne = tuesdayDoseOneText?.text.toString()
            var doseTwo = tuesdayDoseTwoText?.text.toString()
            var wake = tuesdayWakeText?.text.toString()

            tuesdayEatByText?.text = DateTimeUtils.increaseTimeByOne(eatBy)!!
            tuesdayDoseOneText?.text = DateTimeUtils.increaseTimeByOne(doseOne)!!
            tuesdayDoseTwoText?.text = DateTimeUtils.increaseTimeByOne(doseTwo)!!
            tuesdayWakeText?.text = DateTimeUtils.increaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(tuesdayEatByText?.text!!.toString(), eatByTimePickerTuesday!!)
            increaseTimeOfSpinnerByOne(tuesdayDoseOneText?.text!!.toString(), doseOneTimePickerTuesday!!)
            increaseTimeOfSpinnerByOne(tuesdayDoseTwoText?.text!!.toString(), doseTwoTimePickerTuesday!!)
            increaseTimeOfSpinnerByOne(tuesdayWakeText?.text!!.toString(), WakeTimePickerTuesday!!)
        }

        decreaseByOneTuesday?.setOnClickListener {
            var eatBy = tuesdayEatByText?.text.toString()
            var doseOne = tuesdayDoseOneText?.text.toString()
            var doseTwo = tuesdayDoseTwoText?.text.toString()
            var wake = tuesdayWakeText?.text.toString()

            tuesdayEatByText?.text = DateTimeUtils.decreaseTimeByOne(eatBy)!!
            tuesdayDoseOneText?.text = DateTimeUtils.decreaseTimeByOne(doseOne)!!
            tuesdayDoseTwoText?.text = DateTimeUtils.decreaseTimeByOne(doseTwo)!!
            tuesdayWakeText?.text = DateTimeUtils.decreaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(tuesdayEatByText?.text!!.toString(), eatByTimePickerTuesday!!)
            increaseTimeOfSpinnerByOne(tuesdayDoseOneText?.text!!.toString(), doseOneTimePickerTuesday!!)
            increaseTimeOfSpinnerByOne(tuesdayDoseTwoText?.text!!.toString(), doseTwoTimePickerTuesday!!)
            increaseTimeOfSpinnerByOne(tuesdayWakeText?.text!!.toString(), WakeTimePickerTuesday!!)
        }

        increaseByOneWednesday?.setOnClickListener {
            var eatBy = wednesdayEatByText?.text.toString()
            var doseOne = wednesdayDoseOneText?.text.toString()
            var doseTwo = wednesdayDoseTwoText?.text.toString()
            var wake = wednesdayWakeText?.text.toString()

            wednesdayEatByText?.text = DateTimeUtils.increaseTimeByOne(eatBy)!!
            wednesdayDoseOneText?.text = DateTimeUtils.increaseTimeByOne(doseOne)!!
            wednesdayDoseTwoText?.text = DateTimeUtils.increaseTimeByOne(doseTwo)!!
            wednesdayWakeText?.text = DateTimeUtils.increaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(wednesdayEatByText?.text!!.toString(), eatByTimePickerWednesday!!)
            increaseTimeOfSpinnerByOne(wednesdayDoseOneText?.text!!.toString(), doseOneTimePickerWednesday!!)
            increaseTimeOfSpinnerByOne(wednesdayDoseTwoText?.text!!.toString(), doseTwoTimePickerWednesday!!)
            increaseTimeOfSpinnerByOne(wednesdayWakeText?.text!!.toString(), wakeTimePickerWednesday!!)
        }

        decreaseByOneWednesday?.setOnClickListener {
            var eatBy = wednesdayEatByText?.text.toString()
            var doseOne = wednesdayDoseOneText?.text.toString()
            var doseTwo = wednesdayDoseTwoText?.text.toString()
            var wake = wednesdayWakeText?.text.toString()

            wednesdayEatByText?.text = DateTimeUtils.decreaseTimeByOne(eatBy)!!
            wednesdayDoseOneText?.text = DateTimeUtils.decreaseTimeByOne(doseOne)!!
            wednesdayDoseTwoText?.text = DateTimeUtils.decreaseTimeByOne(doseTwo)!!
            wednesdayWakeText?.text = DateTimeUtils.decreaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(wednesdayEatByText?.text!!.toString(), eatByTimePickerWednesday!!)
            increaseTimeOfSpinnerByOne(wednesdayDoseOneText?.text!!.toString(), doseOneTimePickerWednesday!!)
            increaseTimeOfSpinnerByOne(wednesdayDoseTwoText?.text!!.toString(), doseTwoTimePickerWednesday!!)
            increaseTimeOfSpinnerByOne(wednesdayWakeText?.text!!.toString(), wakeTimePickerWednesday!!)
        }

        increaseByOneThursday?.setOnClickListener {
            var eatBy = thursdayEatByText?.text.toString()
            var doseOne = thursdayDoseOneText?.text.toString()
            var doseTwo = thursdayDoseTwoText?.text.toString()
            var wake = thursdayWakeText?.text.toString()

            thursdayEatByText?.text = DateTimeUtils.increaseTimeByOne(eatBy)!!
            thursdayDoseOneText?.text = DateTimeUtils.increaseTimeByOne(doseOne)!!
            thursdayDoseTwoText?.text = DateTimeUtils.increaseTimeByOne(doseTwo)!!
            thursdayWakeText?.text = DateTimeUtils.increaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(thursdayEatByText?.text!!.toString(), eatByTimePickerThursday!!)
            increaseTimeOfSpinnerByOne(thursdayDoseOneText?.text!!.toString(), doseOneTimePickerThursday!!)
            increaseTimeOfSpinnerByOne(thursdayDoseTwoText?.text!!.toString(), doseTwoTimePickerThursday!!)
            increaseTimeOfSpinnerByOne(thursdayWakeText?.text!!.toString(), wakeTimePickerThursday!!)
        }

        decreaseByOneThursday?.setOnClickListener {
            var eatBy = thursdayEatByText?.text.toString()
            var doseOne = thursdayDoseOneText?.text.toString()
            var doseTwo = thursdayDoseTwoText?.text.toString()
            var wake = thursdayWakeText?.text.toString()

            thursdayEatByText?.text = DateTimeUtils.decreaseTimeByOne(eatBy)!!
            thursdayDoseOneText?.text = DateTimeUtils.decreaseTimeByOne(doseOne)!!
            thursdayDoseTwoText?.text = DateTimeUtils.decreaseTimeByOne(doseTwo)!!
            thursdayWakeText?.text = DateTimeUtils.decreaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(thursdayEatByText?.text!!.toString(), eatByTimePickerThursday!!)
            increaseTimeOfSpinnerByOne(thursdayDoseOneText?.text!!.toString(), doseOneTimePickerThursday!!)
            increaseTimeOfSpinnerByOne(thursdayDoseTwoText?.text!!.toString(), doseTwoTimePickerThursday!!)
            increaseTimeOfSpinnerByOne(thursdayWakeText?.text!!.toString(), wakeTimePickerThursday!!)
        }

        increaseByOneFriday?.setOnClickListener {
            var eatBy = fridayEatByText?.text.toString()
            var doseOne = fridayDoseOneText?.text.toString()
            var doseTwo = fridayDoseTwoText?.text.toString()
            var wake = fridayWakeText?.text.toString()

            fridayEatByText?.text = DateTimeUtils.increaseTimeByOne(eatBy)!!
            fridayDoseOneText?.text = DateTimeUtils.increaseTimeByOne(doseOne)!!
            fridayDoseTwoText?.text = DateTimeUtils.increaseTimeByOne(doseTwo)!!
            fridayWakeText?.text = DateTimeUtils.increaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(fridayEatByText?.text!!.toString(), eatByTimePickerFriday!!)
            increaseTimeOfSpinnerByOne(fridayDoseOneText?.text!!.toString(), doseOneTimePickerFriday!!)
            increaseTimeOfSpinnerByOne(fridayDoseTwoText?.text!!.toString(), doseTwoTimePickerFriday!!)
            increaseTimeOfSpinnerByOne(fridayWakeText?.text!!.toString(), wakeTimePickerFriday!!)
        }

        decreaseByOneFriday?.setOnClickListener {
            var eatBy = fridayEatByText?.text.toString()
            var doseOne = fridayDoseOneText?.text.toString()
            var doseTwo = fridayDoseTwoText?.text.toString()
            var wake = fridayWakeText?.text.toString()

            fridayEatByText?.text = DateTimeUtils.decreaseTimeByOne(eatBy)!!
            fridayDoseOneText?.text = DateTimeUtils.decreaseTimeByOne(doseOne)!!
            fridayDoseTwoText?.text = DateTimeUtils.decreaseTimeByOne(doseTwo)!!
            fridayWakeText?.text = DateTimeUtils.decreaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(fridayEatByText?.text!!.toString(), eatByTimePickerFriday!!)
            increaseTimeOfSpinnerByOne(fridayDoseOneText?.text!!.toString(), doseOneTimePickerFriday!!)
            increaseTimeOfSpinnerByOne(fridayDoseTwoText?.text!!.toString(), doseTwoTimePickerFriday!!)
            increaseTimeOfSpinnerByOne(fridayWakeText?.text!!.toString(), wakeTimePickerFriday!!)

        }

        increaseByOneSaturday?.setOnClickListener {
            var eatBy = saturdayEatByText?.text.toString()
            var doseOne = saturdayDoseOneText?.text.toString()
            var doseTwo = saturdayDoseTwoText?.text.toString()
            var wake = saturdayWakeText?.text.toString()

            saturdayEatByText?.text = DateTimeUtils.increaseTimeByOne(eatBy)!!
            saturdayDoseOneText?.text = DateTimeUtils.increaseTimeByOne(doseOne)!!
            saturdayDoseTwoText?.text = DateTimeUtils.increaseTimeByOne(doseTwo)!!
            saturdayWakeText?.text = DateTimeUtils.increaseTimeByOne(wake)

            increaseTimeOfSpinnerByOne(saturdayEatByText?.text!!.toString(), eatByTimePickerSaturday!!)
            increaseTimeOfSpinnerByOne(saturdayDoseOneText?.text!!.toString(), doseOneTimePickerSaturday!!)
            increaseTimeOfSpinnerByOne(saturdayDoseTwoText?.text!!.toString(), doseTwoTimePickerSaturday!!)
            increaseTimeOfSpinnerByOne(saturdayWakeText?.text!!.toString(), wakeTimePickerSaturday!!)
        }

        decreaseByOneSaturday?.setOnClickListener {
            var eatBy = saturdayEatByText?.text.toString()
            var doseOne = saturdayDoseOneText?.text.toString()
            var doseTwo = saturdayDoseTwoText?.text.toString()
            var wake = saturdayWakeText?.text.toString()

            saturdayEatByText?.text = DateTimeUtils.decreaseTimeByOne(eatBy)!!
            saturdayDoseOneText?.text = DateTimeUtils.decreaseTimeByOne(doseOne)!!
            saturdayDoseTwoText?.text = DateTimeUtils.decreaseTimeByOne(doseTwo)!!
            saturdayWakeText?.text = DateTimeUtils.decreaseTimeByOne(wake)


            increaseTimeOfSpinnerByOne(saturdayEatByText?.text!!.toString(), eatByTimePickerSaturday!!)
            increaseTimeOfSpinnerByOne(saturdayDoseOneText?.text!!.toString(), doseOneTimePickerSaturday!!)
            increaseTimeOfSpinnerByOne(saturdayDoseTwoText?.text!!.toString(), doseTwoTimePickerSaturday!!)
            increaseTimeOfSpinnerByOne(saturdayWakeText?.text!!.toString(), wakeTimePickerSaturday!!)
        }
    }

    private fun saveData() {
        try {
            if (!isPlanSetup!!) {
                val am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                var nm = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                for (i in 101..516) {
                    var intent = Intent(activity, MainActivity::class.java)
                    var pendingIntent = PendingIntent.getBroadcast(activity, i, intent, 0)
                    am.cancel(pendingIntent)
                }

                for (i in 11..66) {
                    nm.cancel(i)
                }

            }


            var planSunday = Plan(id = 1, eatBy = sundayEatByText?.text.toString()!!, doseOne = sundayDoseOneText?.text.toString()!!,
                    doseTwo = sundayDoseTwoText?.text.toString()!!, wakeUp = sundayWakeText?.text.toString(), reminderWeekNo = 1)
            mPlanRepository!!.updatePlan(planSunday)

            var planMonday = Plan(id = 2, eatBy = mondayEatByText?.text.toString()!!, doseOne = mondayDoseOneText?.text.toString()!!,
                    doseTwo = mondayDoseTwoText?.text.toString()!!, wakeUp = mondayWakeText?.text.toString(), reminderWeekNo = 2)
            mPlanRepository!!.updatePlan(planMonday)

            var planTuesday = Plan(id = 3, eatBy = tuesdayEatByText?.text.toString()!!, doseOne = tuesdayDoseOneText?.text.toString()!!,
                    doseTwo = tuesdayDoseTwoText?.text.toString()!!, wakeUp = tuesdayWakeText?.text.toString(), reminderWeekNo = 3)
            mPlanRepository!!.updatePlan(planTuesday)

            var planWednesday = Plan(id = 4, eatBy = wednesdayEatByText?.text.toString()!!, doseOne = wednesdayDoseOneText?.text.toString()!!,
                    doseTwo = wednesdayDoseTwoText?.text.toString()!!, wakeUp = wednesdayWakeText?.text.toString(), reminderWeekNo = 4)
            mPlanRepository!!.updatePlan(planWednesday)

            var planThursday = Plan(id = 5, eatBy = thursdayEatByText?.text.toString()!!, doseOne = thursdayDoseOneText?.text.toString()!!,
                    doseTwo = thursdayDoseTwoText?.text.toString()!!, wakeUp = thursdayWakeText?.text.toString(), reminderWeekNo = 5)
            mPlanRepository!!.updatePlan(planThursday)

            var planFriday = Plan(id = 6, eatBy = fridayEatByText?.text.toString()!!, doseOne = fridayDoseOneText?.text.toString()!!,
                    doseTwo = fridayDoseTwoText?.text.toString()!!, wakeUp = fridayWakeText?.text.toString(), reminderWeekNo = 6)
            mPlanRepository!!.updatePlan(planFriday)

            var planSaturday = Plan(id = 7, eatBy = saturdayEatByText?.text.toString()!!, doseOne = saturdayDoseOneText?.text.toString()!!,
                    doseTwo = saturdayDoseTwoText?.text.toString()!!, wakeUp = saturdayWakeText?.text.toString(), reminderWeekNo = 7)
            mPlanRepository!!.updatePlan(planSaturday)


            var pendintIntentSundayWakeUp = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.SUNDAY_WAKE_REQUEST_CODE)
                intent.putExtra("time", planSunday.wakeUp)
                PendingIntent.getBroadcast(context, Constants.SUNDAY_WAKE_REQUEST_CODE, intent, 0)
            }
            var sundayWakeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planSunday.wakeUp, activity, 1, Constants.SUNDAY_WAKE_NOTIFICATION_ID, pendintIntentSundayWakeUp, sundayWakeAlarmManager!!)

            var pendingIntentSundayEatBy = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.SUNDAY_EATBY_REQUEST_CODE)
                intent.putExtra("time", planSunday.eatBy)
                PendingIntent.getBroadcast(context, Constants.SUNDAY_EATBY_REQUEST_CODE, intent, 0)
            }
            var sundayEatByAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planSunday.eatBy, activity, 1, Constants.SUNDAY_EATBY_NOTIFICATION_ID, pendingIntentSundayEatBy, sundayEatByAlarmManager!!)

            var pendingIntentDoseOne = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.SUNDAY_DOSE1_REQUEST_CODE)
                intent.putExtra("time", planSunday.doseOne)
                PendingIntent.getBroadcast(context, Constants.SUNDAY_DOSE1_REQUEST_CODE, intent, 0)
            }
            var sundayDose1AlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planSunday.doseOne, activity, 1, Constants.SUNDAY_DOSE1_NOTIFICATION_ID, pendingIntentDoseOne, sundayDose1AlarmManager!!)

            var pendingIntentDoseTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.SUNDAY_DOSE2_REQUEST_CODE)
                intent.putExtra("time", planSunday.doseTwo)
                PendingIntent.getBroadcast(context, Constants.SUNDAY_DOSE2_REQUEST_CODE, intent, 0)
            }
            var sundayDose2AlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planSunday.doseTwo, activity, 1, Constants.SUNDAY_DOSE2_NOTIFICATION_ID, pendingIntentDoseTwo, sundayDose2AlarmManager!!)

            var nap = NapRepository(activity).getNapById(1)
            if (nap != null) {
                if (nap.napOne != null && !TextUtils.isEmpty(nap.napOne) && !nap.napOne.equals("-1")) {
                    var pendingIntentNapOne = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.SUNDAY_NAP1_REQUEST_CODE)
                        intent.putExtra("time", nap.napOne)
                        PendingIntent.getBroadcast(context, Constants.SUNDAY_NAP1_REQUEST_CODE, intent, 0)
                    }
                    var sundayNapOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap.napOne, activity, 1, Constants.SUNDAY_NAP1_NOTIFICATION_ID, pendingIntentNapOne, sundayNapOneAlarmManager!!)

                }

                if (nap.napTwo != null && !TextUtils.isEmpty(nap.napTwo) && !nap.napTwo.equals("-1")) {
                    var pendingIntentNapTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.SUNDAY_NAP2_REQUEST_CODE)
                        intent.putExtra("time", nap.napTwo)
                        PendingIntent.getBroadcast(context, Constants.SUNDAY_NAP2_REQUEST_CODE, intent, 0)
                    }
                    var sundayNapTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap.napTwo, activity, 1, Constants.SUNDAY_NAP2_NOTIFICATION_ID, pendingIntentNapTwo, sundayNapTwoAlarmManager!!)

                }

                if (nap.napThree != null && !TextUtils.isEmpty(nap.napThree) && !nap.napThree.equals("-1")) {
                    var pendingIntentNapThree = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.SUNDAY_NAP3_REQUEST_CODE)
                        intent.putExtra("time", nap.napThree)
                        PendingIntent.getBroadcast(context, Constants.SUNDAY_NAP3_REQUEST_CODE, intent, 0)
                    }
                    var sundayNapThreeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap.napThree, activity, 1, Constants.SUNDAY_NAP3_NOTIFICATION_ID, pendingIntentNapThree, sundayNapThreeAlarmManager!!)

                }

            }

            //sunday eatby one hours before
            var mReminderRepository = ReminderRepository(activity!!).getReminderById(1)
            if (mReminderRepository.reminderEatBy) {
                var pendingIntent = Intent(context, PlanReceiver::class.java).let { intent ->
                    PendingIntent.getBroadcast(context, Constants.SUNDAY_EATBY_ONE_HOUR_REQUEST_CODE, intent, 0)
                }
                var alarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                NotificationUtils.setNotificationForEatBy1HourBefore(planSunday.eatBy, activity, 1, Constants.SUNDAY_EATBY_ONE_HOUR_NOTIFICATION_ID, pendingIntent, alarmManager!!)
            }


            var pendingIntentMondayWakeUp = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.MONDAY_WAKE_REQUEST_CODE)
                intent.putExtra("time", planMonday.wakeUp)
                PendingIntent.getBroadcast(context, Constants.MONDAY_WAKE_REQUEST_CODE, intent, 0)
            }
            var mondayWakeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planMonday.wakeUp, activity, 2, Constants.MONDAY_WAKE_NOTIFICATION_ID, pendingIntentMondayWakeUp, mondayWakeAlarmManager!!)

            var pendingIntentMondayEatBy = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.MONDAY_EATBY_REQUEST_CODE)
                intent.putExtra("time", planMonday.eatBy)
                PendingIntent.getBroadcast(context, Constants.MONDAY_EATBY_REQUEST_CODE, intent, 0)
            }
            var mondayEatAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planMonday.eatBy, activity, 2, Constants.MONDAY_EATBY_NOTIFICATION_ID, pendingIntentMondayEatBy, mondayEatAlarmManager!!)

            var pendingIntentMondayDoseOne = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.MONDAY_DOSE1_REQUEST_CODE)
                intent.putExtra("time", planMonday.doseOne)
                PendingIntent.getBroadcast(context, Constants.MONDAY_DOSE1_REQUEST_CODE, intent, 0)
            }
            var mondayDoseOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planMonday.doseOne, activity, 2, Constants.MONDAY_DOSE1_NOTIFICATION_ID, pendingIntentMondayDoseOne, mondayDoseOneAlarmManager!!)


            var pendingIntentMondayDoseTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.MONDAY_DOSE2_REQUEST_CODE)
                intent.putExtra("time", planMonday.doseTwo)
                PendingIntent.getBroadcast(context, Constants.MONDAY_DOSE2_REQUEST_CODE, intent, 0)
            }
            var mondayDoseTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planMonday.doseTwo, activity, 2, Constants.MONDAY_DOSE2_NOTIFICATION_ID, pendingIntentMondayDoseTwo, mondayDoseTwoAlarmManager!!)

            var nap2 = NapRepository(activity).getNapById(2)
            if (nap2 != null) {
                if (nap2.napOne != null && !TextUtils.isEmpty(nap2.napOne) && !nap2.napOne.equals("-1")) {
                    var pendingIntentMondayNapOne = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.MONDAY_NAP1_REQUEST_CODE)
                        intent.putExtra("time", nap2.napOne)
                        PendingIntent.getBroadcast(context, Constants.MONDAY_NAP1_REQUEST_CODE, intent, 0)
                    }
                    var mondayNapOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap2.napOne, activity, 2, Constants.MONDAY_NAP1_NOTIFICATION_ID, pendingIntentMondayNapOne, mondayNapOneAlarmManager!!)

                }

                if (nap2.napTwo != null && !TextUtils.isEmpty(nap2.napTwo) && !nap2.napTwo.equals("-1")) {
                    var pendingIntentMondayNapTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.MONDAY_NAP2_REQUEST_CODE)
                        intent.putExtra("time", nap2.napTwo)
                        PendingIntent.getBroadcast(context, Constants.MONDAY_NAP2_REQUEST_CODE, intent, 0)
                    }
                    var mondayNapTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap2.napTwo, activity, 2, Constants.MONDAY_NAP2_NOTIFICATION_ID, pendingIntentMondayNapTwo, mondayNapTwoAlarmManager!!)

                }

                if (nap2.napThree != null && !TextUtils.isEmpty(nap2.napThree) && !nap2.napThree.equals("-1")) {
                    var pendingIntentMondayNapThree = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.MONDAY_NAP3_REQUEST_CODE)
                        intent.putExtra("time", nap2.napThree)
                        PendingIntent.getBroadcast(context, Constants.MONDAY_NAP3_REQUEST_CODE, intent, 0)
                    }
                    var mondayNapThreeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap2.napThree, activity, 2, Constants.MONDAY_NAP3_NOTIFICATION_ID, pendingIntentMondayNapThree, mondayNapThreeAlarmManager!!)

                }

            }
            //monday eatby one hours before
            mReminderRepository = ReminderRepository(activity!!).getReminderById(2)
            if (mReminderRepository.reminderEatBy) {
                var pendingIntent = Intent(context, PlanReceiver::class.java).let { intent ->
                    PendingIntent.getBroadcast(context, Constants.MONDAY_EATBY_ONE_HOUR_REQUEST_CODE, intent, 0)
                }
                var alarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                NotificationUtils.setNotificationForEatBy1HourBefore(planMonday.eatBy, activity, 2, Constants.MONDAY_EATBY_ONE_HOUR_NOTIFICATION_ID, pendingIntent, alarmManager!!)
            }


            var pendingIntentTuesdayWakeUp = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.TUESDAY_WAKE_REQUEST_CODE)
                intent.putExtra("time", planTuesday.wakeUp)
                PendingIntent.getBroadcast(context, Constants.TUESDAY_WAKE_REQUEST_CODE, intent, 0)
            }
            var tuesdayWakeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planTuesday.wakeUp, activity, 3, Constants.TUESDAY_WAKE_NOTIFICATION_ID, pendingIntentTuesdayWakeUp, tuesdayWakeAlarmManager!!)

            var pendingIntentTuesdayEatBy = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.TUESDAY_EATBY_REQUEST_CODE)
                intent.putExtra("time", planTuesday.eatBy)
                PendingIntent.getBroadcast(context, Constants.TUESDAY_EATBY_REQUEST_CODE, intent, 0)
            }
            var tuesdayEatAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planTuesday.eatBy, activity, 3, Constants.TUESDAY_EATBY_NOTIFICATION_ID, pendingIntentTuesdayEatBy, tuesdayEatAlarmManager!!)

            var pendingIntentTuesdayDoseOne = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.TUESDAY_DOSE1_REQUEST_CODE)
                intent.putExtra("time", planTuesday.doseOne)
                PendingIntent.getBroadcast(context, Constants.TUESDAY_DOSE1_REQUEST_CODE, intent, 0)
            }
            var tuesdayDoseOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planTuesday.doseOne, activity, 3, Constants.TUESDAY_DOSE1_NOTIFICATION_ID, pendingIntentTuesdayDoseOne, tuesdayDoseOneAlarmManager!!)

            var pendingIntentTuesdayDoseTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.TUESDAY_DOSE2_REQUEST_CODE)
                intent.putExtra("time", planTuesday.doseTwo)
                PendingIntent.getBroadcast(context, Constants.TUESDAY_DOSE2_REQUEST_CODE, intent, 0)
            }
            var tuesdayDoseTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planTuesday.doseTwo, activity, 3, Constants.TUESDAY_DOSE2_NOTIFICATION_ID, pendingIntentTuesdayDoseTwo, tuesdayDoseTwoAlarmManager!!)

            var nap3 = NapRepository(activity).getNapById(3)
            if (nap3 != null) {
                if (nap3.napOne != null && !TextUtils.isEmpty(nap3.napOne) && !nap3.napOne.equals("-1")) {
                    var pendingIntentTuesdayNapOne = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.TUESDAY_NAP1_REQUEST_CODE)
                        intent.putExtra("time", nap3.napOne)
                        PendingIntent.getBroadcast(context, Constants.TUESDAY_NAP1_REQUEST_CODE, intent, 0)
                    }
                    var tuesdayNapOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap3.napOne, activity, 3, Constants.TUESDAY_NAP1_NOTIFICATION_ID, pendingIntentTuesdayNapOne, tuesdayNapOneAlarmManager!!)

                }

                if (nap3.napTwo != null && !TextUtils.isEmpty(nap3.napTwo) && !nap3.napTwo.equals("-1")) {
                    var pendingIntentTuesdayNapTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.TUESDAY_NAP2_REQUEST_CODE)
                        intent.putExtra("time", nap3.napTwo)
                        PendingIntent.getBroadcast(context, Constants.TUESDAY_NAP2_REQUEST_CODE, intent, 0)
                    }
                    var tuesdayNapTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap3.napTwo, activity, 3, Constants.TUESDAY_NAP2_NOTIFICATION_ID, pendingIntentTuesdayNapTwo, tuesdayNapTwoAlarmManager!!)

                }

                if (nap3.napThree != null && !TextUtils.isEmpty(nap3.napThree) && !nap3.napThree.equals("-1")) {
                    var pendingIntentTuesdayNapThree = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.TUESDAY_NAP3_REQUEST_CODE)
                        intent.putExtra("time", nap3.napThree)
                        PendingIntent.getBroadcast(context, Constants.TUESDAY_NAP3_REQUEST_CODE, intent, 0)
                    }
                    var tuesdayNapThreeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap3.napThree, activity, 3, Constants.TUESDAY_NAP3_NOTIFICATION_ID, pendingIntentTuesdayNapThree, tuesdayNapThreeAlarmManager!!)

                }

            }

            //tuesday eatby one hours before
            mReminderRepository = ReminderRepository(activity!!).getReminderById(3)
            if (mReminderRepository.reminderEatBy) {
                var pendingIntent = Intent(context, PlanReceiver::class.java).let { intent ->
                    PendingIntent.getBroadcast(context, Constants.TUESDAY_EATBY_ONE_HOUR_REQUEST_CODE, intent, 0)
                }
                var alarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                NotificationUtils.setNotificationForEatBy1HourBefore(planTuesday.eatBy, activity, 3, Constants.TUESDAY_EATBY_ONE_HOUR_NOTIFICATION_ID, pendingIntent, alarmManager!!)
            }


            var pendingIntentWednesdayWakeUp = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.WEDNESDAY_WAKE_REQUEST_CODE)
                intent.putExtra("time", planWednesday.wakeUp)
                PendingIntent.getBroadcast(context, Constants.WEDNESDAY_WAKE_REQUEST_CODE, intent, 0)
            }
            var wednesDayWakeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planWednesday.wakeUp, activity, 4, Constants.WEDNESDAY_WAKE_NOTIFICATION_ID, pendingIntentWednesdayWakeUp, wednesDayWakeAlarmManager!!)

            var pendingIntentWednesdayEatBy = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.WEDNESDAY_EATBY_REQUEST_CODE)
                intent.putExtra("time", planWednesday.wakeUp)
                PendingIntent.getBroadcast(context, Constants.WEDNESDAY_EATBY_REQUEST_CODE, intent, 0)
            }
            var wednesDayEatAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planWednesday.eatBy, activity, 4, Constants.WEDNESDAY_EATBY_NOTIFICATION_ID, pendingIntentWednesdayEatBy, wednesDayEatAlarmManager!!)

            var pendingIntentWednesdayDoseOne = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.WEDNESDAY_DOSE1_REQUEST_CODE)
                intent.putExtra("time", planWednesday.doseOne)
                PendingIntent.getBroadcast(context, Constants.WEDNESDAY_DOSE1_REQUEST_CODE, intent, 0)
            }
            var wednesDayDoseOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planWednesday.doseOne, activity, 4, Constants.WEDNESDAY_DOSE1_NOTIFICATION_ID, pendingIntentWednesdayDoseOne, wednesDayDoseOneAlarmManager!!)

            var pendingIntentWednesdayDoseTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.WEDNESDAY_DOSE2_REQUEST_CODE)
                intent.putExtra("time", planWednesday.doseTwo)
                PendingIntent.getBroadcast(context, Constants.WEDNESDAY_DOSE2_REQUEST_CODE, intent, 0)
            }
            var wednesDayDoseTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planWednesday.doseTwo, activity, 4, Constants.WEDNESDAY_DOSE2_NOTIFICATION_ID, pendingIntentWednesdayDoseTwo, wednesDayDoseTwoAlarmManager!!)
            var nap4 = NapRepository(activity).getNapById(4)
            if (nap4 != null) {
                if (nap4.napOne != null && !TextUtils.isEmpty(nap4.napOne) && !nap4.napOne.equals("-1")) {
                    var pendingIntentWednesdayNapOne = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.WEDNESDAY_NAP1_REQUEST_CODE)
                        intent.putExtra("time", nap4.napOne)
                        PendingIntent.getBroadcast(context, Constants.WEDNESDAY_NAP1_REQUEST_CODE, intent, 0)
                    }
                    var wednesDayNapOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap4.napOne, activity, 4, Constants.WEDNESDAY_NAP1_NOTIFICATION_ID, pendingIntentWednesdayNapOne, wednesDayNapOneAlarmManager!!)

                }

                if (nap4.napTwo != null && !TextUtils.isEmpty(nap4.napTwo) && !nap4.napTwo.equals("-1")) {
                    var pendingIntentWednesdayNapTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.WEDNESDAY_NAP2_REQUEST_CODE)
                        intent.putExtra("time", nap4.napTwo)
                        PendingIntent.getBroadcast(context, Constants.WEDNESDAY_NAP2_REQUEST_CODE, intent, 0)
                    }
                    var wednesDayNapTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap4.napTwo, activity, 4, Constants.WEDNESDAY_NAP2_NOTIFICATION_ID, pendingIntentWednesdayNapTwo, wednesDayNapTwoAlarmManager!!)

                }

                if (nap4.napThree != null && !TextUtils.isEmpty(nap4.napThree) && !nap4.napThree.equals("-1")) {
                    var pendingIntentWednesdayNapThree = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.WEDNESDAY_NAP3_REQUEST_CODE)
                        intent.putExtra("time", nap4.napThree)
                        PendingIntent.getBroadcast(context, Constants.WEDNESDAY_NAP3_REQUEST_CODE, intent, 0)
                    }
                    var wednesDayNapThreeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap4.napThree, activity, 4, Constants.WEDNESDAY_NAP3_NOTIFICATION_ID, pendingIntentWednesdayNapThree, wednesDayNapThreeAlarmManager!!)

                }

            }
            //wednesday eatby one hours before
            mReminderRepository = ReminderRepository(activity!!).getReminderById(4)
            if (mReminderRepository.reminderEatBy) {
                var pendingIntent = Intent(context, PlanReceiver::class.java).let { intent ->
                    PendingIntent.getBroadcast(context, Constants.WEDNESDAY_EATBY_ONE_HOUR_REQUEST_CODE, intent, 0)
                }
                var alarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                NotificationUtils.setNotificationForEatBy1HourBefore(planWednesday.eatBy, activity, 4, Constants.WEDNESDAY_EATBY_ONE_HOUR_NOTIFICATION_ID, pendingIntent, alarmManager!!)
            }


            var pendingIntentThursdayWakeUp = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.THURSDAY_WAKE_REQUEST_CODE)
                intent.putExtra("time", planThursday.wakeUp)
                PendingIntent.getBroadcast(context, Constants.THURSDAY_WAKE_REQUEST_CODE, intent, 0)
            }
            var thursdayWakeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planThursday.wakeUp, activity, 5, Constants.THURSDAY_WAKE_NOTIFICATION_ID, pendingIntentThursdayWakeUp, thursdayWakeAlarmManager!!)

            var pendingIntentThursdayEatBy = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.THURSDAY_EATBY_REQUEST_CODE)
                intent.putExtra("time", planThursday.eatBy)
                PendingIntent.getBroadcast(context, Constants.THURSDAY_EATBY_REQUEST_CODE, intent, 0)
            }
            var thursdayEatAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planThursday.eatBy, activity, 5, Constants.THURSDAY_EATBY_NOTIFICATION_ID, pendingIntentThursdayEatBy, thursdayEatAlarmManager!!)

            var pendingIntentThursdayDoseOne = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.THURSDAY_DOSE1_REQUEST_CODE)
                intent.putExtra("time", planThursday.doseOne)
                PendingIntent.getBroadcast(context, Constants.THURSDAY_DOSE1_REQUEST_CODE, intent, 0)
            }
            var thursdayDoseOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planThursday.doseOne, activity, 5, Constants.THURSDAY_DOSE1_NOTIFICATION_ID, pendingIntentThursdayDoseOne, thursdayDoseOneAlarmManager!!)

            var pendingIntentThursdayDoseTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.THURSDAY_DOSE2_REQUEST_CODE)
                intent.putExtra("time", planThursday.doseTwo)
                PendingIntent.getBroadcast(context, Constants.THURSDAY_DOSE2_REQUEST_CODE, intent, 0)
            }
            var thursdayDoseTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planThursday.doseTwo, activity, 5, Constants.THURSDAY_DOSE2_NOTIFICATION_ID, pendingIntentThursdayDoseTwo, thursdayDoseTwoAlarmManager!!)

            var nap5 = NapRepository(activity).getNapById(5)
            if (nap5 != null) {
                if (nap5.napOne != null && !TextUtils.isEmpty(nap5.napOne) && !nap5.napOne.equals("-1")) {
                    var pendingIntentThursdayNapOne = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.THURSDAY_NAP1_REQUEST_CODE)
                        intent.putExtra("time", nap5.napOne)
                        PendingIntent.getBroadcast(context, Constants.THURSDAY_NAP1_REQUEST_CODE, intent, 0)
                    }
                    var thursdayNapOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap5.napOne, activity, 5, Constants.THURSDAY_NAP1_NOTIFICATION_ID, pendingIntentThursdayNapOne, thursdayNapOneAlarmManager!!)

                }

                if (nap5.napTwo != null && !TextUtils.isEmpty(nap5.napTwo) && !nap5.napTwo.equals("-1")) {
                    var pendingIntentThursdayNapTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.THURSDAY_NAP2_REQUEST_CODE)
                        intent.putExtra("time", nap5.napTwo)
                        PendingIntent.getBroadcast(context, Constants.THURSDAY_NAP2_REQUEST_CODE, intent, 0)
                    }
                    var thursdayNapTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap5.napTwo, activity, 5, Constants.THURSDAY_NAP2_NOTIFICATION_ID, pendingIntentThursdayNapTwo, thursdayNapTwoAlarmManager!!)

                }

                if (nap5.napThree != null && !TextUtils.isEmpty(nap5.napThree) && !nap5.napThree.equals("-1")) {
                    var pendingIntentThursdayNapThree = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.THURSDAY_NAP3_REQUEST_CODE)
                        intent.putExtra("time", nap5.napThree)
                        PendingIntent.getBroadcast(context, Constants.THURSDAY_NAP3_REQUEST_CODE, intent, 0)
                    }
                    var thursdayNapThreeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap5.napThree, activity, 5, Constants.THURSDAY_NAP3_NOTIFICATION_ID, pendingIntentThursdayNapThree, thursdayNapThreeAlarmManager!!)

                }

            }

            //thursday eatby one hours before
            mReminderRepository = ReminderRepository(activity!!).getReminderById(5)
            if (mReminderRepository.reminderEatBy) {
                var pendingIntent = Intent(context, PlanReceiver::class.java).let { intent ->
                    PendingIntent.getBroadcast(context, Constants.THURSDAY_EATBY_ONE_HOUR_REQUEST_CODE, intent, 0)
                }
                var alarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                NotificationUtils.setNotificationForEatBy1HourBefore(planThursday.eatBy, activity, 5, Constants.THURSDAY_EATBY_ONE_HOUR_NOTIFICATION_ID, pendingIntent, alarmManager!!)
            }


            var pendingIntentFridayWakeUp = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.FRIDAY_WAKE_REQUEST_CODE)
                intent.putExtra("time", planFriday.wakeUp)
                PendingIntent.getBroadcast(context, Constants.FRIDAY_WAKE_REQUEST_CODE, intent, 0)
            }
            var fridayWakeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planFriday.wakeUp, activity, 6, Constants.FRIDAY_WAKE_NOTIFICATION_ID, pendingIntentFridayWakeUp, fridayWakeAlarmManager!!)

            var pendingIntentFridayEatBy = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.FRIDAY_EATBY_REQUEST_CODE)
                intent.putExtra("time", planFriday.eatBy)
                PendingIntent.getBroadcast(context, Constants.FRIDAY_EATBY_REQUEST_CODE, intent, 0)
            }
            var fridayEatAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planFriday.eatBy, activity, 6, Constants.FRIDAY_EATBY_NOTIFICATION_ID, pendingIntentFridayEatBy, fridayEatAlarmManager!!)

            var pendingIntentFridayDoseOne = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.FRIDAY_DOSE1_REQUEST_CODE)
                intent.putExtra("time", planFriday.doseOne)
                PendingIntent.getBroadcast(context, Constants.FRIDAY_DOSE1_REQUEST_CODE, intent, 0)
            }
            var fridayDoseOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planFriday.doseOne, activity, 6, Constants.FRIDAY_DOSE1_NOTIFICATION_ID, pendingIntentFridayDoseOne, fridayDoseOneAlarmManager!!)

            var pendingIntentFridayDoseTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.FRIDAY_DOSE2_REQUEST_CODE)
                intent.putExtra("time", planFriday.doseTwo)
                PendingIntent.getBroadcast(context, Constants.FRIDAY_DOSE2_REQUEST_CODE, intent, 0)
            }
            var fridayDoseTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planFriday.doseTwo, activity, 6, Constants.FRIDAY_DOSE2_NOTIFICATION_ID, pendingIntentFridayDoseTwo, fridayDoseTwoAlarmManager!!)

            var nap6 = NapRepository(activity).getNapById(6)
            if (nap6 != null) {
                if (nap6.napOne != null && !TextUtils.isEmpty(nap6.napOne) && !nap6.napOne.equals("-1")) {
                    var pendingIntentFridayNapOne = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.FRIDAY_NAP1_REQUEST_CODE)
                        intent.putExtra("time", nap6.napOne)
                        PendingIntent.getBroadcast(context, Constants.FRIDAY_NAP1_REQUEST_CODE, intent, 0)
                    }
                    var fridayNapOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap6.napOne, activity, 6, Constants.FRIDAY_NAP1_NOTIFICATION_ID, pendingIntentFridayNapOne, fridayNapOneAlarmManager!!)

                }

                if (nap6.napTwo != null && !TextUtils.isEmpty(nap6.napTwo) && !nap6.napTwo.equals("-1")) {
                    var pendingIntentFridayNapTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.FRIDAY_NAP2_REQUEST_CODE)
                        intent.putExtra("time", nap6.napTwo)
                        PendingIntent.getBroadcast(context, Constants.FRIDAY_NAP2_REQUEST_CODE, intent, 0)
                    }
                    var fridayNapTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap6.napTwo, activity, 6, Constants.FRIDAY_NAP2_NOTIFICATION_ID, pendingIntentFridayNapTwo, fridayNapTwoAlarmManager!!)

                }

                if (nap6.napThree != null && !TextUtils.isEmpty(nap6.napThree) && !nap6.napThree.equals("-1")) {
                    var pendingIntentFridayNapThree = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.FRIDAY_NAP3_REQUEST_CODE)
                        intent.putExtra("time", nap6.napThree)
                        PendingIntent.getBroadcast(context, Constants.FRIDAY_NAP3_REQUEST_CODE, intent, 0)
                    }
                    var fridayNapThreeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap6.napThree, activity, 6, Constants.FRIDAY_NAP3_NOTIFICATION_ID, pendingIntentFridayNapThree, fridayNapThreeAlarmManager!!)

                }

            }

            //friday eatby one hours before
            mReminderRepository = ReminderRepository(activity!!).getReminderById(6)
            if (mReminderRepository.reminderEatBy) {
                var pendingIntent = Intent(context, PlanReceiver::class.java).let { intent ->
                    PendingIntent.getBroadcast(context, Constants.FRIDAY_EATBY_ONE_HOUR_REQUEST_CODE, intent, 0)
                }
                var alarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                NotificationUtils.setNotificationForEatBy1HourBefore(planFriday.eatBy, activity, 6, Constants.FRIDAY_EATBY_ONE_HOUR_NOTIFICATION_ID, pendingIntent, alarmManager!!)
            }


            var pendingIntentSaturdayWakeUp = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.SATURDAY_WAKE_REQUEST_CODE)
                intent.putExtra("time", planSaturday.wakeUp)
                PendingIntent.getBroadcast(context, Constants.SATURDAY_WAKE_REQUEST_CODE, intent, 0)
            }
            var saturdayWakeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planSaturday.wakeUp, activity, 7, Constants.SATURDAY_WAKE_NOTIFICATION_ID, pendingIntentSaturdayWakeUp, saturdayWakeAlarmManager!!)

            var pendingIntentSaturdayEatBy = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.SATURDAY_EATBY_REQUEST_CODE)
                intent.putExtra("time", planSaturday.eatBy)
                PendingIntent.getBroadcast(context, Constants.SATURDAY_EATBY_REQUEST_CODE, intent, 0)
            }
            var saturdayEatAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planSaturday.eatBy, activity, 7, Constants.SATURDAY_EATBY_NOTIFICATION_ID, pendingIntentSaturdayEatBy, saturdayEatAlarmManager!!)

            var pendingIntentSaturdayDoseOne = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.SATURDAY_DOSE1_REQUEST_CODE)
                intent.putExtra("time", planSaturday.doseOne)
                PendingIntent.getBroadcast(context, Constants.SATURDAY_DOSE1_REQUEST_CODE, intent, 0)
            }
            var saturdayDoseOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForPlan(planSaturday.doseOne, activity, 7, Constants.SATURDAY_DOSE1_NOTIFICATION_ID, pendingIntentSaturdayDoseOne, saturdayDoseOneAlarmManager!!)

            var pendingIntentSaturdayDoseTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", Constants.SATURDAY_DOSE2_REQUEST_CODE)
                intent.putExtra("time", planSaturday.doseTwo)
                PendingIntent.getBroadcast(context, Constants.SATURDAY_DOSE2_REQUEST_CODE, intent, 0)
            }
            var saturdayDosetwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

            NotificationUtils.setNotificationForPlan(planSaturday.doseTwo, activity, 7, Constants.SATURDAY_DOSE2_NOTIFICATION_ID, pendingIntentSaturdayDoseTwo, saturdayDosetwoAlarmManager!!)
            var nap7 = NapRepository(activity).getNapById(7)
            if (nap7 != null) {
                if (nap7.napOne != null && !TextUtils.isEmpty(nap7.napOne) && !nap7.napOne.equals("-1")) {
                    var pendingIntentSaturdayNapOne = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.SATURDAY_NAP1_REQUEST_CODE)
                        intent.putExtra("time", nap7.napOne)
                        PendingIntent.getBroadcast(context, Constants.SATURDAY_NAP1_REQUEST_CODE, intent, 0)
                    }
                    var saturdayNapOneAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap7.napOne, activity, 7, Constants.SATURDAY_NAP1_NOTIFICATION_ID, pendingIntentSaturdayNapOne, saturdayNapOneAlarmManager!!)

                }

                if (nap7.napTwo != null && !TextUtils.isEmpty(nap7.napTwo) && !nap7.napTwo.equals("-1")) {
                    var pendingIntentSaturdayNapTwo = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.SATURDAY_NAP2_REQUEST_CODE)
                        intent.putExtra("time", nap7.napTwo)
                        PendingIntent.getBroadcast(context, Constants.SATURDAY_NAP2_REQUEST_CODE, intent, 0)
                    }
                    var saturdayNapTwoAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap7.napTwo, activity, 7, Constants.SATURDAY_NAP2_NOTIFICATION_ID, pendingIntentSaturdayNapTwo, saturdayNapTwoAlarmManager!!)

                }

                if (nap7.napThree != null && !TextUtils.isEmpty(nap7.napThree) && !nap7.napThree.equals("-1")) {
                    var pendingIntentSaturdayNapThree = Intent(context, PlanReceiver::class.java).let { intent ->
                        intent.putExtra("requestCode", Constants.SATURDAY_NAP3_REQUEST_CODE)
                        intent.putExtra("time", nap7.napThree)
                        PendingIntent.getBroadcast(context, Constants.SATURDAY_NAP3_REQUEST_CODE, intent, 0)
                    }
                    var saturdayNapThreeAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                    NotificationUtils.setNotificationForPlan(nap7.napThree, activity, 7, Constants.SATURDAY_NAP3_NOTIFICATION_ID, pendingIntentSaturdayNapThree, saturdayNapThreeAlarmManager!!)

                }

            }

            //friday eatby one hours before
            mReminderRepository = ReminderRepository(activity!!).getReminderById(7)
            if (mReminderRepository.reminderEatBy) {
                var pendingIntent = Intent(context, PlanReceiver::class.java).let { intent ->
                    PendingIntent.getBroadcast(context, Constants.SATURDAY_EATBY_ONE_HOUR_REQUEST_CODE, intent, 0)
                }
                var alarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
                NotificationUtils.setNotificationForEatBy1HourBefore(planSaturday.eatBy, activity, 7, Constants.SATURDAY_EATBY_ONE_HOUR_NOTIFICATION_ID, pendingIntent, alarmManager!!)
            }




            Repository().setIsPlanSetup(activity!!, PlanEnum.PLANSETUP.ordinal)
            activity!!.startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onResume() {
        super.onResume()

        if (isPlanSetup!!) {
            topicTitle!!.text = "PLAN SETUP [10/10]"
            var leftCancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
            leftCancel.text = "X"
            leftCancel.textSize = 24f
        } else {

            topicTitle!!.text = "Change Plan"
            (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft).visibility = View.GONE
            cancel!!.visibility = View.VISIBLE


            cancel!!.text = "X"
            cancel!!.textSize = 24f
            var bottomView = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomLayoutMain)
            bottomView.visibility = View.GONE
        }

        if (view == null) {
            return
        }

        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()

        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return if (event!!.getAction() === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (isPlanSetup!!) {
                    } else {
                        var bottomView = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomLayoutMain)
                        bottomView.visibility = View.VISIBLE
                        LoadFragment.popupBackStack(activity!!.supportFragmentManager, MainFragment.TAG)
                    }
                    true
                } else false
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun setTimePickerInterVal(timePicker: TimePicker) {
        try {
            val minutePicker = timePicker?.findViewById<NumberPicker>(Resources.getSystem().getIdentifier(
                    "minute", "id", "android")) as NumberPicker
            minutePicker.minValue = 0
            minutePicker.maxValue = (60 / TIME_PICKER_INTERVAL) - 1
            var displayedvalues = ArrayList<String>()
//            for( i in 0 until 60 step TIME_PICKER_INTERVAL){
//                displayedvalues.add(String.format("%02d",i))
//            }
            var i = 0
            while (i < 60) {
                displayedvalues.add(String.format("%02d", i))
                i += TIME_PICKER_INTERVAL
            }
            minutePicker.displayedValues = displayedvalues.toArray(arrayOfNulls<String>(0))


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun increaseTimeOfSpinnerByOne(time: String, timePicker: TimePicker) {
        try {
            var tokensWakeSaturday = DateTimeUtils.convert12HrsTo24Hrs(time).split(":")
            var hoursWakeSaturday = tokensWakeSaturday[0].toInt()
            var minutesWakeSaturday = tokensWakeSaturday[1].split(" ")[0].toInt()

            var calendarSaturdayWake = Calendar.getInstance()
            calendarSaturdayWake.timeInMillis = System.currentTimeMillis()
            var hourSaturdayWake = calendarSaturdayWake.get(Calendar.HOUR_OF_DAY)
            var minuteSaturdayWake = calendarSaturdayWake.get(Calendar.MINUTE)
            setTimePickerInterVal(timePicker!!)
//            // Configure displayed time
            if (minuteSaturdayWake % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteSaturdayWake + TIME_PICKER_INTERVAL - minuteSaturdayWake % TIME_PICKER_INTERVAL
                minuteSaturdayWake = minuteFloor + if (minuteSaturdayWake === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteSaturdayWake >= 60) {
                    minuteSaturdayWake = minuteSaturdayWake % 60
                    hourSaturdayWake++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    timePicker?.hour = hourSaturdayWake
                    timePicker?.minute = (minuteSaturdayWake / TIME_PICKER_INTERVAL)
                } else {
                    timePicker?.currentHour = hourSaturdayWake
                    timePicker?.currentMinute = (minuteSaturdayWake / TIME_PICKER_INTERVAL)
                }

            }


            if (Build.VERSION.SDK_INT >= 23) {
                timePicker?.hour = hoursWakeSaturday
                timePicker?.minute = (minutesWakeSaturday / TIME_PICKER_INTERVAL)
            } else {
                timePicker?.currentHour = hoursWakeSaturday
                timePicker?.currentMinute = (minutesWakeSaturday / TIME_PICKER_INTERVAL)
            }
            timePicker?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    saturdayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    saturdayWakeText?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                }


            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun increaseTimeOfSpinnerBySunday(time: String, timePicker: TimePicker, textView: TextView) {
        try {
            var tokensWakeSaturday = DateTimeUtils.convert12HrsTo24Hrs(time).split(":")
            var hoursWakeSaturday = tokensWakeSaturday[0].toInt()
            var minutesWakeSaturday = tokensWakeSaturday[1].split(" ")[0].toInt()

            var calendarSaturdayWake = Calendar.getInstance()
            calendarSaturdayWake.timeInMillis = System.currentTimeMillis()
            var hourSaturdayWake = calendarSaturdayWake.get(Calendar.HOUR_OF_DAY)
            var minuteSaturdayWake = calendarSaturdayWake.get(Calendar.MINUTE)
            setTimePickerInterVal(timePicker!!)
//             Configure displayed time
            if (minuteSaturdayWake % TIME_PICKER_INTERVAL !== 0) {
                val minuteFloor = minuteSaturdayWake + TIME_PICKER_INTERVAL - minuteSaturdayWake % TIME_PICKER_INTERVAL
                minuteSaturdayWake = minuteFloor + if (minuteSaturdayWake === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minuteSaturdayWake >= 60) {
                    minuteSaturdayWake = minuteSaturdayWake % 60
                    hourSaturdayWake++
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    timePicker?.hour = hourSaturdayWake
                    timePicker?.minute = (minuteSaturdayWake / TIME_PICKER_INTERVAL)
                } else {
                    timePicker?.currentHour = hourSaturdayWake
                    timePicker?.currentMinute = (minuteSaturdayWake / TIME_PICKER_INTERVAL)
                }

            }


            if (Build.VERSION.SDK_INT >= 23) {
                timePicker?.hour = hoursWakeSaturday
                timePicker?.minute = (minutesWakeSaturday / TIME_PICKER_INTERVAL)
            } else {
                timePicker?.currentHour = hoursWakeSaturday
                timePicker?.currentMinute = (minutesWakeSaturday / TIME_PICKER_INTERVAL)
            }
            timePicker?.setOnTimeChangedListener { view, hourOfDay, minute ->
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    textView?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    textView?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)
                }


            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}