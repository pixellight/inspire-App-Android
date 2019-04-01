package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Alucard on 2/22/2019.
 */
@Entity(tableName = "cataplexy_tbl")
data class Cataplexy(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = "sum")
        var questionOne: Int = 0,
        @ColumnInfo(name = "created_date")
        var createdDate: String
)