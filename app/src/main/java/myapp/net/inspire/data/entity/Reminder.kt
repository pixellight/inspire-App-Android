package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Alucard on 2/16/2019.
 */
@Entity(tableName = "reminder_tbl")
data class Reminder(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = "reminder_eat_by")
        var reminderEatBy: Boolean = false,
        @ColumnInfo(name = "reminder_dose_one")
        var reminderDoseOne: Boolean = false,
        @ColumnInfo(name = "reminder_nap")
        var reminderNap: Boolean = false,
        @ColumnInfo(name = "week_no")
        var reminderWeekNo: Int = -1
)