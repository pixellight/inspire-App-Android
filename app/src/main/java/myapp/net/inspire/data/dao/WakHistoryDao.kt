package myapp.net.inspire.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import myapp.net.inspire.data.entity.WakeHistory

/**
 * Created by QPay on 2/24/2019.
 */
@Dao
interface WakHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wakeHistory: WakeHistory): Long

    @Query("SELECT * FROM wake_history_tbl")
    fun getAllWakeHistory(): List<WakeHistory>

    @Query("SELECT * FROM wake_history_tbl WHERE id=:date")
    fun getWakeHistory(date: String): WakeHistory

    @Query("UPDATE wake_history_tbl SET wake_up=:wakeUp WHERE id=:date")
    fun update(wakeUp:String,date: String)

    @Query("DELETE FROM wake_history_tbl WHERE id=:id")
    fun delteWake(id:Int)
}