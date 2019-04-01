package myapp.net.inspire.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import myapp.net.inspire.data.entity.Plan

/**
 * Created by Alucard on 2/16/2019.
 * PlanDao class
 */

@Dao
interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlan(plan: Plan)

    @Query("UPDATE plan_tbl SET eat_by=:eatBy,dose_one=:doseOne, " +
            "dose_two=:doseTwo,wakeup=:wakeUp WHERE id = :id")
    fun updatePlan(eatBy: String, doseOne: String, doseTwo: String, wakeUp: String, id: Long)

    @Query("SELECT * FROM plan_tbl WHERE id=:id")
    fun getPlanById(id: Int): Plan

    @Query("SELECT * FROM plan_tbl p JOIN reminder_tbl r")
    fun getAll(): List<Plan>

    @Query("DELETE FROM plan_tbl WHERE id=:id")
    fun deletePlan(id: Int)

}