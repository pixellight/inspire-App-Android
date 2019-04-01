package myapp.net.inspire.alarm

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_sound.*
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.AlarmSound
import myapp.net.inspire.data.entity.Reminder
import myapp.net.inspire.data.repository.AlarmSoundRepository
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.plan.PlanSetupFristActivity
import myapp.net.inspire.plan.PlanSetupTenFragment
import myapp.net.inspire.utils.LoadFragment
import myapp.net.inspire.utils.MediaPlayerUtils


/**
 * Created by Alucard on 2/10/2019.
 */
class AlarmFragment : AppCompatActivity(), View.OnClickListener {
    companion object {
        val TAG = "AlarmFragment"
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
    private var weekId: Int? = 0
    private var mAlarmSoundRepository: AlarmSoundRepository? = null
    private var cross:TextView?=null
    private var back: TextView? = null
    private var isPlanSetup: Boolean? = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sound)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        toolbar.visibility = View.VISIBLE
        var reset = (this@AlarmFragment as AppCompatActivity).findViewById<TextView>(R.id.titleRight)
     
        reset.text = getString(R.string.reset)
        title.text = "Sounds"




        reset.setOnClickListener {
//            dialogReset()
            resetAlarmSounds()
        }

        weekId = intent.extras.getInt("week")
        mAlarmSoundRepository = AlarmSoundRepository(this@AlarmFragment)
        var sound = mAlarmSoundRepository!!.getAlarmSoundById(weekId!!)
        var doseOnePos = sound.soundDoseOne!!.toInt()
        var doseTwoPos = sound.soundDoseTwo!!.toInt()
        var wakePos = sound.soundWakeup!!.toInt()
        var napStartPos = sound.soundNapStart!!.toInt()
        var napEndPos = sound.soundNapEnd!!.toInt()
        doseOneSoundDrawable = doseOnePos.toString()
        doseTwoSoundDrawable = doseTwoPos.toString()
        wakeSoundDrawable = wakePos.toString()
        napStartSoundDrawable = napStartPos.toString()
        napEndSoundDrawable = napEndPos.toString()

        doseOneAlarmTextview?.text = sounds[doseOnePos]
        doseTwoAlarmTextview?.text = sounds[doseTwoPos]
        wakeAlarmTextview?.text = sounds[wakePos]
        napeStartAlarmTextview?.text = sounds[napStartPos]
        napEndAlarmTextview?.text = sounds[napEndPos]

        doseOneLayout?.setOnClickListener(this)
        doseTwoLayout?.setOnClickListener(this)
        wakeLayout?.setOnClickListener(this)
        napeStartLayout?.setOnClickListener(this)
        napEndLayout?.setOnClickListener(this)

        doseOnePicker(doseOnePos)
        doseTwoPicker(doseTwoPos)
        wakePicker(wakePos)
        napStartPicker(napStartPos)
        napEndPicker(napEndPos)


    }
    


    fun dialogReset() {
        val builder = android.support.v7.app.AlertDialog.Builder(this@AlarmFragment)
        val inflater = this@AlarmFragment.layoutInflater
        val v = inflater.inflate(R.layout.dialog_reset, null)
        builder.setView(v)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        v.findViewById<TextView>(R.id.resetPlan).setOnClickListener(View.OnClickListener {
//            Repository().setIsPlanSetup(this@AlarmFragment, PlanEnum.NOPLAN.ordinal)
            Repository().setIsSchedulePlan(this@AlarmFragment,true)
            startActivity(Intent(this@AlarmFragment!!, PlanSetupFristActivity::class.java))
            this@AlarmFragment!!.finishAffinity()
            dialog.dismiss()
        })
        v.findViewById<TextView>(R.id.cancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        val density = this@AlarmFragment.resources.displayMetrics.density
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
                    doseOneAlarmTextview?.setTextColor(ContextCompat.getColor(this@AlarmFragment,R.color.colorPrimary))
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
                    doseTwoAlarmTextview?.setTextColor(ContextCompat.getColor(this@AlarmFragment,R.color.colorPrimary))
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
                    wakeAlarmTextview?.setTextColor(ContextCompat.getColor(this@AlarmFragment,R.color.colorPrimary))
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
                    napeStartTextview?.setTextColor(ContextCompat.getColor(this@AlarmFragment,R.color.colorPrimary))
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
                    napEndTextview?.setTextColor(ContextCompat.getColor(this@AlarmFragment,R.color.colorPrimary))
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


    private fun doseOnePicker(pos: Int) {
        doseOnePicker?.minValue = 0
        doseOnePicker?.maxValue = sounds.size - 1
        doseOnePicker?.displayedValues = sounds
        doseOnePicker?.wrapSelectorWheel = true
        doseOnePicker?.value = pos
        doseOnePicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            doseOneSound = sounds[newVal]
            doseOneAlarmTextview?.text = doseOneSound
            doseOneAlarmTextview?.setTextColor(ContextCompat.getColor(this@AlarmFragment,R.color.redText))
            doseOneSoundDrawable = newVal.toString()
            MediaPlayerUtils.stopPlaying()
            MediaPlayerUtils.playWaterSound(this@AlarmFragment!!, soundDrawable[newVal].toString())
        }
    }

    private fun doseTwoPicker(pos: Int) {
        doseTwoPicker?.minValue = 0
        doseTwoPicker?.maxValue = sounds.size - 1
        doseTwoPicker?.displayedValues = sounds
        doseTwoPicker?.wrapSelectorWheel = true
        doseTwoPicker?.value = pos
        doseTwoPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            doseTwoSound = sounds[newVal]
            doseTwoAlarmTextview?.text = doseTwoSound
            doseTwoAlarmTextview?.setTextColor(ContextCompat.getColor(this@AlarmFragment,R.color.redText))
            doseTwoSoundDrawable = newVal.toString()
            MediaPlayerUtils.stopPlaying()
            MediaPlayerUtils.playWaterSound(this@AlarmFragment!!, soundDrawable[newVal].toString())
        }
    }

    private fun wakePicker(pos: Int) {
        wakePicker?.minValue = 0
        wakePicker?.maxValue = sounds.size - 1
        wakePicker?.displayedValues = sounds
        wakePicker?.wrapSelectorWheel = true
        wakePicker?.value = pos
        wakePicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            wakeSound = sounds[newVal]
            wakeAlarmTextview?.text = wakeSound
            wakeAlarmTextview?.setTextColor(ContextCompat.getColor(this@AlarmFragment,R.color.redText))
            wakeSoundDrawable = newVal.toString()
            MediaPlayerUtils.stopPlaying()
            MediaPlayerUtils.playWaterSound(this@AlarmFragment!!, soundDrawable[newVal].toString())
        }
    }

    private fun napStartPicker(pos: Int) {
        napStartPicker?.minValue = 0
        napStartPicker?.maxValue = sounds.size - 1
        napStartPicker?.displayedValues = sounds
        napStartPicker?.wrapSelectorWheel = true
        napStartPicker?.value = pos
        napStartPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            napStartSound = sounds[newVal]
            napeStartAlarmTextview?.text = napStartSound
            napeStartAlarmTextview?.setTextColor(ContextCompat.getColor(this@AlarmFragment,R.color.redText))
            napStartSoundDrawable = newVal.toString()
            MediaPlayerUtils.stopPlaying()
            MediaPlayerUtils.playWaterSound(this@AlarmFragment!!, soundDrawable[newVal].toString())
        }
    }

    private fun napEndPicker(pos: Int) {
        napEndPicker?.minValue = 0
        napEndPicker?.maxValue = sounds.size - 1
        napEndPicker?.displayedValues = sounds
        napEndPicker?.wrapSelectorWheel = true
        napEndPicker?.value = pos
        napEndPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            napEndSound = sounds[newVal]
            napEndAlarmTextview?.text = napEndSound
            napEndAlarmTextview?.setTextColor(ContextCompat.getColor(this@AlarmFragment,R.color.redText))
            napEndSoundDrawable = newVal.toString()
            MediaPlayerUtils.stopPlaying()
            MediaPlayerUtils.playWaterSound(this@AlarmFragment!!, soundDrawable[newVal].toString())
        }
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
//                    MediaPlayerUtils.stopPlaying()
//                    var sound = AlarmSound(id = weekId!!.toLong(), soundDoseOne = doseOneSoundDrawable!!, soundDoseTwo = doseTwoSoundDrawable!!,
//                            soundWakeup = wakeSoundDrawable!!, soundNapStart = napStartSoundDrawable!!, soundNapEnd = napEndSoundDrawable!!, reminderWeekNo = weekId!!)
//                    mAlarmSoundRepository!!.updateAlarmSound(sound)
//                    LoadFragment.popupBackStack(this@AlarmFragment!!.supportFragmentManager, PlanSetupTenFragment.TAG)
//
//                    true
//                } else false
//            }
//
//        })
//
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        MediaPlayerUtils.stopPlaying()
        var sound = AlarmSound(id = weekId!!.toLong(), soundDoseOne = doseOneSoundDrawable!!, soundDoseTwo = doseTwoSoundDrawable!!,
                soundWakeup = wakeSoundDrawable!!, soundNapStart = napStartSoundDrawable!!, soundNapEnd = napEndSoundDrawable!!, reminderWeekNo = weekId!!)
        mAlarmSoundRepository!!.updateAlarmSound(sound)
        finish()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                MediaPlayerUtils.stopPlaying()
                var sound = AlarmSound(id = weekId!!.toLong(), soundDoseOne = doseOneSoundDrawable!!, soundDoseTwo = doseTwoSoundDrawable!!,
                        soundWakeup = wakeSoundDrawable!!, soundNapStart = napStartSoundDrawable!!, soundNapEnd = napEndSoundDrawable!!, reminderWeekNo = weekId!!)
                mAlarmSoundRepository!!.updateAlarmSound(sound)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)

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
    }
}