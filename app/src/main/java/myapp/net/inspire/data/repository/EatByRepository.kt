package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.EatByDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.EatByHistory
import org.jetbrains.anko.doAsync

/**
 * Created by QPay on 2/22/2019.
 */
class EatByRepository(context: Context) {

    private var mEatByHistoryDao: EatByDao? = null
    private var mAllEatHisotry: List<EatByHistory>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mEatByHistoryDao = db.eatByDao()
        mAllEatHisotry = mEatByHistoryDao!!.getAllEatByHistory()
    }


    fun getEatBy(currentDate: String): EatByHistory {
        return mEatByHistoryDao!!.getEatByHistory(currentDate)
    }


    fun insertEatByHistory(eatByHistory: EatByHistory) {
        doAsync {
            mEatByHistoryDao!!.insert(eatByHistory)
        }
    }

    fun updateNapHistory(eatBy:String,date:String) {
        mEatByHistoryDao!!.update(eatBy,date)
    }

    fun deleteEatBy(id:Int){
        mEatByHistoryDao!!.deleteEatBy(id)
    }

}