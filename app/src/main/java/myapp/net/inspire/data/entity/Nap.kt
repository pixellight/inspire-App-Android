package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Alucard on 2/16/2019.
 */
@Entity(tableName = "nap_tbl")
data class Nap(
        @PrimaryKey(autoGenerate = true)
         var id: Long?,
        @ColumnInfo(name = "nap_one")
         var napOne: String="",
        @ColumnInfo(name = "nap_two")
         var napTwo: String="",
        @ColumnInfo(name = "nap_three")
         var napThree: String="",
        @ColumnInfo(name = "week_no")
         var reminderWeekNo: Int=-1,
        @ColumnInfo(name = "nap_one_interval")
         var napOneInterval: String="",
        @ColumnInfo(name = "nap_two_interval")
         var napTwoInterval: String="",
        @ColumnInfo(name = "nap_three_interval")
         var napThreeInterval: String=""
)