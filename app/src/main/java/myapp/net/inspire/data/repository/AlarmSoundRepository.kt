package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.AlarmSoundDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.AlarmSound
import org.jetbrains.anko.doAsync

/**
 * Created by Alucard on 2/16/2019.
 */
class AlarmSoundRepository(context: Context) {
    private var mAlarmSoundDao: AlarmSoundDao? = null
    private var mAllAlarmSound: List<AlarmSound>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mAlarmSoundDao = db.alarmSoundDao()
        mAllAlarmSound = mAlarmSoundDao!!.getAll()
    }

    fun getAlarmSoundById(id: Int): AlarmSound {
        return mAlarmSoundDao!!.getAlarmSoundById(id)
    }

    fun insertAlarmSound(plan: AlarmSound) {
        doAsync {
            mAlarmSoundDao!!.insertAlarmSound(plan)
        }
    }

    fun updateAlarmSound(plan: AlarmSound) {
        mAlarmSoundDao!!.updateAlarmSound(plan.soundDoseOne, plan.soundDoseTwo, plan.soundWakeup,
                plan.soundNapStart, plan.soundNapEnd, plan.id!!.toLong())
    }

    fun deleteAlarmSound(id:Int){
        mAlarmSoundDao!!.deleteSoundById(id)
    }
}