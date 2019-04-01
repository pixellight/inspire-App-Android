package myapp.net.inspire.nap

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
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.NumberPicker
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.fragment_nap_options.*
import kotlinx.android.synthetic.main.nap_one_layout.*
import kotlinx.android.synthetic.main.nap_three_layout.*
import kotlinx.android.synthetic.main.nap_two_layout.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.Nap
import myapp.net.inspire.data.repository.NapRepository
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.nap.adapter.NapOneAdapter
import myapp.net.inspire.plan.PlanSetupFristActivity
import myapp.net.inspire.plan.PlanSetupTenFragment
import myapp.net.inspire.utils.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by QPay on 2/10/2019.
 */
class NapFragment : AppCompatActivity() {

    companion object {
        val TAG = "NapFragment"
    }

    private var minutes = ArrayList<Int>()
    private var napOneLayout: RelativeLayout? = null
    private var adapterNapTwo: NapOneAdapter? = null
    private var adapterNapThree: NapOneAdapter? = null
    private var adapterNapOne: NapOneAdapter? = null
    private var isOpenNapOneLayout: Boolean? = false
    private var isOpenNapTwoLayout: Boolean? = false
    private var isOpenNapThreelayout: Boolean? = false
    private var napOneInterval: String? = "-1"
    private var napTwoInterval: String? = "-1"
    private var napThreeInterval: String? = "-1"
    private var napOneTime: String? = "-1"
    private var napTwoTime: String? = "-1"
    private var napThreeTime: String? = "-1"

    private var weekId: Int? = 0
    private var mNapRepository: NapRepository? = null
    private var cross: TextView? = null
    private var back: TextView? = null
    private var isPlanSetup: Boolean? = false
    private val TIME_PICKER_INTERVAL = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_nap_options)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        toolbar.visibility = View.VISIBLE
        title.text = "Naps"
        var reset = (this@NapFragment as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        weekId = intent.extras.getInt("week")
        mNapRepository = NapRepository(this@NapFragment)
        var nap = mNapRepository!!.getNapById(weekId!!)
        try {
            isPlanSetup = intent.extras.getBoolean("isSetup")
            if (isPlanSetup!!) {
                back = (this@NapFragment as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)

                back!!.text = getString(R.string.back)

            } else {
                cross = (this@NapFragment as AppCompatActivity).findViewById<TextView>(R.id.cross)

                cross!!.text = getString(R.string.back)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        reset.text = getString(R.string.reset)
        title.text = "Naps"

        try {
            back!!.setOnClickListener {
                napOneTime = if (chooseNap1Textview!!.text.equals(getString(R.string.choose))) "-1" else chooseNap1Textview!!.text.toString()
                napTwoTime = if (chooseNaptwoTextview!!.text.equals(getString(R.string.choose))) "-1" else chooseNaptwoTextview!!.text.toString()
                napThreeTime = if (chooseNapThreeTextview!!.text.equals(getString(R.string.choose))) "-1" else chooseNapThreeTextview!!.text.toString()
                if (napOneTime.equals("-1") && napTwoTime.equals("-1") && napThreeTime.equals("-1")) {
                    LoadFragment.popupBackStack(this@NapFragment!!.supportFragmentManager, PlanSetupTenFragment.TAG)
                } else {
                    var nap = Nap(id = weekId!!.toLong(), napOne = napOneTime!!, napTwo = napTwoTime!!, napThree = napThreeTime!!,
                            reminderWeekNo = weekId!!, napOneInterval = napOneInterval!!,
                            napTwoInterval = napTwoInterval!!, napThreeInterval = napThreeInterval!!)
                    mNapRepository!!.updateNap(nap)
                    LoadFragment.popupBackStack(this@NapFragment!!.supportFragmentManager, PlanSetupTenFragment.TAG)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        try {
            cross!!.setOnClickListener {
                napOneTime = if (chooseNap1Textview!!.text.equals(getString(R.string.choose))) "-1" else chooseNap1Textview!!.text.toString()
                napTwoTime = if (chooseNaptwoTextview!!.text.equals(getString(R.string.choose))) "-1" else chooseNaptwoTextview!!.text.toString()
                napThreeTime = if (chooseNapThreeTextview!!.text.equals(getString(R.string.choose))) "-1" else chooseNapThreeTextview!!.text.toString()
                if (napOneTime.equals("-1") && napTwoTime.equals("-1") && napThreeTime.equals("-1")) {
                    LoadFragment.popupBackStack(this@NapFragment!!.supportFragmentManager, PlanSetupTenFragment.TAG)
                } else {
                    var nap = Nap(id = weekId!!.toLong(), napOne = napOneTime!!, napTwo = napTwoTime!!, napThree = napThreeTime!!,
                            reminderWeekNo = weekId!!, napOneInterval = napOneInterval!!,
                            napTwoInterval = napTwoInterval!!, napThreeInterval = napThreeInterval!!)
                    mNapRepository!!.updateNap(nap)
                    LoadFragment.popupBackStack(this@NapFragment!!.supportFragmentManager, PlanSetupTenFragment.TAG)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        reset.setOnClickListener {
//            dialogReset()
            resetNaps()
        }



        try {
            napOne()
            napTwo()
            napThree()
            layoutClick()
            setTextFromDB(nap)
            chooseNap1Textview?.setTextColor(Color.BLACK)
            if(!chooseNap1Textview?.text.toString().equals("Choose")){
                chooseNaptwoTextview?.setTextColor(Color.BLACK)
                chooseNapThreeTextview?.setTextColor(Color.BLACK)
                napTwoTextview?.setTextColor(Color.BLACK)
                napThreeTextview?.setTextColor(Color.BLACK)
                napTwoLayout?.isClickable=true
                napThreeLayout?.isClickable=true

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }




        loadMinutesData()
        try {
            if (nap.napOneInterval.equals("-1")) {

            } else {
                napOneRecyclerview.scrollToPosition(nap.napOneInterval.toInt())
            }

            if (nap.napTwoInterval.equals("-1")) {

            } else {
                napTwoRecyclerview.scrollToPosition(nap.napTwoInterval.toInt())
            }

            if (nap.napThreeInterval.equals("-1")) {

            } else {
                napThreeRecyclerview.scrollToPosition(nap.napThreeInterval.toInt())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun dialogReset() {
        val builder = android.support.v7.app.AlertDialog.Builder(this@NapFragment)
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.dialog_reset, null)
        builder.setView(v)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        v.findViewById<TextView>(R.id.resetPlan).setOnClickListener(View.OnClickListener {
            //            Repository().setIsPlanSetup(this@NapFragment,PlanEnum.NOPLAN.ordinal)
            Repository().setIsSchedulePlan(this@NapFragment, true)

            startActivity(Intent(this@NapFragment!!, PlanSetupFristActivity::class.java))
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

    private fun loadMinutesData() {
        var n = 55
        var i = 5
        while (i <= n) {
            minutes.add(i)
            i = i + 5
        }

        //set padding to particular items will appear in the middle of the screen
        val padding: Int = ScreenUtils.getScreenWidth(this@NapFragment) / 2 - ScreenUtils.dpToPx(this@NapFragment, 40)
        //adapter one
        napOneRecyclerview.setPadding(padding, 0, padding, 0)
        //setting layout manager
        napOneRecyclerview.layoutManager = SliderLayoutManager(this@NapFragment).apply {
            napOneInterval = minutes.get(4).toString()
            callback = object : SliderLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(layoutPosition: Int) {
                    println("Selected Item:: " + minutes.get(layoutPosition))
                    napOneInterval = layoutPosition.toString()

                }

            }
        }
        adapterNapOne = NapOneAdapter(minutes)
        napOneRecyclerview.adapter = adapterNapOne
        adapterNapOne!!.notifyDataSetChanged()
        //end adapter one

        //adapter two
        napTwoRecyclerview.setPadding(padding, 0, padding, 0)
        //setting layout manager
        napTwoRecyclerview.layoutManager = SliderLayoutManager(this@NapFragment).apply {
            scrollToPosition(8)
            napTwoInterval = minutes.get(4).toString()
            callback = object : SliderLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(layoutPosition: Int) {
                    println("Selected Item:: " + minutes.get(layoutPosition))
                    napTwoInterval = layoutPosition.toString()
                }

            }
        }

        adapterNapTwo = NapOneAdapter(minutes)
        napTwoRecyclerview.adapter = adapterNapTwo
        adapterNapTwo!!.notifyDataSetChanged()
        //end adapter two

        //adapter three
        napThreeRecyclerview.setPadding(padding, 0, padding, 0)
        //setting layout manager
        napThreeRecyclerview.layoutManager = SliderLayoutManager(this@NapFragment).apply {
            scrollToPosition(8)
            napThreeInterval = minutes.get(4).toString()
            callback = object : SliderLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(layoutPosition: Int) {
                    println("Selected Item:: " + minutes.get(layoutPosition))
                    napThreeInterval = layoutPosition.toString()
                }

            }
        }

        adapterNapThree = NapOneAdapter(minutes)
        napThreeRecyclerview.adapter = adapterNapThree
        adapterNapThree!!.notifyDataSetChanged()
        //end adapter three

    }

    private fun napOne() {
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minute = calendar.get(Calendar.MINUTE)
        var am_pm = calendar.get(Calendar.AM_PM)
        setTimePickerInterVal(editNapTimePicker!!)
        // Configure displayed time
        if (minute % TIME_PICKER_INTERVAL !== 0) {
            val minuteFloor = minute + TIME_PICKER_INTERVAL - minute % TIME_PICKER_INTERVAL
            minute = minuteFloor + if (minute === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
            if (minute >= 60) {
                minute = minute % 60
                hour++
            }
            if (Build.VERSION.SDK_INT >= 23) {
                editNapTimePicker?.hour = hour
                editNapTimePicker?.minute = (minute / TIME_PICKER_INTERVAL)
            } else {
                editNapTimePicker?.currentHour = hour
                editNapTimePicker?.currentMinute = (minute / TIME_PICKER_INTERVAL)
            }

        }
        chooseNap1Textview?.setTextColor(GeneralUtils.getColorWrapper(this@NapFragment, R.color.black))

        editNapTimePicker?.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener {
            override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
                println("Hours:: " + hourOfDay)
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    chooseNap1Textview?.text = (DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString()))!!.toUpperCase()
                    chooseNap1Textview?.setTextColor(ContextCompat.getColor(this@NapFragment!!,R.color.redText))
                    napTwoTextview?.setTextColor(Color.BLACK)
                    chooseNaptwoTextview?.setTextColor(Color.BLACK)
                    napTwoLayout?.isClickable = true
                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    chooseNap1Textview?.text = (DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString()))!!.toUpperCase()
                    chooseNap1Textview?.setTextColor(ContextCompat.getColor(this@NapFragment!!,R.color.redText))
                    napTwoTextview?.setTextColor(Color.BLACK)
                    chooseNaptwoTextview?.setTextColor(Color.BLACK)
                    napTwoLayout?.isClickable = true
                }

            }

        })

        cancelNapOneBttn?.setOnClickListener {
            chooseNap1Textview?.text = getString(R.string.choose)
            chooseNap1Textview?.setTextColor(GeneralUtils.getColorWrapper(this@NapFragment, R.color.black))
            napOneInterval = "00"
            napOneTime = "-1"
            mNapRepository!!.updateNapOne(napOneTime!!, weekId!!.toLong())
            napTwoTextview?.setTextColor(Color.GRAY)
            chooseNaptwoTextview?.setTextColor(Color.GRAY)
            napTwoLayout?.isClickable = false
            val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (weekId == 1) {
                var intent1 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent1 = PendingIntent.getBroadcast(this@NapFragment, Constants.SUNDAY_NAP1_REQUEST_CODE, intent1, 0)
                am.cancel(pendingIntent1)
                nm.cancel(Constants.SUNDAY_NAP1_NOTIFICATION_ID)

            }
            if (weekId == 2) {
                var intent1 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent1 = PendingIntent.getBroadcast(this@NapFragment, Constants.MONDAY_NAP1_REQUEST_CODE, intent1, 0)
                am.cancel(pendingIntent1)
                nm.cancel(Constants.MONDAY_NAP1_NOTIFICATION_ID)

            }
            if (weekId == 3) {
                var intent1 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent1 = PendingIntent.getBroadcast(this@NapFragment, Constants.TUESDAY_NAP1_REQUEST_CODE, intent1, 0)
                am.cancel(pendingIntent1)
                nm.cancel(Constants.TUESDAY_NAP1_NOTIFICATION_ID)

            }
            if (weekId == 4) {
                var intent1 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent1 = PendingIntent.getBroadcast(this@NapFragment, Constants.WEDNESDAY_NAP1_REQUEST_CODE, intent1, 0)
                am.cancel(pendingIntent1)
                nm.cancel(Constants.WEDNESDAY_NAP1_NOTIFICATION_ID)

            }
            if (weekId == 5) {
                var intent1 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent1 = PendingIntent.getBroadcast(this@NapFragment, Constants.THURSDAY_NAP1_REQUEST_CODE, intent1, 0)
                am.cancel(pendingIntent1)
                nm.cancel(Constants.THURSDAY_NAP1_NOTIFICATION_ID)

            }
            if (weekId == 6) {
                var intent1 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent1 = PendingIntent.getBroadcast(this@NapFragment, Constants.FRIDAY_NAP1_REQUEST_CODE, intent1, 0)
                am.cancel(pendingIntent1)
                nm.cancel(Constants.FRIDAY_NAP1_NOTIFICATION_ID)

            }
            if (weekId == 7) {
                var intent1 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent1 = PendingIntent.getBroadcast(this@NapFragment, Constants.SATURDAY_NAP1_REQUEST_CODE, intent1, 0)
                am.cancel(pendingIntent1)
                nm.cancel(Constants.SATURDAY_NAP1_NOTIFICATION_ID)

            }
        }

    }

    private fun napTwo() {
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minute = calendar.get(Calendar.MINUTE)
        var am_pm = calendar.get(Calendar.AM_PM)
        setTimePickerInterVal(editNapTwoTimePicker!!)
        // Configure displayed time
        if (minute % TIME_PICKER_INTERVAL !== 0) {
            val minuteFloor = minute + TIME_PICKER_INTERVAL - minute % TIME_PICKER_INTERVAL
            minute = minuteFloor + if (minute === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
            if (minute >= 60) {
                minute = minute % 60
                hour++
            }
            if (Build.VERSION.SDK_INT >= 23) {
                editNapTwoTimePicker?.hour = hour
                editNapTwoTimePicker?.minute = (minute / TIME_PICKER_INTERVAL)
            } else {
                editNapTwoTimePicker?.currentHour = hour
                editNapTwoTimePicker?.currentMinute = (minute / TIME_PICKER_INTERVAL)
            }

        }
        editNapTwoTimePicker?.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener {
            override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
                println("Hours:: " + hourOfDay)
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    chooseNaptwoTextview?.text = (DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString()))!!.toUpperCase()
                    chooseNaptwoTextview?.setTextColor(GeneralUtils.getColorWrapper(this@NapFragment, R.color.redText))
                    napThreeTextview?.setTextColor(Color.BLACK)
                    chooseNapThreeTextview?.setTextColor(Color.BLACK)
                    napThreeLayout?.isClickable = true


                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    chooseNaptwoTextview?.text = (DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString()))!!.toUpperCase()
                    chooseNaptwoTextview?.setTextColor(GeneralUtils.getColorWrapper(this@NapFragment, R.color.redText))
                    napThreeTextview?.setTextColor(Color.BLACK)
                    chooseNapThreeTextview?.setTextColor(Color.BLACK)
                    napThreeLayout?.isClickable = true


                }
                chooseNaptwoTextview?.setTextColor(GeneralUtils.getColorWrapper(this@NapFragment, R.color.redText))
            }

        })

        cancelTwoBttn?.setOnClickListener {
            chooseNaptwoTextview?.text = getString(R.string.choose)
            chooseNaptwoTextview?.setTextColor(GeneralUtils.getColorWrapper(this@NapFragment, R.color.black))
            napTwoTextview?.setTextColor(Color.BLACK)
            napTwoInterval = "00"
            napTwoTime = "-1"
            chooseNapThreeTextview?.text="Choose"
            napThreeTextview?.setTextColor(Color.GRAY)
            chooseNapThreeTextview?.setTextColor(Color.GRAY)
            napThreeLayout?.isClickable = false
            mNapRepository!!.updateNapTwo(napTwoTime!!, weekId!!.toLong())
            val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (weekId == 1) {

                var intent2 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent2 = PendingIntent.getBroadcast(this@NapFragment, Constants.SUNDAY_NAP1_REQUEST_CODE, intent2, 0)
                am.cancel(pendingIntent2)
                nm.cancel(Constants.SUNDAY_NAP2_NOTIFICATION_ID)

            }
            if (weekId == 2) {
                var intent2 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent2 = PendingIntent.getBroadcast(this@NapFragment, Constants.MONDAY_NAP1_REQUEST_CODE, intent2, 0)
                am.cancel(pendingIntent2)
                nm.cancel(Constants.MONDAY_NAP2_NOTIFICATION_ID)

            }
            if (weekId == 3) {
                var intent2 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent2 = PendingIntent.getBroadcast(this@NapFragment, Constants.TUESDAY_NAP1_REQUEST_CODE, intent2, 0)
                am.cancel(pendingIntent2)
                nm.cancel(Constants.TUESDAY_NAP2_NOTIFICATION_ID)

            }
            if (weekId == 4) {
                var intent2 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent2 = PendingIntent.getBroadcast(this@NapFragment, Constants.WEDNESDAY_NAP1_REQUEST_CODE, intent2, 0)
                am.cancel(pendingIntent2)
                nm.cancel(Constants.WEDNESDAY_NAP2_NOTIFICATION_ID)

            }
            if (weekId == 5) {
                var intent2 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent2 = PendingIntent.getBroadcast(this@NapFragment, Constants.THURSDAY_NAP1_REQUEST_CODE, intent2, 0)
                am.cancel(pendingIntent2)
                nm.cancel(Constants.THURSDAY_NAP2_NOTIFICATION_ID)

            }
            if (weekId == 6) {
                var intent2 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent2 = PendingIntent.getBroadcast(this@NapFragment, Constants.FRIDAY_NAP1_REQUEST_CODE, intent2, 0)
                am.cancel(pendingIntent2)
                nm.cancel(Constants.FRIDAY_NAP2_NOTIFICATION_ID)
            }
            if (weekId == 7) {
                var intent2 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent2 = PendingIntent.getBroadcast(this@NapFragment, Constants.SATURDAY_NAP1_REQUEST_CODE, intent2, 0)
                am.cancel(pendingIntent2)
                nm.cancel(Constants.SATURDAY_NAP2_NOTIFICATION_ID)

            }
        }
    }

    private fun napThree() {
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minute = calendar.get(Calendar.MINUTE)
        var am_pm = calendar.get(Calendar.AM_PM)
        setTimePickerInterVal(editNapThreeTimePicker!!)
        // Configure displayed time
        if (minute % TIME_PICKER_INTERVAL !== 0) {
            val minuteFloor = minute + TIME_PICKER_INTERVAL - minute % TIME_PICKER_INTERVAL
            minute = minuteFloor + if (minute === minuteFloor + 1) TIME_PICKER_INTERVAL else 0
            if (minute >= 60) {
                minute = minute % 60
                hour++
            }
            if (Build.VERSION.SDK_INT >= 23) {
                editNapThreeTimePicker?.hour = hour
                editNapThreeTimePicker?.minute = (minute / TIME_PICKER_INTERVAL)
            } else {
                editNapThreeTimePicker?.currentHour = hour
                editNapThreeTimePicker?.currentMinute = (minute / TIME_PICKER_INTERVAL)
            }
        }
        editNapThreeTimePicker?.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener {
            override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
                println("Hours:: " + hourOfDay)
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    chooseNapThreeTextview?.text = (DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString()))!!.toUpperCase()


                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    chooseNapThreeTextview?.text = (DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString()))!!.toUpperCase()


                }
                chooseNapThreeTextview?.setTextColor(GeneralUtils.getColorWrapper(this@NapFragment, R.color.redText))
            }

        })

        cancelThreeBttn?.setOnClickListener {
            chooseNapThreeTextview?.text = getString(R.string.choose)
            chooseNapThreeTextview?.setTextColor(GeneralUtils.getColorWrapper(this@NapFragment, R.color.black))
            napThreeTextview?.setTextColor(Color.BLACK)
            napThreeInterval = "00"
            napThreeTime = "-1"
            mNapRepository!!.updateNapThree(napThreeTime!!, weekId!!.toLong())
            val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (weekId == 1) {
                var intent3 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent3 = PendingIntent.getBroadcast(this@NapFragment, Constants.SUNDAY_NAP1_REQUEST_CODE, intent3, 0)
                am.cancel(pendingIntent3)
                nm.cancel(Constants.SUNDAY_NAP3_NOTIFICATION_ID)
            }
            if (weekId == 2) {
                var intent3 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent3 = PendingIntent.getBroadcast(this@NapFragment, Constants.MONDAY_NAP1_REQUEST_CODE, intent3, 0)
                am.cancel(pendingIntent3)
                nm.cancel(Constants.MONDAY_NAP3_NOTIFICATION_ID)
            }
            if (weekId == 3) {

                var intent3 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent3 = PendingIntent.getBroadcast(this@NapFragment, Constants.TUESDAY_NAP1_REQUEST_CODE, intent3, 0)
                am.cancel(pendingIntent3)
                nm.cancel(Constants.TUESDAY_NAP3_NOTIFICATION_ID)
            }
            if (weekId == 4) {

                var intent3 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent3 = PendingIntent.getBroadcast(this@NapFragment, Constants.WEDNESDAY_NAP1_REQUEST_CODE, intent3, 0)
                am.cancel(pendingIntent3)
                nm.cancel(Constants.WEDNESDAY_NAP3_NOTIFICATION_ID)
            }
            if (weekId == 5) {

                var intent3 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent3 = PendingIntent.getBroadcast(this@NapFragment, Constants.THURSDAY_NAP1_REQUEST_CODE, intent3, 0)
                am.cancel(pendingIntent3)
                nm.cancel(Constants.THURSDAY_NAP3_NOTIFICATION_ID)
            }
            if (weekId == 6) {

                var intent3 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent3 = PendingIntent.getBroadcast(this@NapFragment, Constants.FRIDAY_NAP1_REQUEST_CODE, intent3, 0)
                am.cancel(pendingIntent3)
                nm.cancel(Constants.FRIDAY_NAP3_NOTIFICATION_ID)
            }
            if (weekId == 7) {

                var intent3 = Intent(this@NapFragment, MainActivity::class.java)
                var pendingIntent3 = PendingIntent.getBroadcast(this@NapFragment, Constants.SATURDAY_NAP1_REQUEST_CODE, intent3, 0)
                am.cancel(pendingIntent3)
                nm.cancel(Constants.SATURDAY_NAP3_NOTIFICATION_ID)
            }
        }

    }

    fun layoutClick() {
        napOneLayout = findViewById(R.id.napOneLayout)
        if (!napOneTime.equals("-1", true)) {
            chooseNap1Textview?.text = napOneTime
            chooseNap1Textview?.setTextColor(Color.BLACK)
        }

        napOneLayout?.setOnClickListener {

            if (isOpenNapOneLayout!!) {
                napOneLayoutTimePicker!!.visibility = View.GONE
                isOpenNapOneLayout = false
                if (chooseNap1Textview!!.text.equals(getString(R.string.choose))){
                    chooseNap1Textview?.setTextColor(Color.BLACK)
                }else{
                    chooseNap1Textview?.setTextColor(ContextCompat.getColor(this@NapFragment!!,R.color.colorPrimaryDark))
                }

            } else {
                napOneLayoutTimePicker!!.visibility = View.VISIBLE
                napTwoLayoutTimePicker!!.visibility = View.GONE
                napThreeLayoutTimePicker!!.visibility = View.GONE
                isOpenNapOneLayout = true
                if(chooseNap1Textview?.text.toString().equals("Choose")){
                    chooseNap1Textview?.text = "11:00 AM"
                    chooseNap1Textview?.setTextColor(ContextCompat.getColor(this@NapFragment,R.color.redText))
                    chooseNap1Textview?.setTextColor(ContextCompat.getColor(this@NapFragment!!,R.color.redText))
                    napTwoTextview?.setTextColor(Color.BLACK)
                    chooseNaptwoTextview?.setTextColor(Color.BLACK)
                    napTwoLayout?.isClickable = true
                }

            }

        }

        napTwoLayout?.setOnClickListener {
            if (!chooseNap1Textview?.text!!.toString().equals("Choose", true)) {
                napTwoLayout?.isClickable = true

//                if (!napTwoTime.equals("-1", true)) {
//                    chooseNaptwoTextview?.text = napTwoTime
//                    chooseNaptwoTextview?.setTextColor(ContextCompat.getColor(this@NapFragment, R.color.redText))
//
//                }
                if (isOpenNapTwoLayout!!) {
                    napTwoLayoutTimePicker!!.visibility = View.GONE
                    isOpenNapTwoLayout = false
                    if (chooseNaptwoTextview!!.text.equals(getString(R.string.choose))){
                        chooseNaptwoTextview?.setTextColor(Color.BLACK)
                    }else{
                        chooseNaptwoTextview?.setTextColor(ContextCompat.getColor(this@NapFragment!!,R.color.colorPrimaryDark))
                    }

                } else {
                    napTwoLayoutTimePicker!!.visibility = View.VISIBLE
                    napOneLayoutTimePicker!!.visibility = View.GONE
                    napThreeLayoutTimePicker!!.visibility = View.GONE
                    isOpenNapTwoLayout = true
                    if(chooseNaptwoTextview?.text.toString().equals("Choose")){
                        chooseNaptwoTextview?.text = "2:00 PM"
                        chooseNaptwoTextview?.setTextColor(ContextCompat.getColor(this@NapFragment,R.color.redText))
                        chooseNaptwoTextview?.setTextColor(GeneralUtils.getColorWrapper(this@NapFragment, R.color.redText))
                        napThreeTextview?.setTextColor(Color.BLACK)
                        chooseNapThreeTextview?.setTextColor(Color.BLACK)
                        napThreeLayout?.isClickable = true
                    }
                }
            } else {
                napTwoLayout?.isClickable = false
            }

        }

        napThreeLayout?.setOnClickListener {
            if (!chooseNap1Textview?.text!!.toString().equals("Choose", true)) {
                napTwoLayout?.isClickable = true

//                if (!napThreeTime.equals("-1", true)) {
//                    chooseNapThreeTextview?.text = napThreeTime
//                    chooseNapThreeTextview?.setTextColor(ContextCompat.getColor(this@NapFragment, R.color.redText))
//                }
                if (isOpenNapThreelayout!!) {
                    napThreeLayoutTimePicker!!.visibility = View.GONE
                    isOpenNapThreelayout = false
                    if (chooseNapThreeTextview!!.text.equals(getString(R.string.choose))){
                        chooseNapThreeTextview?.setTextColor(Color.BLACK)
                    }else{
                        chooseNapThreeTextview?.setTextColor(ContextCompat.getColor(this@NapFragment!!,R.color.colorPrimaryDark))
                    }

                } else {
                    napThreeLayoutTimePicker!!.visibility = View.VISIBLE
                    napTwoLayoutTimePicker!!.visibility = View.GONE
                    napOneLayoutTimePicker!!.visibility = View.GONE
                    isOpenNapThreelayout = true
                    if(chooseNapThreeTextview?.text.toString().equals("Choose")){
                        chooseNapThreeTextview?.text = "4:00 PM"
                        chooseNapThreeTextview?.setTextColor(ContextCompat.getColor(this@NapFragment,R.color.redText))
                    }
                }
            } else {
                napTwoLayout?.isClickable = false
            }

        }
    }

    fun setTextFromDB(nap: Nap) {
        if (nap.napOne.equals("-1")) {
            if (Build.VERSION.SDK_INT >= 23) {
                editNapTimePicker?.hour = 11
                editNapTimePicker?.minute = (0 / TIME_PICKER_INTERVAL)

            } else {
                editNapTimePicker?.currentHour = 11
                editNapTimePicker?.currentMinute = (0 / TIME_PICKER_INTERVAL)

            }
            chooseNap1Textview?.text = "Choose"
            chooseNap1Textview?.setTextColor(Color.BLACK)
        } else {
            chooseNap1Textview?.text = nap.napOne
            chooseNap1Textview?.setTextColor(Color.BLACK)
            var tokensNapOne = DateTimeUtils.convert12HrsTo24Hrs(nap.napOne).split(":")
            var hoursNapOne = tokensNapOne[0].toInt()
            var minutesNapOne = tokensNapOne[1].split(" ")[0].toInt()

            if (Build.VERSION.SDK_INT >= 23) {
                editNapTimePicker?.hour = hoursNapOne
                editNapTimePicker?.minute = (minutesNapOne / TIME_PICKER_INTERVAL)
            } else {
                editNapTimePicker?.currentHour = hoursNapOne
                editNapTimePicker?.currentMinute = (minutesNapOne / TIME_PICKER_INTERVAL)
            }


        }

        if (nap.napTwo.equals("-1")) {
            if (Build.VERSION.SDK_INT >= 23) {
                editNapTwoTimePicker?.hour = 14
                editNapTwoTimePicker?.minute = (0 / TIME_PICKER_INTERVAL)
                val hour = String.format("%02d", editNapTwoTimePicker!!.hour)
                val minutes = String.format("%02d", editNapTwoTimePicker!!.minute * 5)
                napTwoTime = (DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString()))!!.toUpperCase()
            } else {
                editNapTwoTimePicker?.currentHour = 14
                editNapTwoTimePicker?.currentMinute = (0 / TIME_PICKER_INTERVAL)
                val hour = String.format("%02d", editNapTwoTimePicker!!.currentHour)
                val minutes = String.format("%02d", editNapTwoTimePicker!!.currentMinute * 5)
                napTwoTime = (DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString()))!!.toUpperCase()
            }
                chooseNaptwoTextview?.text = "Choose"
                napTwoTextview?.setTextColor(Color.GRAY)
                chooseNaptwoTextview?.setTextColor(Color.GRAY)



        } else {
            napTwoLayout?.isClickable = true
            chooseNaptwoTextview?.text = nap.napTwo
            chooseNaptwoTextview?.setTextColor(Color.BLACK)
            var tokensNapTwo = DateTimeUtils.convert12HrsTo24Hrs(nap.napTwo).split(":")
            var hoursNapTwo = tokensNapTwo[0].toInt()
            var minutesNapTwo = tokensNapTwo[1].split(" ")[0].toInt()

            if (Build.VERSION.SDK_INT >= 23) {
                editNapTwoTimePicker?.hour = hoursNapTwo
                editNapTwoTimePicker?.minute = (minutesNapTwo / TIME_PICKER_INTERVAL)

            } else {
                editNapTwoTimePicker?.currentHour = hoursNapTwo
                editNapTwoTimePicker?.currentMinute = (minutesNapTwo / TIME_PICKER_INTERVAL)
            }

        }

        if (nap.napThree.equals("-1")) {
            if (Build.VERSION.SDK_INT >= 23) {
                editNapThreeTimePicker?.hour = 16
                editNapThreeTimePicker?.minute = (0 / TIME_PICKER_INTERVAL)
                val hour = String.format("%02d", editNapThreeTimePicker!!.hour)
                val minutes = String.format("%02d", editNapThreeTimePicker!!.minute * 5)
                napThreeTime = (DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString()))!!.toUpperCase()
            } else {
                editNapThreeTimePicker?.currentHour = 16
                editNapThreeTimePicker?.currentMinute = (0 / TIME_PICKER_INTERVAL)
                val hour = String.format("%02d", editNapThreeTimePicker!!.currentHour)
                val minutes = String.format("%02d", editNapThreeTimePicker!!.currentMinute * 5)
                napThreeTime = (DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString()))!!.toUpperCase()
            }
                chooseNapThreeTextview?.text = "Choose"
                napThreeTextview?.setTextColor(Color.GRAY)
                chooseNapThreeTextview?.setTextColor(Color.GRAY)


        } else {
            napThreeLayout?.isClickable = true
            chooseNapThreeTextview?.text = nap.napThree
            chooseNapThreeTextview?.setTextColor(Color.BLACK)
            var tokensNapThree = DateTimeUtils.convert12HrsTo24Hrs(nap.napThree).split(":")
            var hoursNapThree = tokensNapThree[0].toInt()
            var minutesNapThree = tokensNapThree[1].split(" ")[0].toInt()

            if (Build.VERSION.SDK_INT >= 23) {
                editNapThreeTimePicker?.hour = hoursNapThree
                editNapThreeTimePicker?.minute = (minutesNapThree / TIME_PICKER_INTERVAL)
            } else {
                editNapThreeTimePicker?.currentHour = hoursNapThree
                editNapThreeTimePicker?.currentMinute = (minutesNapThree / TIME_PICKER_INTERVAL)
            }
        }


    }


    override fun onBackPressed() {
        napOneTime = if (chooseNap1Textview!!.text.equals(getString(R.string.choose))) "-1" else chooseNap1Textview!!.text.toString()
        napTwoTime = if (chooseNaptwoTextview!!.text.equals(getString(R.string.choose))) "-1" else chooseNaptwoTextview!!.text.toString()
        napThreeTime = if (chooseNapThreeTextview!!.text.equals(getString(R.string.choose))) "-1" else chooseNapThreeTextview!!.text.toString()
        if (napOneTime.equals("-1") && napTwoTime.equals("-1") && napThreeTime.equals("-1")) {
            finish()
        } else {
            var naps=NapRepository(this@NapFragment).getNapById(weekId!!)
            if(naps!=null){
                var nap = Nap(id = weekId!!.toLong(), napOne = napOneTime!!, napTwo = napTwoTime!!, napThree = napThreeTime!!,
                        reminderWeekNo = weekId!!, napOneInterval = napOneInterval!!,
                        napTwoInterval = napTwoInterval!!, napThreeInterval = napThreeInterval!!)
                mNapRepository!!.updateNap(nap)
                finish()
            }else{
                var nap = Nap(id = weekId!!.toLong(), napOne = napOneTime!!, napTwo = napTwoTime!!, napThree = napThreeTime!!,
                        reminderWeekNo = weekId!!, napOneInterval = napOneInterval!!,
                        napTwoInterval = napTwoInterval!!, napThreeInterval = napThreeInterval!!)
                mNapRepository!!.insertNap(nap)
                finish()
            }
        }
        super.onBackPressed()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                napOneTime = if (chooseNap1Textview!!.text.equals(getString(R.string.choose))) "-1" else chooseNap1Textview!!.text.toString()
                napTwoTime = if (chooseNaptwoTextview!!.text.equals(getString(R.string.choose))) "-1" else chooseNaptwoTextview!!.text.toString()
                napThreeTime = if (chooseNapThreeTextview!!.text.equals(getString(R.string.choose))) "-1" else chooseNapThreeTextview!!.text.toString()
                if (napOneTime.equals("-1") && napTwoTime.equals("-1") && napThreeTime.equals("-1")) {
                    finish()
                } else {
                    var naps=NapRepository(this@NapFragment).getNapById(weekId!!)
                    if(naps!=null){
                        var nap = Nap(id = weekId!!.toLong(), napOne = napOneTime!!, napTwo = napTwoTime!!, napThree = napThreeTime!!,
                                reminderWeekNo = weekId!!, napOneInterval = napOneInterval!!,
                                napTwoInterval = napTwoInterval!!, napThreeInterval = napThreeInterval!!)
                        mNapRepository!!.updateNap(nap)
                        finish()
                    }else{
                        var nap = Nap(id = weekId!!.toLong(), napOne = napOneTime!!, napTwo = napTwoTime!!, napThree = napThreeTime!!,
                                reminderWeekNo = weekId!!, napOneInterval = napOneInterval!!,
                                napTwoInterval = napTwoInterval!!, napThreeInterval = napThreeInterval!!)
                        mNapRepository!!.insertNap(nap)
                        finish()
                    }

                }

            }
        }
        return super.onOptionsItemSelected(item)
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


    private fun resetNaps(){
        chooseNap1Textview?.text = getString(R.string.choose)
        chooseNap1Textview?.setTextColor(GeneralUtils.getColorWrapper(this@NapFragment, R.color.black))
        napOneInterval = "00"

        chooseNaptwoTextview?.text = getString(R.string.choose)
        napTwoTextview?.setTextColor(Color.GRAY)
        chooseNaptwoTextview?.setTextColor(Color.GRAY)
        napTwoLayout?.isClickable = false
        napTwoInterval = "00"
        chooseNapThreeTextview?.text="Choose"
        napThreeTextview?.setTextColor(Color.GRAY)
        chooseNapThreeTextview?.setTextColor(Color.GRAY)
        napThreeLayout?.isClickable = false
        napThreeInterval = "00"

    }
}