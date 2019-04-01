package myapp.net.inspire.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.plan_set_picker_layout.*
import kotlinx.android.synthetic.main.plan_setup_fragment.*
import myapp.net.inspire.R
import myapp.net.inspire.plan.PlanSetupActivity
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.GeneralUtils

/**
 * Created by deadlydragger on 10/24/18.
 */

class PlanFragment : Fragment() {
    private var color = R.color.darkThemeBackground


    companion object {
        val TAG=PlanFragment.javaClass.simpleName
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutSetData = view?.findViewById<LinearLayout>(R.id.layoutSetData)
        val doseONe = view!!.findViewById<TextView>(R.id.doseOne)
        val doseTwo = view.findViewById<TextView>(R.id.doseTwo)
        val eatBy = view!!.findViewById<TextView>(R.id.eatBy)
        val wake = view!!.findViewById<TextView>(R.id.wake)
        (activity as PlanSetupActivity).titleRight?.setOnClickListener {
            dialogReminder()
            layoutSetData?.visibility = View.GONE
            (activity as PlanSetupActivity).bottomView?.visibility = View.VISIBLE
        }
        doseONe.setOnClickListener {

            doseONe.setBackgroundColor(resources.getColor(R.color.planBackground))
            doseONe.setTextColor(resources.getColor(R.color.white))

            doseTwo.setBackgroundColor(resources.getColor(R.color.transparent))

            doseTwo.setTextColor(resources.getColor(R.color.black))

            eatBy.setBackgroundColor(resources.getColor(R.color.transparent))
            eatBy.setTextColor(resources.getColor(R.color.black))

            wake.setBackgroundColor(resources.getColor(R.color.transparent))
            wake.setTextColor(resources.getColor(R.color.black))


        }
        doseTwo.setOnClickListener {
            doseTwo.setBackgroundColor(resources.getColor(R.color.planBackground))

            doseTwo.setTextColor(resources.getColor(R.color.white))

            doseONe.setBackgroundColor(resources.getColor(R.color.transparent))
            doseONe.setTextColor(resources.getColor(R.color.black))

            eatBy.setBackgroundColor(resources.getColor(R.color.transparent))
            eatBy.setTextColor(resources.getColor(R.color.black))

            wake.setBackgroundColor(resources.getColor(R.color.transparent))
            wake.setTextColor(resources.getColor(R.color.black))


        }
        eatBy.setOnClickListener {
            eatBy.setBackgroundColor(resources.getColor(R.color.planBackground))
            eatBy.setTextColor(resources.getColor(R.color.white))

            doseONe.setBackgroundColor(resources.getColor(R.color.transparent))
            doseONe.setTextColor(resources.getColor(R.color.black))

            doseTwo.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwo.setTextColor(resources.getColor(R.color.black))

            wake.setBackgroundColor(resources.getColor(R.color.transparent))
            wake.setTextColor(resources.getColor(R.color.black))

        }
        wake.setOnClickListener {
            wake.setBackgroundColor(resources.getColor(R.color.planBackground))
            wake.setTextColor(resources.getColor(R.color.white))

            doseONe.setBackgroundColor(resources.getColor(R.color.transparent))
            doseONe.setTextColor(resources.getColor(R.color.black))

            doseTwo.setBackgroundColor(resources.getColor(R.color.transparent))
            doseTwo.setTextColor(resources.getColor(R.color.black))

            eatBy.setBackgroundColor(resources.getColor(R.color.transparent))
            eatBy.setTextColor(resources.getColor(R.color.black))
        }
        view!!.findViewById<LinearLayout>(R.id.tuesday).setOnClickListener {
            (activity as PlanSetupActivity).bottomView?.visibility = View.GONE
            layoutSetData?.visibility = View.VISIBLE
        }

        sunday.setOnClickListener {

        }
        monday.setOnClickListener {

        }

        wensday.setOnClickListener {

        }

        thursday.setOnClickListener {

        }

        friday.setOnClickListener {

        }

        saturday.setOnClickListener {

        }



        setCurrentDaySelection(DateTimeUtils.getCurrentDay().toLowerCase())


        napLayoutSchedule?.setOnClickListener {
//            LoadFragment.addFragmentToBackStack(activity.supportFragmentManager, NapFragment(), R.id.fragmennt, TAG)
        }

        reminderLayout?.setOnClickListener {
//            LoadFragment.addFragmentToBackStack(activity.supportFragmentManager, ReminderFragment(), R.id.fragmennt, TAG)
        }

        soundsLayout?.setOnClickListener {
//            LoadFragment.addFragmentToBackStack(activity.supportFragmentManager, AlarmFragment(), R.id.fragmennt, TAG)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.plan_setup_fragment, container, false)
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

}
