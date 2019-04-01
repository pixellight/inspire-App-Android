package myapp.net.inspire.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import myapp.net.inspire.data.entity.Reminder

/**
 * Created by Alucard on 2/16/2019.
 * ReminderDao
 */

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminder: Reminder)

    @Query("UPDATE reminder_tbl SET reminder_eat_by=:eatBy,reminder_dose_one=:doseOne, " +
            "reminder_nap=:nap WHERE id = :id")
    fun updateReminder(eatBy: Boolean, doseOne: Boolean, nap: Boolean, id: Long)

    @Query("SELECT * FROM reminder_tbl WHERE id=:id")
    fun getReminderById(id: Int): Reminder

    @Query("SELECT * FROM reminder_tbl")
    fun getAll(): List<Reminder>

    @Query("DELETE FROM reminder_tbl WHERE id=:id")
    fun deleteReminder(id: Int)
}