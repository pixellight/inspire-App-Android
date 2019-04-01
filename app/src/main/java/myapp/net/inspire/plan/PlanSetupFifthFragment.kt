package myapp.net.inspire.plan

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_plansetup_five.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.GeneralUtils
import myapp.net.inspire.utils.LoadFragment


/**
 * Created by QPay on 2/13/2019.
 */
class PlanSetupFifthFragment : Fragment() {

    companion object {
        val TAG = "PlanSetupFifthFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_plansetup_five, container, false);
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var title = (activity as AppCompatActivity).findViewById<TextView>(R.id.title)
        var reset = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        var cancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
        cancel.text = "X"
        cancel.textSize=24f
        reset.text = getString(R.string.reset)
        title.text = "PLAN SETUP [5/10]"

        cancel.setOnClickListener {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }

        reset.setOnClickListener {
            dialogReset()
//            startActivity(Intent(activity!!, PlanSetupFristActivity::class.java))
//            activity!!.finishAffinity()
        }


        println("Dose1:: " + Repository().getDose1(activity!!) + " Dose2:: " + Repository().getDose2(activity!!))

        doseOneTextview?.text = Repository().getDose1(activity!!)
        doseTwoTextview?.text = Repository().getDose2(activity!!)
        wakeUpTextview?.text = Repository().getWakeUpPlanSetup(activity!!)
        var dose1= Repository().getDose1(activity!!)
        var eatDinnerBy = DateTimeUtils.calculateEatByDinner(dose1!!)
        Repository().setEatDinnerBy(activity!!, eatDinnerBy)
        eatDinnerByTextview?.text = Repository().getEatDinnerBy(activity!!)

        nextFiveButton?.setOnClickListener {
            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, PlanSetupSixthFragment(), R.id.container, TAG)
        }

        previousFiveButton?.setOnClickListener {
            LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupFourthFragment.TAG)
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
                    LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupFourthFragment.TAG)
                    true
                } else false
            }

        })

    }

}