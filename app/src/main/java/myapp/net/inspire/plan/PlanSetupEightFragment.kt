package myapp.net.inspire.plan

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_plansetup_eight.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.AppCache
import myapp.net.inspire.utils.GeneralUtils
import myapp.net.inspire.utils.LoadFragment
import myapp.net.inspire.utils.MediaPlayerUtils

/**
 * Created by QPay on 2/13/2019.
 */
class PlanSetupEightFragment : Fragment(), View.OnClickListener {

    companion object {
        val TAG = "PlanSetupEightFragment"
    }

    private var isDoseOne: Boolean = false
    private var isDoseTwo: Boolean = false
    private var isWake: Boolean = false
    private var isNapStart: Boolean = false
    private var isNapEnd: Boolean = false

    private val sounds = arrayOf("Alarm Clock", "Soft Alarm", "Heartbeat", "Sunrise", "Jingle", "Inspire", "Marimba", "Guitar")
    private val soundDrawable = arrayOf(R.raw.alarm_clock, R.raw.soft_alarm_clock, R.raw.heartbeat, R.raw.sunrise, R.raw.jingle, R.raw.inspire, R.raw.marimba, R.raw.guitar)
    private var doseOneSound: String? = null
    private var doseTwoSound: String? = null
    private var wakeSound: String? = null
    private var napStartSound: String? = null
    private var napEndSound: String? = null
    private var doseOneSoundDrawable: String? = "0"
    private var doseTwoSoundDrawable: String? = "0"
    private var wakeSoundDrawable: String? = "0"
    private var napStartSoundDrawable: String? = "0"
    private var napEndSoundDrawable: String? = "0"


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_plansetup_eight, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var title = (activity as AppCompatActivity).findViewById<TextView>(R.id.title)
        var reset = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
        var cancel = (activity as AppCompatActivity).findViewById<TextView>(R.id.titleLeft)
        cancel.text = "X"
        cancel.textSize = 24f
        reset.text = getString(R.string.reset)
        title.text = "PLAN SETUP [8/10]"

        cancel.setOnClickListener {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }

        reset.setOnClickListener {
            resetAlarmSounds()
//            dialogReset()
//            startActivity(Intent(activity!!, PlanSetupFristActivity::class.java))
//            activity!!.finishAffinity()
        }



        doseOneLayout?.setOnClickListener(this)
        doseTwoLayout?.setOnClickListener(this)
        wakeLayout?.setOnClickListener(this)
        napeStartLayout?.setOnClickListener(this)
        napEndLayout?.setOnClickListener(this)
        setupCacheData()
        doseOnePicker()
        doseTwoPicker()
        wakePicker()
        napStartPicker()
        napEndPicker()




        nextEightButton?.setOnClickListener {
            Repository().setDoseOneAlarm(activity!!, doseOneSoundDrawable!!)
            Repository().setDoseTwoAlarm(activity!!, doseTwoSoundDrawable!!)
            Repository().setWakeAlarm(activity!!, wakeSoundDrawable!!)
            Repository().setNapStartAlarm(activity!!, napStartSoundDrawable!!)
            Repository().setNapEndAlarm(activity!!, napEndSoundDrawable!!)
            AppCache.dose1 = doseOneSoundDrawable!!.toInt()
            AppCache.dose2 = doseTwoSoundDrawable!!.toInt()
            AppCache.wake = wakeSoundDrawable!!.toInt()
            AppCache.napStart = napStartSoundDrawable!!.toInt()
            AppCache.napEnd = napEndSoundDrawable!!.toInt()
            MediaPlayerUtils.stopPlaying()
            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, PlanSetupNineFragment(), R.id.container, TAG)
        }

        previousEightButton?.setOnClickListener {
            MediaPlayerUtils.stopPlaying()
            LoadFragment.popupBackStack(activity!!.supportFragmentManager, PlanSetupSevenFragment.TAG)
        }

    }

    private fun setupCacheData() {
        if (AppCache.wake >= 0) {
            wakeAlarmTextview?.text = sounds.get(AppCache.wake)
        }

        if (AppCache.dose1 >= 0) {
            doseOneAlarmTextview?.text = sounds.get(AppCache.dose1)
        }

        if (AppCache.dose2 >= 0) {
            doseTwoAlarmTextview?.text = sounds.get(AppCache.dose2)
        }

        if (AppCache.napStart >= 0) {
            napeStartAlarmTextview?.text = sounds.get(AppCache.napStart)
        }

        if (AppCache.napEnd >= 0) {
            napEndAlarmTextview?.text = sounds.get(AppCache.napEnd)
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

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.doseOneLayout -> {
                if (isDoseOne) {
                    isDoseOne = false
                    doseOnePicker?.visibility = View.GONE
                    MediaPlayerUtils.stopPlaying()
                    doseOneAlarmTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimaryDark))
                } else {
                    isDoseOne = true
                    doseOnePicker?.visibility = View.VISIBLE
                    doseTwoPicker?.visibility = View.GONE
                    wakePicker?.visibility = View.GONE
                    napStartPicker?.visibility = View.GONE
                    napEndPicker?.visibility = View.GONE
                    MediaPlayerUtils.stopPlaying()
                }

            }
            R.id.doseTwoLayout -> {
                if (isDoseTwo) {
                    isDoseTwo = false
                    doseTwoPicker?.visibility = View.GONE
                    MediaPlayerUtils.stopPlaying()
                    doseTwoAlarmTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimaryDark))
                } else {
                    isDoseTwo = true
                    doseTwoPicker?.visibility = View.VISIBLE
                    doseOnePicker?.visibility = View.GONE
                    wakePicker?.visibility = View.GONE
                    napStartPicker?.visibility = View.GONE
                    napEndPicker?.visibility = View.GONE
                    MediaPlayerUtils.stopPlaying()
                }
            }
            R.id.wakeLayout -> {
                if (isWake) {
                    isWake = false
                    wakePicker?.visibility = View.GONE
                    MediaPlayerUtils.stopPlaying()
                    wakeAlarmTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimaryDark))
                } else {
                    isWake = true
                    wakePicker?.visibility = View.VISIBLE
                    doseTwoPicker?.visibility = View.GONE
                    doseOnePicker?.visibility = View.GONE
                    napStartPicker?.visibility = View.GONE
                    napEndPicker?.visibility = View.GONE
                    MediaPlayerUtils.stopPlaying()
                }
            }

            R.id.napeStartLayout -> {
                if (isNapStart) {
                    isNapStart = false
                    napStartPicker?.visibility = View.GONE
                    MediaPlayerUtils.stopPlaying()
                    napeStartAlarmTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimaryDark))
                } else {
                    isNapStart = true
                    napStartPicker?.visibility = View.VISIBLE
                    wakePicker?.visibility = View.GONE
                    doseTwoPicker?.visibility = View.GONE
                    doseOnePicker?.visibility = View.GONE
                    napEndPicker?.visibility = View.GONE
                    MediaPlayerUtils.stopPlaying()
                }
            }

            R.id.napEndLayout -> {
                if (isNapEnd) {
                    isNapEnd = false
                    napEndPicker?.visibility = View.GONE
                    MediaPlayerUtils.stopPlaying()
                    napEndAlarmTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.colorPrimaryDark))
                } else {
                    isNapEnd = true
                    napEndPicker?.visibility = View.VISIBLE
                    napStartPicker?.visibility = View.GONE
                    wakePicker?.visibility = View.GONE
                    doseTwoPicker?.visibility = View.GONE
                    doseOnePicker?.visibility = View.GONE
                    MediaPlayerUtils.stopPlaying()
                }
            }
        }
    }


    private fun doseOnePicker() {
        doseOnePicker?.minValue = 0
        doseOnePicker?.maxValue = sounds.size - 1
        doseOnePicker?.displayedValues = sounds
        doseOnePicker?.wrapSelectorWheel = true
        if (AppCache.dose1 > 0) {
            doseOnePicker?.value = AppCache.dose1

        } else {
            doseOnePicker?.value = 0

        }
        doseOnePicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            doseOneSound = sounds[newVal]
            doseOneAlarmTextview?.text = doseOneSound
            doseOneAlarmTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.redText))
            doseOneSoundDrawable = newVal.toString()
            MediaPlayerUtils.stopPlaying()
            MediaPlayerUtils.playWaterSound(activity!!, soundDrawable[newVal].toString())
        }
    }

    private fun doseTwoPicker() {
        doseTwoPicker?.minValue = 0
        doseTwoPicker?.maxValue = sounds.size - 1
        doseTwoPicker?.displayedValues = sounds
        doseTwoPicker?.wrapSelectorWheel = true
        if (AppCache.dose2 > 0) {
            doseTwoPicker?.value = AppCache.dose2

        } else {
            doseTwoPicker?.value = 0

        }
        doseTwoPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            doseTwoSound = sounds[newVal]
            doseTwoAlarmTextview?.text = doseTwoSound
            doseTwoAlarmTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.redText))
            doseTwoSoundDrawable = newVal.toString()
            MediaPlayerUtils.stopPlaying()
            MediaPlayerUtils.playWaterSound(activity!!, soundDrawable[newVal].toString())
        }
    }

    private fun wakePicker() {
        wakePicker?.minValue = 0
        wakePicker?.maxValue = sounds.size - 1
        wakePicker?.displayedValues = sounds
        wakePicker?.wrapSelectorWheel = true
        if (AppCache.wake > 0) {
            wakePicker?.value = AppCache.wake

        } else {
            wakePicker?.value = 0

        }
        wakePicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            wakeSound = sounds[newVal]
            wakeAlarmTextview?.text = wakeSound
            wakeAlarmTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.redText))
            wakeSoundDrawable = newVal.toString()
            MediaPlayerUtils.stopPlaying()
            MediaPlayerUtils.playWaterSound(activity!!, soundDrawable[newVal].toString())
        }
    }

    private fun napStartPicker() {
        napStartPicker?.minValue = 0
        napStartPicker?.maxValue = sounds.size - 1
        napStartPicker?.displayedValues = sounds
        napStartPicker?.wrapSelectorWheel = true
        if (AppCache.napStart > 0) {
            napStartPicker?.value = AppCache.napStart

        } else {
            napStartPicker?.value = 0

        }
        napStartPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            napStartSound = sounds[newVal]
            napeStartAlarmTextview?.text = napStartSound
            napeStartAlarmTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.redText))
            napStartSoundDrawable = newVal.toString()
            MediaPlayerUtils.stopPlaying()
            MediaPlayerUtils.playWaterSound(activity!!, soundDrawable[newVal].toString())
        }
    }

    private fun napEndPicker() {
        napEndPicker?.minValue = 0
        napEndPicker?.maxValue = sounds.size - 1
        napEndPicker?.displayedValues = sounds
        napEndPicker?.wrapSelectorWheel = true
        if (AppCache.napEnd > 0) {
            napEndPicker?.value = AppCache.napEnd

        } else {
            napEndPicker?.value = 0

        }
        napEndPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            napEndSound = sounds[newVal]
            napEndAlarmTextview?.text = napEndSound
            napEndAlarmTextview?.setTextColor(ContextCompat.getColor(activity!!,R.color.redText))
            napEndSoundDrawable = newVal.toString()
            MediaPlayerUtils.stopPlaying()
            MediaPlayerUtils.playWaterSound(activity!!, soundDrawable[newVal].toString())
        }
    }

    private fun resetAlarmSounds(){

        doseOnePicker?.value = 0
        doseOneAlarmTextview?.text=sounds[0]
        doseTwoPicker?.value=0
        doseTwoAlarmTextview?.text=sounds[0]
        wakePicker?.value = 0
        wakeAlarmTextview?.text=sounds[0]
        napStartPicker?.value = 0
        napeStartAlarmTextview?.text=sounds[0]
        napEndPicker?.value = 0
        napEndAlarmTextview?.text=sounds[0]

        doseOneAlarmTextview?.setTextColor(Color.BLACK)
        doseTwoAlarmTextview?.setTextColor(Color.BLACK)
        wakeAlarmTextview?.setTextColor(Color.BLACK)
        napeStartAlarmTextview?.setTextColor(Color.BLACK)
        napEndAlarmTextview?.setTextColor(Color.BLACK)

        AppCache.dose1 = 0
        AppCache.dose2 = 0
        AppCache.wake = 0
        AppCache.napStart = 0
        AppCache.napEnd = 0
    }

}