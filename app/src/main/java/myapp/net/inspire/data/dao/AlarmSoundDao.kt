package myapp.net.inspire.data.dao

import android.arch.persistence.room.*
import myapp.net.inspire.data.entity.AlarmSound

/**
 * Created by Alucard on 2/16/2019.
 * AlarmSoundDao class
 */

@Dao
interface AlarmSoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarmSound(alarmSound: AlarmSound)

    @Query("UPDATE alarm_sound_tbl SET sound_dose_one=:doseOne,sound_dose_two=:doseTwo, " +
            "sound_wakeup=:wake,sound_nap_start=:napStart,sound_nap_end=:napEnd WHERE id = :id")
    fun updateAlarmSound(doseOne: String, doseTwo: String, wake: String, napStart: String, napEnd: String, id: Long)

    @Query("SELECT * FROM alarm_sound_tbl WHERE week_no=:id")
    fun getAlarmSoundById(id: Int): AlarmSound

    @Query("SELECT * FROM alarm_sound_tbl")
    fun getAll(): List<AlarmSound>

    @Query("DELETE FROM alarm_sound_tbl WHERE week_no=:id")
    fun deleteSoundById(id:Int)
}