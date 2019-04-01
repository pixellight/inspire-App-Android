package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.DoseHistoryDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.DoseHistory
import org.jetbrains.anko.doAsync

/**
 * Created by QPay on 2/22/2019.
 */
class DoseHistoryRepository(context: Context) {

    private var mDoseHistoryDao: DoseHistoryDao? = null
    private var mAllDoseHistory: List<DoseHistory>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mDoseHistoryDao = db.doseHistoryDao()
        mAllDoseHistory = mDoseHistoryDao!!.getAllDoseHistory()
    }


    fun getDoseOne(currentDate: String, napName: String): DoseHistory {
        return mDoseHistoryDao!!.getDoseOne(currentDate, napName)
    }

    fun getDoseTwo(currentDate: String, napName: String): DoseHistory {
        return mDoseHistoryDao!!.getDoseTwo(currentDate, napName)
    }

    fun getDose(id:Int): DoseHistory {
        return mDoseHistoryDao!!.getDose(id)
    }


    fun insertDoseHistory(doseHistory: DoseHistory) {

        doAsync {
            mDoseHistoryDao!!.insert(doseHistory)
        }
    }

    fun updateDoseOneHistory(doseHistory: DoseHistory) {
        mDoseHistoryDao!!.updateDoseOneHistory(doseHistory!!.doseOne, doseHistory!!.id.toString())
    }

    fun updateDoseTwoHistory(doseHistory: DoseHistory) {
        mDoseHistoryDao!!.updateDoseTwoHistory(doseHistory!!.doseTwo, doseHistory!!.id.toString())
    }
    fun updateDoseHistory(doseOne:String,doseTwo:String,id: Int) {
        mDoseHistoryDao!!.update(doseOne, doseTwo, id)
    }

    fun deleteDoseHistory(id:Int){
        mDoseHistoryDao!!.delteDoseHistory(id)
    }

}