package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by QPay on 2/22/2019.
 */
@Entity(tableName = "dose_history_tbl")
data class DoseHistory(
        @PrimaryKey(autoGenerate = false)
        var id: Long?,
        @ColumnInfo(name = "dose_one")
        var doseOne: String,
        @ColumnInfo(name = "dose_two")
        var doseTwo: String,
        @ColumnInfo(name = "current_date")
        var currentDate: String = "",
        @ColumnInfo(name = "achieved")
        var achieve: Int? = 0
)