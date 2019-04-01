package myapp.net.inspire.data.dao

import android.arch.persistence.room.*
import myapp.net.inspire.data.entity.EdsSeverity

/**
 * Created by Alucard on 2/22/2019.
 */
@Dao
interface EdsSeverityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(edsSeverity: EdsSeverity): Long

    @Query("SELECT * FROM eds_severity_tbl")
    fun getAllEdsSeverity(): List<EdsSeverity>

    @Query("SELECT * FROM eds_severity_tbl WHERE created_date=:date")
    fun getTodayEdsSeverity(date: String): EdsSeverity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(edsSeverity: EdsSeverity)

    @Query("SELECT * FROM eds_severity_tbl GROUP BY created_date")
    fun getAMonthData(): List<EdsSeverity>

    @Query("SELECT * FROM eds_severity_tbl WHERE created_date BETWEEN DATE('now','localtime','start of year') AND DATE('now','localtime')")
    fun getMonthData(): List<EdsSeverity>

    @Query("SELECT * FROM eds_severity_tbl WHERE DATE(created_date) >= DATE('now', 'weekday 0', '-7 days')")
    fun getAWeekData(): List<EdsSeverity>

    @Query("SELECT * FROM eds_severity_tbl WHERE strftime('%W', 'now', 'localtime', 'weekday 0', '-6 days')")
    fun getCurrentWeekData(): List<EdsSeverity>

}