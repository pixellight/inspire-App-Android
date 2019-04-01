package myapp.net.inspire.reminder

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_nap_options.*
import kotlinx.android.synthetic.main.fragment_reminder.*
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.Nap
import myapp.net.inspire.data.entity.Reminder
import myapp.net.inspire.data.repository.ReminderRepository
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.plan.PlanSetupFristActivity
import myapp.net.inspire.plan.PlanSetupTenFragment
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by QPay on 2/10/2019.
 */
class ReminderFragment : AppCompatActivity() {
    companion object {
        val TAG = "ReminderFragment"
    }

    private var isEatBy: Boolean? = true
    private var isDoseOne: Boolean? = true
    private var isNap: Boolean? = true
    private var weekId: Int? = 0
    private var mReminderRepository: ReminderRepository? = null
    private var cross: TextView? = null
    private var back: TextView? = null
    private var isPlanSetup: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_reminder)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        toolbar.visibility = View.VISIBLE
        var reset = (this@ReminderFragment as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
       

        reset.text = getString(R.string.reset)
        title.text = "Reminders"
//        try {
//            isPlanSetup = intent.extras.getBoolean("isSetup")
//            if (isPlanSetup!!) {
//                back = (this@this@ReminderFragment as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
//
//                back!!.text = getString(R.string.back)
//
//            } else {
//                cross = (this@this@ReminderFragment as AppCompatActivity).findViewById<TextView>(R.id.cross)
//
//                cross!!.text = getString(R.string.back)
//            }
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        try {
//            back!!.setOnClickListener {
//                var reminder = Reminder(id = weekId!!.toLong(), reminderEatBy = isEatBy!!,
//                        reminderDoseOne = isDoseOne!!, reminderNap = isNap!!, reminderWeekNo = weekId!!)
//                mReminderRepository!!.updateReminder(reminder)
//                LoadFragment.popupBackStack(this@ReminderFragment.supportFragmentManager, PlanSetupTenFragment.TAG)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        try {
//            cross!!.setOnClickListener {
//                var reminder = Reminder(id = weekId!!.toLong(), reminderEatBy = isEatBy!!,
//                        reminderDoseOne = isDoseOne!!, reminderNap = isNap!!, reminderWeekNo = weekId!!)
//                mReminderRepository!!.updateReminder(reminder)
//                LoadFragment.popupBackStack(this@this@ReminderFragment.supportFragmentManager, PlanSetupTenFragment.TAG)
//
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

        reset.setOnClickListener {
//            dialogReset()
            resetReminer()
        }
        try {
            weekId = intent.extras.getInt("week")
            mReminderRepository = ReminderRepository(this@ReminderFragment)
            var reminder = mReminderRepository!!.getReminderById(weekId!!)
            isEatBy = reminder.reminderEatBy
            isDoseOne = reminder.reminderDoseOne
            isNap = reminder.reminderNap
            switchEatBy?.isChecked = isEatBy!!
            switchNap?.isChecked = isNap!!
            switchDoseOne?.isChecked = isDoseOne!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
        switchEatBy?.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                isEatBy = isChecked
            } else {
                isEatBy = isChecked
            }
        }

        switchDoseOne?.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                isDoseOne = isChecked
            } else {
                isDoseOne = isChecked
            }
        }

        switchNap?.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                isNap = isChecked
            } else {
                isNap = isChecked
            }
        }
    }

//    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater!!.inflate(R.layout.fragment_reminder, container, false)
//    }
//
//    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//      
//    }


    fun dialogReset() {
        val builder = android.support.v7.app.AlertDialog.Builder(this@ReminderFragment)
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.dialog_reset, null)
        builder.setView(v)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        v.findViewById<TextView>(R.id.resetPlan).setOnClickListener(View.OnClickListener {
            //            Repository().setIsPlanSetup(this@this@ReminderFragment, PlanEnum.NOPLAN.ordinal)
            Repository().setIsSchedulePlan(this@ReminderFragment, true)
            startActivity(Intent(this@ReminderFragment!!, PlanSetupFristActivity::class.java))
            finishAffinity()
            dialog.dismiss()
        })
        v.findViewById<TextView>(R.id.cancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        val density = resources.displayMetrics.density
        lp.width = (300 * density).toInt()
        lp.height = (325 * density).toInt()
        lp.gravity = Gravity.CENTER
        dialog.window!!.attributes = lp
    }

//    override fun onResume() {
//        super.onResume()
//        if (view == null) {
//            return
//        }
//
//        view!!.isFocusableInTouchMode = true
//        view!!.requestFocus()
//
//        view!!.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
//                return if (event!!.getAction() === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    var reminder = Reminder(id = weekId!!.toLong(), reminderEatBy = isEatBy!!,
//                            reminderDoseOne = isDoseOne!!, reminderNap = isNap!!, reminderWeekNo = weekId!!)
//                    mReminderRepository!!.updateReminder(reminder)
//                    LoadFragment.popupBackStack(this@this@ReminderFragment!!.supportFragmentManager, PlanSetupTenFragment.TAG)
//                    true
//                } else false
//            }
//
//        })
//
//    }


    override fun onBackPressed() {
        super.onBackPressed()
        var reminder = Reminder(id = weekId!!.toLong(), reminderEatBy = isEatBy!!,
                reminderDoseOne = isDoseOne!!, reminderNap = isNap!!, reminderWeekNo = weekId!!)
        mReminderRepository!!.updateReminder(reminder)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                var reminder = Reminder(id = weekId!!.toLong(), reminderEatBy = isEatBy!!,
                        reminderDoseOne = isDoseOne!!, reminderNap = isNap!!, reminderWeekNo = weekId!!)
                mReminderRepository!!.updateReminder(reminder)
                finish()
                }

            }
        return super.onOptionsItemSelected(item)

    }
    private fun resetReminer(){
        switchEatBy?.isChecked=true
        switchDoseOne?.isChecked=true
        switchNap?.isChecked=true
    }
}