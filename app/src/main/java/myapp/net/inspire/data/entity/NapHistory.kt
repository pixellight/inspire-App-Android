package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Alucard on 2/21/2019.
 */

@Entity(tableName = "nap_history_tbl")
data class NapHistory(
        @PrimaryKey(autoGenerate = false)
        var id: Long?,
        @ColumnInfo(name = "nap_one")
        var napOne: String = "",
        @ColumnInfo(name = "nap_two")
        var napTwo: String = "",
        @ColumnInfo(name = "nap_three")
        var napThree: String = "",
        @ColumnInfo(name = "current_date")
        var currentDate: String = "",
        @ColumnInfo(name = "achieved")
        var achieve: Int? = 0
)