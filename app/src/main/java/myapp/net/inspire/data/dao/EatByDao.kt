package myapp.net.inspire.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import myapp.net.inspire.data.entity.EatByHistory

/**
 * Created by QPay on 2/22/2019.
 */

@Dao
interface EatByDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(eatByHistory: EatByHistory): Long

    @Query("SELECT * FROM eatby_history_tbl")
    fun getAllEatByHistory(): List<EatByHistory>

    @Query("SELECT * FROM eatby_history_tbl WHERE id=:date")
    fun getEatByHistory(date: String): EatByHistory

    @Query("UPDATE eatby_history_tbl SET eat_by=:eatBy WHERE id=:date")
    fun update(eatBy:String,date:String)

    @Query("DELETE FROM eatby_history_tbl WHERE id=:id")
    fun deleteEatBy(id:Int)

}