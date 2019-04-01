package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by QPay on 2/24/2019.
 */
@Entity(tableName = "wake_history_tbl")
data class WakeHistory(
        @PrimaryKey(autoGenerate = false)
        var id: Long?,
        @ColumnInfo(name = "wake_up")
        var wakeUp: String = "",
        @ColumnInfo(name = "current_date")
        var currentDate: String = "",
        @ColumnInfo(name = "achieved")
        var achieve: Int? = 0
)