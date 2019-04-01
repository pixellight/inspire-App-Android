package myapp.net.inspire.data.dao

import android.arch.persistence.room.*
import myapp.net.inspire.data.entity.EdsSeverity
import myapp.net.inspire.data.entity.PlanHistoryAll

/**
 * Created by QPay on 3/14/2019.
 */
@Dao
interface PlanHistoryAllDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(planHistoryAll: PlanHistoryAll)

    @Query("SELECT * FROM plan_history_all_tbl")
    fun getAllPlanHistory(): List<PlanHistoryAll>

    @Query("SELECT * FROM plan_history_all_tbl WHERE created_date=:date")
    fun getCurrentPlanHistoryAll(date: String): PlanHistoryAll

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(planHistoryAll: PlanHistoryAll)

    @Query("UPDATE plan_history_all_tbl SET wakeUp=:wakeUp WHERE id=:id")
    fun updateWakeUp(wakeUp: String, id: String)

    @Query("UPDATE plan_history_all_tbl SET dose_one=:doseOne  WHERE id=:id")
    fun updateDoseOne(doseOne: String, id: String)

    @Query("UPDATE plan_history_all_tbl SET dose_two=:doseTwo WHERE id=:id")
    fun updateDoseTwo(doseTwo: String, id: String)

    @Query("UPDATE plan_history_all_tbl SET nap_one=:napOne WHERE id=:id")
    fun updateNapOne(napOne: String, id: String)

    @Query("UPDATE plan_history_all_tbl SET nap_two=:napTwo WHERE id=:id")
    fun updateNapTwo(napTwo: String, id: String)

    @Query("UPDATE plan_history_all_tbl SET nap_three=:napThree WHERE id=:id")
    fun updateNapThree(napThree: String, id: String)

    @Query("UPDATE plan_history_all_tbl SET eat_by=:eatBy WHERE id=:id")
    fun updateEatBy(eatBy: String, id: String)


    @Query("SELECT * FROM plan_history_all_tbl GROUP BY created_date")
    fun getAMonthData(): List<PlanHistoryAll>

    @Query("SELECT * FROM plan_history_all_tbl WHERE created_date BETWEEN DATE('now','localtime','start of year') AND DATE('now','localtime')")
    fun getMonthData(): List<PlanHistoryAll>

    @Query("SELECT * FROM plan_history_all_tbl WHERE DATE(created_date) >= DATE('now', 'weekday 0', '-7 days')")
    fun getAWeekData(): List<PlanHistoryAll>

    @Query("DELETE FROM plan_history_all_tbl WHERE created_date=:date")
    fun deletePlanAllHistory(date: String)



}