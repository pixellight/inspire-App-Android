package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Alucard on 2/16/2019.
 * Plan Entity and POJO class for plan
 */

@Entity(tableName = "plan_tbl")
data class Plan(
        @PrimaryKey(autoGenerate = true)
         var id: Long?,
        @ColumnInfo(name = "eat_by")
         var eatBy: String="",
        @ColumnInfo(name = "dose_one")
         var doseOne: String="",
        @ColumnInfo(name = "dose_two")
         var doseTwo: String="",
        @ColumnInfo(name = "wakeup")
         var wakeUp: String="",
        @ColumnInfo(name = "week_no")
         var reminderWeekNo: Int=-1
)