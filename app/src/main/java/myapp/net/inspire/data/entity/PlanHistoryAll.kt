package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by QPay on 3/14/2019.
 */
@Entity(tableName = "plan_history_all_tbl")
data class PlanHistoryAll(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long?,
        @ColumnInfo(name = "wakeUp")
        val wakeUp: String = "",
        @ColumnInfo(name = "dose_one")
        val doseOne: String = "",
        @ColumnInfo(name = "dose_two")
        val doseTwo: String = "",
        @ColumnInfo(name = "nap_one")
        val napOne: String = "",
        @ColumnInfo(name = "nap_two")
        val napTwo: String = "",
        @ColumnInfo(name = "nap_three")
        val napThree: String = "",
        @ColumnInfo(name = "eat_by")
        val eatBy: String = "",
        @ColumnInfo(name = "created_date")
        val created_date: String = "",
        @ColumnInfo(name = "weekNo")
        val weekNo: Int = -1)