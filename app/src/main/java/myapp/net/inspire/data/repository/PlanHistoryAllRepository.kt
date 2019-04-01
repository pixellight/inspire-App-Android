package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.PlanHistoryAllDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.PlanHistoryAll
import org.jetbrains.anko.doAsync

/**
 * Created by QPay on 3/14/2019.
 */
class PlanHistoryAllRepository(context: Context) {

    private var mPlanHistoryDao: PlanHistoryAllDao? = null
    private var mAllPlanHistory: List<PlanHistoryAll>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mPlanHistoryDao = db.planHistoryAllDao()
        mAllPlanHistory = mPlanHistoryDao!!.getAllPlanHistory()
    }

    fun insertPlanHistoryAll(planHistoryAll: PlanHistoryAll) {
        doAsync {
            mPlanHistoryDao!!.insert(planHistoryAll)
        }
    }

    fun updateWake(planHistoryAll: PlanHistoryAll) {
        mPlanHistoryDao!!.updateWakeUp(planHistoryAll.wakeUp!!, planHistoryAll!!.id.toString())
    }

    fun updateDoseOne(planHistoryAll: PlanHistoryAll) {
        mPlanHistoryDao!!.updateDoseOne(planHistoryAll.doseOne!!, planHistoryAll!!.id.toString())
    }

    fun updateDoseTwo(planHistoryAll: PlanHistoryAll) {
        mPlanHistoryDao!!.updateDoseTwo(planHistoryAll.doseTwo!!, planHistoryAll!!.id.toString())
    }

    fun updateNapOne(planHistoryAll: PlanHistoryAll) {
        mPlanHistoryDao!!.updateNapOne(planHistoryAll.napOne!!, planHistoryAll!!.id.toString())
    }

    fun updateNapTwo(planHistoryAll: PlanHistoryAll) {
        mPlanHistoryDao!!.updateNapTwo(planHistoryAll.napTwo!!, planHistoryAll!!.id.toString())
    }

    fun updateNapThree(planHistoryAll: PlanHistoryAll) {
        mPlanHistoryDao!!.updateNapThree(planHistoryAll.napThree!!, planHistoryAll!!.id.toString())
    }

    fun updateEatBy(planHistoryAll: PlanHistoryAll) {
        mPlanHistoryDao!!.updateEatBy(planHistoryAll.napThree!!, planHistoryAll!!.id.toString())
    }

    fun getCurrentPlanHistoryAll(date: String): PlanHistoryAll {
        return mPlanHistoryDao!!.getCurrentPlanHistoryAll(date)
    }

    fun getAWeekData(): List<PlanHistoryAll> {
        return mPlanHistoryDao!!.getAWeekData()
    }

    fun getMonthData():List<PlanHistoryAll>{
        return mPlanHistoryDao!!.getMonthData()
    }

    fun deleteCurrentPlanHistory(date:String){
        mPlanHistoryDao!!.deletePlanAllHistory(date)
    }

}