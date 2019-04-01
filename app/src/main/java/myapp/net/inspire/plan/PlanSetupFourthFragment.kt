package myapp.net.inspire.plan

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_plansetup_four.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.AppCache
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.GeneralUtils
import myapp.net.inspire.utils.LoadFragment
import java.util.*

/**
 * Created by QPay on 2/13/2019.
 */
class PlanSetupFourthFragment : Fragment() {
    companion object {
        val TAG = "PlanSetupFourthFragment"
    }

    private var times: ArrayList<String>? = null
    private var selectDoseTime: String? = "4:00"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_plansetup_four, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var title = (activity as AppCompatActivity).findViewById<TextView>(R.id.title)
        var reset = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        var cancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
        cancel.text = "X"
        cancel.textSize=24f
        reset.text = getString(R.string.reset)
        title.text = "PLAN SETUP [4/10]"

        cancel.setOnClickListener {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }

        reset.setOnClickListener {
            doseFourWakeTimePicker?.value = 18
            AppCache.timeBetweenDoses=18
//            dialogReset()
//            startActivity(Intent(activity!!, PlanSetupFristActivity::class.java))
//            activity!!.finishAffinity()
        }

        setUpWheelView()
        println("Time doses:: " + selectDoseTime)
        nextFourButton?.setOnClickListener {
            val array = arrayOfNulls<String>(times!!.size)
            var timesArr = times!!.toArray(array)
            Repository().setTimeBetweenDoses(activity!!, timesArr[AppCache.timeBetweenDoses]!!)

            var dose1 = DateTimeUtils.calculateTimeBack(Repository().getDose2(activity)!!, Repository().getTimeBetweenDoses(activity)!!)
            Repository().setDose1(activity!!, dose1)
            println("Dose1:: " + dose1)
//            println("Time between Doses:: "+Repository().getTimeBetweenDoses(activity)!!)
            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, PlanSetupFifthFragment(), R.id.container, TAG)
        }

        previousFourButton?.setOnClickListener {
            LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupThirdFragment.TAG)
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
                    LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupThirdFragment.TAG)
                    true
                } else false
            }

        })

    }

    private fun setUpWheelView() {
        times = ArrayList()
        for (i in 2 until 4 step 1) {
            if(i>=3){
                for (j in 5 until 65 step 5) {
                    if (j < 10) {
                        times!!.add(i.toString() + ":" + "0" + j)
                    } else {
                        if (j == 60) {
                            times!!.add((i+1).toString() + ":" + "00")

                        } else {
                            times!!.add(i.toString() + ":" + j)

                        }

                    }

                }
            }else{
                for (j in 30 until 65 step 5) {
                    if (j < 10) {
                        times!!.add(i.toString() + ":" + "0" + j)
                    } else {
                        if (j == 60) {
                            times!!.add((i+1).toString() + ":" + "00")

                        } else {
                            times!!.add(i.toString() + ":" + j)

                        }

                    }

                }
            }

        }


        val array = arrayOfNulls<String>(times!!.size)
        var timesArr = times!!.toArray(array)

        var timeArray = Arrays.toString(array)
        println(timeArray)

        doseFourWakeTimePicker?.minValue = 0
        doseFourWakeTimePicker?.maxValue = timesArr!!.size - 1
        doseFourWakeTimePicker?.displayedValues = timesArr
        doseFourWakeTimePicker?.wrapSelectorWheel = true
        if(AppCache.timeBetweenDoses>=0){
            doseFourWakeTimePicker?.value = AppCache.timeBetweenDoses

        }else{
            doseFourWakeTimePicker?.value = 18

        }
//        selectDoseTime = timesArr.get(timesArr!!.lastIndex - 1)
        println("Selected values:: " + selectDoseTime)

        doseFourWakeTimePicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            selectDoseTime = timesArr[newVal].toString()
            AppCache.setTimeBetweenDoses(newVal)
        }

    }
}