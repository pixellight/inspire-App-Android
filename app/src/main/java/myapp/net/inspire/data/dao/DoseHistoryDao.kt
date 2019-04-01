package myapp.net.inspire.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import myapp.net.inspire.data.entity.DoseHistory


/**
 * Created by QPay on 2/22/2019.
 */
@Dao
interface DoseHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(doseHistory: DoseHistory): Long


    @Query("SELECT * FROM dose_history_tbl")
    fun getAllDoseHistory(): List<DoseHistory>

    @Query("SELECT * FROM dose_history_tbl WHERE current_date=:date AND dose_one=:doseOne")
    fun getDoseOne(date: String, doseOne: String): DoseHistory

    @Query("SELECT * FROM dose_history_tbl WHERE current_date=:date AND dose_two=:doseTwo")
    fun getDoseTwo(date: String, doseTwo: String): DoseHistory

    @Query("SELECT * FROM dose_history_tbl  WHERE id=:id")
    fun getDose(id: Int): DoseHistory


    @Query("UPDATE dose_history_tbl SET dose_one=:doseOne WHERE id=:id")
    fun updateDoseOneHistory(doseOne: String, id: String)

    @Query("UPDATE dose_history_tbl SET dose_two=:doseTwo WHERE id=:id")
    fun updateDoseTwoHistory(doseTwo: String, id: String)

    @Query("UPDATE dose_history_tbl SET dose_one=:doseOne,dose_two=:doseTwo WHERE id=:id")
    fun update(doseOne: String, doseTwo: String, id: Int)


    @Query("DELETE FROM dose_history_tbl WHERE id=:id")
    fun delteDoseHistory(id:Int)
}