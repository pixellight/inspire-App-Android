package myapp.net.inspire.plan

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_plansetup_success.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.AlarmSound
import myapp.net.inspire.data.entity.Nap
import myapp.net.inspire.data.entity.Plan
import myapp.net.inspire.data.entity.Reminder
import myapp.net.inspire.data.repository.*
import myapp.net.inspire.utils.GeneralUtils
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by QPay on 2/13/2019.
 */
class PlanSetupNineFragment : Fragment() {

    private var mPlanRepository: PlanRepository? = null
    private var mReminderRepository: ReminderRepository? = null
    private var mAlarmSoundRepository: AlarmSoundRepository? = null
    private var mNapRepository: NapRepository? = null


    companion object {
        val TAG = "PlanSetupNineFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_plansetup_success, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try{
            if (Build.VERSION.SDK_INT >= 16) {
                imageViewNine?.background = GeneralUtils.getAssetImage(activity!!, "timehelperbackground3.png")
            } else {
                imageViewNine?.setBackgroundDrawable(GeneralUtils.getAssetImage(activity!!, "timehelperbackground3.png"))
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        mPlanRepository = PlanRepository(activity)
        mReminderRepository = ReminderRepository(activity)
        mAlarmSoundRepository = AlarmSoundRepository(activity)
        mNapRepository = NapRepository(activity)
        try {
            saveData()

        }catch (e:Exception){
            e.printStackTrace()
        }
        var title = (activity as AppCompatActivity).findViewById<TextView>(R.id.title)
        var reset = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        var cancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
        cancel.text = "X"
        cancel.textSize=24f
        reset.text = getString(R.string.reset)
        title.text = "PLAN SETUP [9/10]"

        cancel.setOnClickListener {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }

        reset.setOnClickListener {
            dialogReset()
//            startActivity(Intent(activity!!, PlanSetupFristActivity::class.java))
//            activity!!.finishAffinity()
        }


        nextSuccessButton?.setOnClickListener {
            Repository().setIsSchedulePlan(activity, true)
            var fragment = PlanSetupTenFragment()
            var bundle = Bundle()
            bundle.putBoolean("isSetup", true)
            fragment.arguments = bundle
            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, fragment, R.id.container, TAG)
        }

        previousSuccessButton?.setOnClickListener {
            Repository().setIsSchedulePlan(activity,true)

            LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupEightFragment.TAG)
        }

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
            GeneralUtils.resetPlanSetup(activity)
            Repository().setIsSchedulePlan(activity,true)
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

    fun saveData() {
        if (Repository().getIsSchedulePlan(activity)!!) {
            var eatBy = Repository().getEatDinnerBy(activity)
            var doseOne = Repository().getDose1(activity)
            var doseTwo = Repository().getDose2(activity)
            var wake = Repository().getWakeUpPlanSetup(activity)
            var napOne =  Repository().getNapOne(activity)
            var napTwo =  Repository().getNapTwo(activity)
            var napThree = Repository().getNapThree(activity)
            var napOneInterval = Repository().getNapOneInterval(activity)
            var napTwoInterval = Repository().getNapTwoInterval(activity)
            var napThreeInterval = Repository().getNapThreeInterval(activity)
            var eatByReminder = Repository().getEatByReminder(activity)
            var doseOneReminder = Repository().getDoseOneReminder(activity)
            var napReminder = Repository().getNapReminder(activity)
            var doseOneAlarmSound = Repository().getDoseOneAlarm(activity)
            var doseTwoAlarmSound = Repository().getDoseTwoAlarm(activity)
            var wakeAlarmSound = Repository().getWakeAlarm(activity)
            var napStartAlarmSound = Repository().getNapStartAlarm(activity)
            var napEndAlarmSound = Repository().getNapEndAlarm(activity)
            for (i in 1..7) {

                var plan = Plan(id = i.toLong(), eatBy = eatBy!!, doseOne = doseOne!!, doseTwo = doseTwo!!, wakeUp = wake!!,
                        reminderWeekNo = i!!)
                mPlanRepository!!.updatePlan(plan)
                var reminder = Reminder(id = i.toLong(), reminderEatBy = eatByReminder!!, reminderDoseOne = doseOneReminder!!,
                        reminderNap = napReminder!!, reminderWeekNo = i!!)
                mReminderRepository!!.updateReminder(reminder)
                var alarmSound = AlarmSound(id = i.toLong(), soundDoseOne = doseOneAlarmSound!!, soundDoseTwo = doseTwoAlarmSound!!,
                        soundWakeup = wakeAlarmSound!!, soundNapStart = napStartAlarmSound!!,
                        soundNapEnd = napEndAlarmSound!!, reminderWeekNo = i!!)
                mAlarmSoundRepository!!.updateAlarmSound(alarmSound)
                var nap = Nap(id = i.toLong(), napOne = napOne!!, napTwo = napTwo!!, napThree = napThree!!,
                        reminderWeekNo = i!!, napOneInterval = napOneInterval!!, napTwoInterval = napTwoInterval!!,
                        napThreeInterval = napThreeInterval!!)
                mNapRepository!!.updateNap(nap)

            }

        } else {
            var eatBy = Repository().getEatDinnerBy(activity)
            var doseOne = Repository().getDose1(activity)
            var doseTwo = Repository().getDose2(activity)
            var wake = Repository().getWakeUpPlanSetup(activity)
            var napOne =  Repository().getNapOne(activity)
            var napTwo =  Repository().getNapTwo(activity)
            var napThree = Repository().getNapThree(activity)
            var napOneInterval = Repository().getNapOneInterval(activity)
            var napTwoInterval = Repository().getNapTwoInterval(activity)
            var napThreeInterval = Repository().getNapThreeInterval(activity)
            var eatByReminder = Repository().getEatByReminder(activity)
            var doseOneReminder = Repository().getDoseOneReminder(activity)
            var napReminder = Repository().getNapReminder(activity)
            var doseOneAlarmSound = Repository().getDoseOneAlarm(activity)
            var doseTwoAlarmSound = Repository().getDoseTwoAlarm(activity)
            var wakeAlarmSound = Repository().getWakeAlarm(activity)
            var napStartAlarmSound = Repository().getNapStartAlarm(activity)
            var napEndAlarmSound = Repository().getNapEndAlarm(activity)
            for (i in 1..7) {

                var plan = Plan(id = null, eatBy = eatBy!!, doseOne = doseOne!!, doseTwo = doseTwo!!, wakeUp = wake!!,
                        reminderWeekNo = i!!)
                mPlanRepository!!.insertPlan(plan)
                var reminder = Reminder(id = null, reminderEatBy = eatByReminder!!, reminderDoseOne = doseOneReminder!!,
                        reminderNap = napReminder!!, reminderWeekNo = i!!)
                mReminderRepository!!.insertReminder(reminder)
                var alarmSound = AlarmSound(id = null, soundDoseOne = doseOneAlarmSound!!, soundDoseTwo = doseTwoAlarmSound!!,
                        soundWakeup = wakeAlarmSound!!, soundNapStart = napStartAlarmSound!!,
                        soundNapEnd = napEndAlarmSound!!, reminderWeekNo = i!!)
                mAlarmSoundRepository!!.insertAlarmSound(alarmSound)
                var nap = Nap(id = null, napOne = napOne!!, napTwo = napTwo!!, napThree = napThree!!,
                        reminderWeekNo = i!!, napOneInterval = napOneInterval!!, napTwoInterval = napTwoInterval!!,
                        napThreeInterval = napThreeInterval!!)
                mNapRepository!!.insertNap(nap)

            }

        }

    }




}