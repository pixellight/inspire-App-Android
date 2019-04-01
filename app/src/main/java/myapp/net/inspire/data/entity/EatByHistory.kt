package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by QPay on 2/22/2019.
 */
@Entity(tableName = "eatby_history_tbl")
data class EatByHistory (
        @PrimaryKey(autoGenerate = true)
        var id:Long?,
        @ColumnInfo(name = "eat_by")
        var eatBy: String = "",
        @ColumnInfo(name = "current_date")
        var currentDate: String = "",
        @ColumnInfo(name = "achieved")
        var achieve: Int? = 0
)