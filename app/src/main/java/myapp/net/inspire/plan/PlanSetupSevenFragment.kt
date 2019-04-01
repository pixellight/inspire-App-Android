package myapp.net.inspire.plan

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_plansetup_seven.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.AppCache
import myapp.net.inspire.utils.GeneralUtils
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by QPay on 2/13/2019.
 */
class PlanSetupSevenFragment : Fragment() {

    companion object {
        val TAG = "PlanSetupSevenFragment"
    }

    private var isEatBy: Boolean? = true
    private var isDoseOne: Boolean? = true
    private var isNap: Boolean? = true

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_plansetup_seven, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var title = (activity as AppCompatActivity).findViewById<TextView>(R.id.title)
        var reset = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        var cancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
        cancel.text = "X"
        cancel.textSize = 24f
        reset.text = getString(R.string.reset)
        title.text = "PLAN SETUP [7/10]"

        cancel.setOnClickListener {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }

        reset.setOnClickListener {
            //            startActivity(Intent(activity!!, PlanSetupFristActivity::class.java))
//            activity!!.finishAffinity()
//            dialogReset()
            resetReminer()
        }


        nextSevenButton?.setOnClickListener {
            Repository().setEatByReminder(activity!!, isEatBy!!)
            Repository().setDoseOneReminder(activity!!, isDoseOne!!)
            Repository().setNapReminder(activity!!, isNap!!)
            AppCache.eatBy = isEatBy!!
            AppCache.doseOne = isDoseOne!!
            AppCache.napReminder = isNap!!
            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, PlanSetupEightFragment(), R.id.container, TAG)
        }

        previousSevenButton?.setOnClickListener {
            LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupSixthFragment.TAG)
        }

        switchEatByReminder?.isChecked = AppCache.eatBy
        switchDoseOneReminder?.isChecked = AppCache.doseOne
        switchNapReminder?.isChecked = AppCache.napReminder

        switchEatByReminder?.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                isEatBy = isChecked
            } else {
                isEatBy = isChecked
            }
        }

        switchDoseOneReminder?.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                isDoseOne = isChecked
            } else {
                isDoseOne = isChecked
            }
        }

        switchNapReminder?.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                isNap = isChecked
            } else {
                isNap = isChecked
            }
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

    private fun resetReminer(){
        switchEatByReminder?.isChecked=true
        switchDoseOneReminder?.isChecked=true
        switchNapReminder?.isChecked=true

        AppCache.eatBy = true
        AppCache.doseOne = true
        AppCache.napReminder = true
    }

}