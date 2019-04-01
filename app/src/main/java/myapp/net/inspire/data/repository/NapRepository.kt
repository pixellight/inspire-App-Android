package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.NapDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.Nap
import org.jetbrains.anko.doAsync

/**
 * Created by Alucard on 2/16/2019.
 */
class NapRepository(context: Context) {
    private var mNapDao: NapDao? = null
    private var mAllNap: List<Nap>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mNapDao = db.napDao()
        mAllNap = mNapDao!!.getAll()
    }

    fun getNapById(id: Int): Nap {
        return mNapDao!!.getNapById(id)
    }

    fun insertNap(plan: Nap) {
        doAsync {
            mNapDao!!.insertNap(plan)
        }
    }

    fun updateNap(plan: Nap) {
        mNapDao!!.updateNap(plan.napOne, plan.napTwo, plan.napThree, plan.napOneInterval,
                plan.napTwoInterval, plan.napThreeInterval, plan.id!!.toLong())
    }

    fun updateNapOne(napOne:String,id:Long){
        mNapDao!!.updateNapOne(napOne,id!!.toLong())
    }
    fun updateNapTwo(napTwo:String,id:Long){
        mNapDao!!.updateNapOne(napTwo,id!!.toLong())
    }
    fun updateNapThree(napThree:String,id:Long){
        mNapDao!!.updateNapOne(napThree,id!!.toLong())
    }

    fun deleteNap(id:Int){
        mNapDao!!.deleteNaps(id)
    }
}