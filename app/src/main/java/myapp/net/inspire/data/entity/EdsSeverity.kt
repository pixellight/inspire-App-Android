package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Alucard on 2/22/2019.
 */
@Entity(tableName = "eds_severity_tbl")
data class EdsSeverity(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = "question_one")
        var questionOne: Int = 0,
        @ColumnInfo(name = "question_two")
        var questionTwo: Int = 0,
        @ColumnInfo(name = "question_three")
        var questionThree: Int = 0,
        @ColumnInfo(name = "question_four")
        var questionFour: Int = 0,
        @ColumnInfo(name = "question_five")
        var questionFive: Int = 0,
        @ColumnInfo(name = "question_six")
        var questionSix: Int = 0,
        @ColumnInfo(name = "question_seven")
        var questionSeven: Int = 0,
        @ColumnInfo(name = "question_eight")
        var questionEight: Int = 0,
        @ColumnInfo(name = "sum")
        var sum: Int = 0,
        @ColumnInfo(name = "cataplexy")
        var cataplexy: Int = 0,
        @ColumnInfo(name = "created_date")
        var createdDate: String? = ""

)