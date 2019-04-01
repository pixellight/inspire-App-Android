package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.PlanDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.Plan
import org.jetbrains.anko.doAsync

/**
 * Created by Alucard on 2/16/2019.
 */
class PlanRepository(context: Context) {

    private var mPlanDao: PlanDao? = null
    private var mAllPlan: List<Plan>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mPlanDao = db.planDao()
        mAllPlan = mPlanDao!!.getAll()
    }

    fun getPlanById(id: Int): Plan {
        return mPlanDao!!.getPlanById(id)
    }

    fun insertPlan(plan: Plan) {
        doAsync{
            mPlanDao!!.insertPlan(plan)

        }
    }

    fun updatePlan(plan: Plan) {
        mPlanDao!!.updatePlan(plan.eatBy,plan.doseOne,plan.doseTwo,plan.wakeUp,plan.id!!.toLong())
    }

    fun deltePlan(id: Int){
        mPlanDao!!.deletePlan(id)
    }

}