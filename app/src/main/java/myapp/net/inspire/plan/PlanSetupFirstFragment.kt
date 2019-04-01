package myapp.net.inspire.plan

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_plansetup_one.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.utils.AppCache
import myapp.net.inspire.utils.GeneralUtils
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by QPay on 2/12/2019.
 */
class PlanSetupFirstFragment : Fragment() {

    companion object {
        val TAG = "PlanSetupFirstFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_plansetup_one, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                imageView?.background = GeneralUtils.getAssetImage(activity!!, "timehelperbackground2.png")
            } else {
                imageView?.setBackgroundDrawable(GeneralUtils.getAssetImage(activity!!, "timehelperbackground2.png"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


        dialogReminder()

        beginButton?.setOnClickListener {
            AppCache.wakeUpTime="7:00 AM"
            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, PlanSetupSecondFragment(), R.id.container, TAG)
        }
    }

    fun dialogReminder() {
        val builder = android.support.v7.app.AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val v = inflater.inflate(R.layout.dialog_tips, null)
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
        var title = (activity as AppCompatActivity).findViewById<TextView>(R.id.title)
        var reset = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        var cancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
        cancel.text = "X"
        cancel.textSize = 24f
        reset.text = ""
        title.text = "PLAN SETUP [1/10]"
        cancel.setOnClickListener {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }

        reset.setOnClickListener {
            dialogReset()
//            startActivity(Intent(activity!!, PlanSetupFristActivity::class.java))
//            activity!!.finishAffinity()
        }
        if (view == null) {
            return
        }

        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()

        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return if (event!!.getAction() === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    activity!!.finish()
                    true
                } else false
            }

        })
    }


}