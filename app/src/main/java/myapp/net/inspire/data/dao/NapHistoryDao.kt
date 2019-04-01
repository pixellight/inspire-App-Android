package myapp.net.inspire.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import myapp.net.inspire.data.entity.NapHistory

/**
 * Created by Alucard on 2/21/2019.
 */
@Dao
interface NapHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(napHistory: NapHistory): Long

    @Query("SELECT * FROM nap_history_tbl")
    fun getAllNapHistory(): List<NapHistory>

    @Query("SELECT * FROM nap_history_tbl WHERE current_date=:date AND nap_one=:napName")
    fun getNapOne(date: String, napName: String): NapHistory

    @Query("SELECT * FROM nap_history_tbl WHERE current_date=:date AND nap_two=:napName")
    fun getNapTwo(date: String, napName: String): NapHistory

    @Query("SELECT * FROM nap_history_tbl WHERE current_date=:date AND nap_three=:napName")
    fun getNapThree(date: String, napName: String): NapHistory

    @Query("SELECT * FROM nap_history_tbl WHERE id=:id")
    fun getNap(id:Int): NapHistory

    @Query("UPDATE nap_history_tbl SET nap_one=:napOne,nap_two=:napTwo, nap_three=:napThree WHERE current_date=:achieve")
    fun update(napOne: String, napTwo: String, napThree: String, achieve: String)

    @Query("UPDATE nap_history_tbl SET nap_one=:napOne WHERE id=:id")
    fun updateNapOneHistory(napOne: String, id: String)

    @Query("UPDATE nap_history_tbl SET nap_two=:napTwo WHERE id=:id")
    fun updateNapTwoHistory(napTwo: String, id: String)

    @Query("UPDATE nap_history_tbl SET nap_three=:napThree WHERE id=:id")
    fun updateNapThreeHistory(napThree: String, id: String)

    @Query("DELETE FROM nap_history_tbl WHERE id=:id")
    fun deleteNapHistory(id:Int)


}