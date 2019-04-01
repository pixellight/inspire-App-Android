package myapp.net.inspire.plan

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.fragment_plansetup_six.*
import kotlinx.android.synthetic.main.nap_one_layout.*
import kotlinx.android.synthetic.main.nap_three_layout.*
import kotlinx.android.synthetic.main.nap_two_layout.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.nap.adapter.NapOneAdapter
import myapp.net.inspire.utils.*
import java.util.*


/**
 * Created by QPay on 2/13/2019.
 */
class PlanSetupSixthFragment : Fragment() {

    companion object {
        val TAG = "PlanSetupSixthFragment"
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
    private val TIME_PICKER_INTERVAL = 5
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_plansetup_six, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var title = (activity as AppCompatActivity).findViewById<TextView>(R.id.title)
        var reset = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        var cancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
        cancel.text = "X"
        cancel.textSize = 24f
        reset.text = getString(R.string.reset)
        title.text = "PLAN SETUP [6/10]"

        cancel.setOnClickListener {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }

        reset.setOnClickListener {
            //            startActivity(Intent(activity!!, PlanSetupFristActivity::class.java))
//            activity!!.finishAffinity()
//            dialogReset()
            resetNaps()
        }

        if (!AppCache.napOne.isNullOrEmpty() && !AppCache.napOne.equals("-1")) {
            chooseNap1Textview?.text = AppCache.napOne
            chooseNap1Textview?.setTextColor(Color.BLACK)
        }

        if (!AppCache.napOne.isNullOrEmpty() && !AppCache.napTwo.equals("-1")) {
            chooseNaptwoTextview?.text = AppCache.napTwo
            chooseNaptwoTextview?.setTextColor(Color.BLACK)
            napTwoLayout?.isClickable=true
        }

        if (!AppCache.napOne.isNullOrEmpty() && !AppCache.napThree.equals("-1")) {
            chooseNapThreeTextview?.text = AppCache.napThree
            chooseNapThreeTextview?.setTextColor(Color.BLACK)
            napThreeLayout?.isClickable=true
        }


        try{
            napOne()
            napTwo()
            napThree()


        }catch (e:Exception){
            e.printStackTrace()
        }

        nextSixButton?.setOnClickListener {
            println("NapOneInterval:: " + napOneInterval)
            println("NapTwoInterval:: " + napTwoInterval)
            println("NapThreeInterval:: " + napThreeInterval)
            napOneTime = if (chooseNap1Textview!!.text.equals(getString(R.string.choose))) "-1" else chooseNap1Textview!!.text.toString()
            napTwoTime = if (chooseNaptwoTextview!!.text.equals(getString(R.string.choose))) "-1" else chooseNaptwoTextview!!.text.toString()
            napThreeTime = if (chooseNapThreeTextview!!.text.equals(getString(R.string.choose))) "-1" else chooseNapThreeTextview!!.text.toString()

            AppCache.napOne = napOneTime
            AppCache.napTwo = napTwoTime
            AppCache.napThree = napThreeTime

            Repository().setNapOne(activity!!, napOneTime!!)
            Repository().setNapTwo(activity!!, napTwoTime!!)
            Repository().setNapThree(activity!!, napThreeTime!!)
            Repository().setNapOneInterval(activity!!, napOneInterval!!)
            Repository().setNapTwoInterval(activity!!, napTwoInterval!!)
            Repository().setNapThreeInterval(activity!!, napThreeInterval!!)
            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, PlanSetupSevenFragment(), R.id.container, TAG)
        }

        previousSixButton?.setOnClickListener {
            LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupFifthFragment.TAG)
        }

        napOneLayout = view!!.findViewById(R.id.napOneLayout)

        napOneLayout?.setOnClickListener {
            if (isOpenNapOneLayout!!) {
                napOneLayoutTimePicker!!.visibility = View.GONE
                isOpenNapOneLayout = false
                if (chooseNap1Textview!!.text.equals(getString(R.string.choose))){
                    chooseNap1Textview?.setTextColor(Color.BLACK)
                }else{
                    chooseNap1Textview?.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimaryDark))
                }
            } else {
                napOneLayoutTimePicker!!.visibility = View.VISIBLE
                napTwoLayoutTimePicker!!.visibility = View.GONE
                napThreeLayoutTimePicker!!.visibility = View.GONE
                isOpenNapOneLayout = true
                if(chooseNap1Textview?.text.toString().equals("Choose")){
                    chooseNap1Textview?.text = "11:00 AM"
                    chooseNap1Textview?.setTextColor(ContextCompat.getColor(activity,R.color.redText))
                    chooseNap1Textview?.setTextColor(ContextCompat.getColor(activity!!,R.color.redText))
                    napTwoTextview?.setTextColor(Color.BLACK)
                    chooseNaptwoTextview?.setTextColor(Color.BLACK)
                    napTwoLayout?.isClickable = true
                }


            }

        }

        napTwoLayout?.setOnClickListener {
            if (isOpenNapTwoLayout!!) {
                napTwoLayoutTimePicker!!.visibility = View.GONE
                isOpenNapTwoLayout = false
                if (chooseNaptwoTextview!!.text.equals(getString(R.string.choose))){
                    chooseNaptwoTextview?.setTextColor(Color.BLACK)
                }else{
                    chooseNaptwoTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimaryDark))
                }
            } else {
                napTwoLayoutTimePicker!!.visibility = View.VISIBLE
                napOneLayoutTimePicker!!.visibility = View.GONE
                napThreeLayoutTimePicker!!.visibility = View.GONE
                isOpenNapTwoLayout = true
                if(chooseNaptwoTextview?.text.toString().equals("Choose")){
                    chooseNaptwoTextview?.text = "2:00 PM"
                    chooseNaptwoTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.redText))
                    chooseNaptwoTextview?.setTextColor(GeneralUtils.getColorWrapper(activity!!, R.color.redText))
                    napThreeTextview?.setTextColor(Color.BLACK)
                    chooseNapThreeTextview?.setTextColor(Color.BLACK)
                    napThreeLayout?.isClickable = true
                }



            }
        }

        napThreeLayout?.setOnClickListener {
            if (isOpenNapThreelayout!!) {
                napThreeLayoutTimePicker!!.visibility = View.GONE
                isOpenNapThreelayout = false

                if (chooseNapThreeTextview!!.text.equals(getString(R.string.choose))){
                    chooseNapThreeTextview?.setTextColor(Color.BLACK)
                }else{
                    chooseNapThreeTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimaryDark))
                }
            } else {
                napThreeLayoutTimePicker!!.visibility = View.VISIBLE
                napTwoLayoutTimePicker!!.visibility = View.GONE
                napOneLayoutTimePicker!!.visibility = View.GONE
                isOpenNapThreelayout = true
                if(chooseNapThreeTextview?.text.toString().equals("Choose")){
                    chooseNapThreeTextview?.text = "4:00 PM"
                    chooseNapThreeTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.redText))
                }

            }
        }

        loadMinutesData()

        if(!AppCache.napOne.equals("-1")){
            napTwoTextview?.setTextColor(Color.BLACK)
            chooseNaptwoTextview?.setTextColor(Color.BLACK)
            napThreeTextview?.setTextColor(Color.BLACK)
            chooseNapThreeTextview?.setTextColor(Color.BLACK)
            napTwoLayout?.isClickable = true
            napThreeLayout?.isClickable = true
        }else{
            napTwoTextview?.setTextColor(Color.GRAY)
            chooseNaptwoTextview?.setTextColor(Color.GRAY)
            napThreeTextview?.setTextColor(Color.GRAY)
            chooseNapThreeTextview?.setTextColor(Color.GRAY)
            napTwoLayout?.isClickable = false
            napThreeLayout?.isClickable = false
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

    private fun loadMinutesData() {
        var n = 55
        var i = 5
        while (i <= n) {
            minutes.add(i)
            i = i + 5
        }

        //set padding to particular items will appear in the middle of the screen
        val padding: Int = ScreenUtils.getScreenWidth(activity) / 2 - ScreenUtils.dpToPx(activity, 40)
        //adapter one
        napOneRecyclerview.setPadding(padding, 0, padding, 0)
        //setting layout manager
        napOneRecyclerview.layoutManager = SliderLayoutManager(activity).apply {
            scrollToPosition(4)
            napOneInterval = minutes.get(4).toString()
            callback = object : SliderLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(layoutPosition: Int) {
                    println("Selected Item:: " + minutes.get(layoutPosition))
                    napOneInterval = layoutPosition!!.toString()

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
        napTwoRecyclerview.layoutManager = SliderLayoutManager(activity).apply {
            scrollToPosition(4)
            napTwoInterval = minutes.get(4).toString()
            callback = object : SliderLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(layoutPosition: Int) {
                    println("Selected Item:: " + minutes.get(layoutPosition))
                    napTwoInterval = layoutPosition!!.toString()
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
        napThreeRecyclerview.layoutManager = SliderLayoutManager(activity).apply {
            scrollToPosition(4)
            napThreeInterval = minutes.get(4).toString()
            callback = object : SliderLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(layoutPosition: Int) {
                    println("Selected Item:: " + minutes.get(layoutPosition))
                    napThreeInterval = layoutPosition!!.toString()
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
            if (!AppCache.napOne.isNullOrEmpty() && !AppCache.napOne.equals("-1")) {
                var saveTime = AppCache.napOne
                var hr_mins = DateTimeUtils.convert12HrsTo24Hrs(saveTime!!)
                var tokens = hr_mins.split(":")
                var hrs = tokens[0]
                var mins = tokens[1].split(" ")[0]
                chooseNap1Textview?.text = saveTime
                if (Build.VERSION.SDK_INT >= 23) {
                    editNapTimePicker?.hour = hrs.toInt()
                    editNapTimePicker?.minute = mins.toInt()/5
                } else {
                    editNapTimePicker?.currentHour = hrs.toInt()
                    editNapTimePicker?.currentMinute = mins.toInt()/5
                }
            } else {
                if (Build.VERSION.SDK_INT >= 23) {
                    editNapTimePicker?.hour = 11
                    editNapTimePicker?.minute = (0 / TIME_PICKER_INTERVAL)
                } else {
                    editNapTimePicker?.currentHour = 11
                    editNapTimePicker?.currentMinute = (0 / TIME_PICKER_INTERVAL)
                }
            }


        }
//        chooseNap1Textview?.text = DateTimeUtils.get24HrsTo12Hrs(hours.toString() + ":" + minutes.toString()) + " " + if (am_pm == 1) "PM" else "AM"
        editNapTimePicker?.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener {
            override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
                println("Hours:: " + hourOfDay)
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    chooseNap1Textview?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)!!.toUpperCase()
                    chooseNap1Textview?.setTextColor(ContextCompat.getColor(activity!!,R.color.redText))
                    napTwoTextview?.setTextColor(Color.BLACK)
                    chooseNaptwoTextview?.setTextColor(Color.BLACK)
                    napTwoLayout?.isClickable = true


                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    chooseNap1Textview?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour + ":" + minutes)!!.toUpperCase()
                    chooseNap1Textview?.setTextColor(ContextCompat.getColor(activity!!,R.color.redText))
                    napTwoTextview?.setTextColor(Color.BLACK)
                    chooseNaptwoTextview?.setTextColor(Color.BLACK)
                    napTwoLayout?.isClickable = true

                }

//                napThreeTextview?.setTextColor(Color.BLACK)
//                chooseNapThreeTextview?.setTextColor(Color.BLACK)
//                napThreeLayout?.isClickable = true

            }

        })

        cancelNapOneBttn?.setOnClickListener {
            chooseNap1Textview?.text = getString(R.string.choose)
            chooseNap1Textview?.setTextColor(GeneralUtils.getColorWrapper(activity, R.color.black))
            napOneInterval = "00"
            napTwoTextview?.setTextColor(Color.GRAY)
            chooseNaptwoTextview?.setTextColor(Color.GRAY)
            napTwoLayout?.isClickable = false


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

                if (!AppCache.napTwo.isNullOrEmpty() && !AppCache.napTwo.equals("-1")) {
                    var saveTime = AppCache.napTwo
                    chooseNaptwoTextview?.text = saveTime
                    var hr_mins = DateTimeUtils.convert12HrsTo24Hrs(saveTime!!)
                    var tokens = hr_mins.split(":")
                    var hrs = tokens[0]
                    var mins = tokens[1].split(" ")[0]
                    if (Build.VERSION.SDK_INT >= 23) {
                        editNapTwoTimePicker?.hour = hrs.toInt()
                        editNapTwoTimePicker?.minute = mins.toInt()/5
                    } else {
                        editNapTwoTimePicker?.currentHour = hrs.toInt()
                        editNapTwoTimePicker?.currentMinute = mins.toInt()
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= 23) {
                        editNapTwoTimePicker?.hour = 14
                        editNapTwoTimePicker?.minute = (0 / TIME_PICKER_INTERVAL)
                    } else {
                        editNapTwoTimePicker?.currentHour = 14
                        editNapTwoTimePicker?.currentMinute = (0 / TIME_PICKER_INTERVAL)
                    }
                }


            }
//        chooseNaptwoTextview?.text = DateTimeUtils.get24HrsTo12Hrs(hours.toString() + ":" + minutes.toString()) + " " + if (am_pm == 1) "PM" else "AM"
//            chooseNaptwoTextview?.setTextColor(GeneralUtils.getColorWrapper(activity, R.color.redText))
            editNapTwoTimePicker?.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener {
                override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    println("Hours:: " + hourOfDay)

                    if (Build.VERSION.SDK_INT >= 23) {
                        val hour = String.format("%02d", view!!.hour)
                        val minutes = String.format("%02d", view!!.minute * 5)
                        chooseNaptwoTextview?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString())!!.toUpperCase()
                        chooseNaptwoTextview?.setTextColor(GeneralUtils.getColorWrapper(activity, R.color.redText))
                        napThreeTextview?.setTextColor(Color.BLACK)
                        chooseNapThreeTextview?.setTextColor(Color.BLACK)
                        napThreeLayout?.isClickable = true

                        if(DateTimeUtils.calculateTimeInMills(chooseNap1Textview.text.toString(),chooseNaptwoTextview?.text.toString())){

                        }else{
                            Toast.makeText(activity!!,"Nap 2 cannot be within 1 hour of nap 1 end",Toast.LENGTH_LONG).show()
                        }


                    } else {
                        val hour = String.format("%02d", view!!.currentHour)
                        val minutes = String.format("%02d", view!!.currentMinute * 5)
                        chooseNaptwoTextview?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString())!!.toUpperCase()
                        chooseNaptwoTextview?.setTextColor(GeneralUtils.getColorWrapper(activity, R.color.redText))
                        napThreeTextview?.setTextColor(Color.BLACK)
                        chooseNapThreeTextview?.setTextColor(Color.BLACK)
                        napThreeLayout?.isClickable = true

                        if(DateTimeUtils.calculateTimeInMills(chooseNap1Textview.text.toString(),chooseNaptwoTextview?.text.toString())){

                        }else{
                            Toast.makeText(activity!!,"Nap 2 cannot be within 1 hour of nap 1 end",Toast.LENGTH_LONG).show()
                        }
                    }

                }

            })

            cancelTwoBttn?.setOnClickListener {
                chooseNaptwoTextview?.text = getString(R.string.choose)
                chooseNaptwoTextview?.setTextColor(GeneralUtils.getColorWrapper(activity, R.color.black))
                napTwoInterval = "00"
//                chooseNaptwoTextview?.text="Choose"

                chooseNapThreeTextview?.text="Choose"
                napThreeTextview?.setTextColor(Color.GRAY)
                chooseNapThreeTextview?.setTextColor(Color.GRAY)
                napThreeLayout?.isClickable = false
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
            if (!AppCache.napThree.isNullOrEmpty()  && !AppCache.napThree.equals("-1")) {
                var saveTime = AppCache.napThree
                chooseNapThreeTextview?.text = saveTime
                var hr_mins = DateTimeUtils.convert12HrsTo24Hrs(saveTime!!)
                var tokens = hr_mins.split(":")
                var hrs = tokens[0]
                var mins = tokens[1].split(" ")[0]
                if (Build.VERSION.SDK_INT >= 23) {
                    editNapThreeTimePicker?.hour = hrs.toInt()
                    editNapThreeTimePicker?.minute = mins.toInt()/5
                } else {
                    editNapThreeTimePicker?.currentHour = hrs.toInt()
                    editNapThreeTimePicker?.currentMinute = mins.toInt()/5
                }
            } else {
                if (Build.VERSION.SDK_INT >= 23) {
                    editNapThreeTimePicker?.hour = 16
                    editNapThreeTimePicker?.minute = (0 / TIME_PICKER_INTERVAL)
                } else {
                    editNapThreeTimePicker?.currentHour = 16
                    editNapThreeTimePicker?.currentMinute = (0 / TIME_PICKER_INTERVAL)
                }
            }

        }
//        chooseNapThreeTextview?.text = DateTimeUtils.get24HrsTo12Hrs(hours.toString() + ":" + minutes.toString()) + " " + if (am_pm == 1) "PM" else "AM"
//        chooseNapThreeTextview?.setTextColor(GeneralUtils.getColorWrapper(activity, R.color.redText))
        editNapThreeTimePicker?.setOnTimeChangedListener(object : TimePicker.OnTimeChangedListener {
            override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
                println("Hours:: " + hourOfDay)
                if (Build.VERSION.SDK_INT >= 23) {
                    val hour = String.format("%02d", view!!.hour)
                    val minutes = String.format("%02d", view!!.minute * 5)
                    chooseNapThreeTextview?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString())!!.toUpperCase()
                    chooseNapThreeTextview?.setTextColor(GeneralUtils.getColorWrapper(activity, R.color.redText))

                    if(DateTimeUtils.calculateTimeInMills(chooseNaptwoTextview.text.toString(),chooseNapThreeTextview?.text.toString())){

                    }else{
                        Toast.makeText(activity!!,"Nap 3 cannot be within 1 hour of nap 2 end",Toast.LENGTH_LONG).show()
                    }

                } else {
                    val hour = String.format("%02d", view!!.currentHour)
                    val minutes = String.format("%02d", view!!.currentMinute * 5)
                    chooseNapThreeTextview?.text = DateTimeUtils.convert24hrsTo12HrsNap(hour.toString() + ":" + minutes.toString())!!.toUpperCase()
                    chooseNapThreeTextview?.setTextColor(GeneralUtils.getColorWrapper(activity, R.color.redText))

                    if(DateTimeUtils.calculateTimeInMills(chooseNaptwoTextview.text.toString(),chooseNapThreeTextview?.text.toString())){

                    }else{
                        Toast.makeText(activity!!,"Nap 3 cannot be within 1 hour of nap 2 end",Toast.LENGTH_LONG).show()
                    }

                }


            }

        })

        cancelThreeBttn?.setOnClickListener {
            chooseNapThreeTextview?.text = getString(R.string.choose)
            chooseNapThreeTextview?.setTextColor(GeneralUtils.getColorWrapper(activity, R.color.black))
            napThreeInterval = "00"

        }

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
        chooseNap1Textview?.setTextColor(GeneralUtils.getColorWrapper(activity, R.color.black))
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

        AppCache.napOne = "-1"
        AppCache.napTwo = "-1"
        AppCache.napThree = "-1"

    }

}