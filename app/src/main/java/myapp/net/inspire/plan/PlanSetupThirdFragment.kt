package myapp.net.inspire.plan

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_plansetup_three.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.AppCache
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.GeneralUtils
import myapp.net.inspire.utils.LoadFragment
import java.util.*


/**
 * Created by QPay on 2/12/2019.
 */
class PlanSetupThirdFragment : Fragment() {
    companion object {
        val TAG = "PlanSetupThirdFragment"
    }

    private var times: ArrayList<String>? = null
    private var selectDoseTime: String? = "4:00"


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_plansetup_three, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var title = (activity as AppCompatActivity).findViewById<TextView>(R.id.title)
        var reset = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        var cancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
        cancel.text = "X"
        cancel.textSize = 24f
        reset.text = getString(R.string.reset)
        title.text = "PLAN SETUP [3/10]"
        cancel.setOnClickListener {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }

        reset.setOnClickListener {
//            dialogReset()
            doseTwoWakeTimePicker?.value = 18
            AppCache.doseTwoWakeUp=18
        }

        setUpWheelView()
        nextThreeButton?.setOnClickListener {
            val array = arrayOfNulls<String>(times!!.size)
            var timesArr = times!!.toArray(array)
            Repository().setDoseTwoWakeUp(activity!!, timesArr[AppCache.doseTwoWakeUp]!!)
            var dose2 = DateTimeUtils.calculateTimeBack(Repository().getWakeUpPlanSetup(activity)!!, Repository().getDoseTwoWakeUp(activity)!!)
            Repository().setDose2(activity!!, dose2)

            println("Dose2:: " + dose2)

            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, PlanSetupFourthFragment(), R.id.container, TAG)
        }

        previousThreeButton?.setOnClickListener {
            LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupSecondFragment.TAG)
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
                    LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupSecondFragment.TAG)
                    true
                } else false
            }

        })

    }

    private fun setUpWheelView() {
        times = ArrayList()
        for (i in 2 until 6 step 1) {
            if (i >= 3) {
                for (j in 5 until 65 step 5) {
                    if (j < 10) {
                        times!!.add(i.toString() + ":" + "0" + j)
                    } else {
                        if (j == 60) {
                            times!!.add((i + 1).toString() + ":" + "00")

                        } else {
                            times!!.add(i.toString() + ":" + j)

                        }

                    }

                }
            } else {
                for (j in 30 until 65 step 5) {
                    if (j < 10) {
                        times!!.add(i.toString() + ":" + "0" + j)
                    } else {
                        if (j == 60) {
                            times!!.add((i + 1).toString() + ":" + "00")

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

        doseTwoWakeTimePicker?.minValue = 0
        doseTwoWakeTimePicker?.maxValue = timesArr!!.size - 1
        doseTwoWakeTimePicker?.displayedValues = timesArr
        doseTwoWakeTimePicker?.wrapSelectorWheel = true
        if (AppCache.getDoseTwoWakeUp() >= 0) {
            doseTwoWakeTimePicker?.value = AppCache.getDoseTwoWakeUp()

        } else {
            doseTwoWakeTimePicker?.value = 18

        }
//        selectDoseTime = timesArr.get(timesArr!!.lastIndex - 1)
        doseTwoWakeTimePicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            selectDoseTime = timesArr[newVal].toString()
            println("Selected values:: " + timesArr[newVal])
            AppCache.setDoseTwoWakeUp(newVal!!)
        }


    }


}