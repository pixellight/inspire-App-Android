package myapp.net.inspire.plan

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.fragment_plansetup_two.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.AppCache
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.LoadFragment
import java.util.*

/**
 * Created by QPay on 2/12/2019.
 */
class PlanSetupSecondFragment : Fragment() {
    companion object {
        val TAG = "PlanSetupSecondFragment"
    }

    private var hour: Int? = 1
    private var minute: Int? = 0
    private var finalHour: Int? = 0
    private var getAMPMValue: String? = null
    private var time: String? = "7:00 AM"
    private var isSelect: Boolean? = false
    private val TIME_PICKER_INTERVAL = 5

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_plansetup_two, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var title = (activity as AppCompatActivity).findViewById<TextView>(R.id.title)
        var reset = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        var cancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
        cancel.text = "X"
        cancel.textSize = 24f
        reset.text = getString(R.string.reset)
        title.text = "PLAN SETUP [2/10]"

        cancel.setOnClickListener {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }

        reset.setOnClickListener {
//            dialogReset()
            if (Build.VERSION.SDK_INT >= 23) {
                twoTimePicker?.hour = 7
                twoTimePicker?.minute = 0
            } else {
                twoTimePicker?.currentHour = 7
                twoTimePicker?.currentMinute = 0
            }
        }

        time = "7:00 AM"
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)
        var am_pm = calendar.get(Calendar.AM_PM)


        setTimePickerInterVal(twoTimePicker!!)
        // Configure displayed time
        if (minute!! % TIME_PICKER_INTERVAL !== 0) {
            val minuteFloor = minute!! + TIME_PICKER_INTERVAL - minute!! % TIME_PICKER_INTERVAL
            minute = minuteFloor + if (minute === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
            if (minute!! >= 60) {
                minute = minute!! % 60
                hour=hour!!+1
            }

        }
        if (!AppCache.wakeUpTime.isNullOrEmpty()) {
            var saveTime = AppCache.wakeUpTime
            var hr_mins = DateTimeUtils.convert12HrsTo24Hrs(AppCache.wakeUpTime!!)
            var tokens = hr_mins.split(":")
            var hrs = tokens[0]
            var mins = tokens[1]
            setTimePickerInterVal(twoTimePicker!!)
            if (Build.VERSION.SDK_INT >= 23) {
                twoTimePicker?.hour = hrs!!.toInt()
                    twoTimePicker?.minute = mins!!.toInt()/5

            } else {
                twoTimePicker?.currentHour = hrs!!.toInt()
                    twoTimePicker?.currentMinute = mins!!.toInt()/5


            }
        } else {

            if (Build.VERSION.SDK_INT >= 23) {
                twoTimePicker?.hour = 7
                twoTimePicker?.minute = 0
            } else {
                twoTimePicker?.currentHour = 7
                twoTimePicker?.currentMinute = 0
            }
        }


        twoTimePicker?.setOnTimeChangedListener { view, hourOfDay, minute ->
            if (Build.VERSION.SDK_INT >= 23) {
                val hour = String.format("%02d", view!!.hour)
                val minutes = String.format("%02d", view!!.minute * 5)
                time = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)!!.toUpperCase()
                AppCache.wakeUpTime=time
                println("Time:: "+time)


            } else {
                val hour = String.format("%02d", view!!.currentHour)
                val minutes = String.format("%02d", view!!.currentMinute * 5)

                time = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)!!.toUpperCase()
                AppCache.wakeUpTime=time
                println("Time:: "+time)

            }


        }


        nextButton?.setOnClickListener {
            println("time is:: " + time)
            Repository().setWakeUpPlanSetup(activity, AppCache.wakeUpTime!!)
            AppCache.wakeUpTime = time!!
            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, PlanSetupThirdFragment(), R.id.container, TAG)
        }

        previousButton?.setOnClickListener {

            LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupFirstFragment.TAG)
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

    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }

        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()

        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return if (event!!.getAction() === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupFirstFragment.TAG)
                    true
                } else false
            }

        })

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


}