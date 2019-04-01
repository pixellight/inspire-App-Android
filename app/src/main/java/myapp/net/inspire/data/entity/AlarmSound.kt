package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Alucard on 2/16/2019.
 */
@Entity(tableName = "alarm_sound_tbl")
data class AlarmSound(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = "sound_dose_one")
        var soundDoseOne: String = "",
        @ColumnInfo(name = "sound_dose_two")
        var soundDoseTwo: String = "",
        @ColumnInfo(name = "sound_wakeup")
        var soundWakeup: String = "",
        @ColumnInfo(name = "sound_nap_start")
        var soundNapStart: String = "",
        @ColumnInfo(name = "sound_nap_end")
        var soundNapEnd: String = "",
        @ColumnInfo(name = "week_no")
        var reminderWeekNo: Int = -1


)