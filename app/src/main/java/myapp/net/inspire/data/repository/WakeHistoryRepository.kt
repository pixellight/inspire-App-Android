package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.WakHistoryDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.WakeHistory
import org.jetbrains.anko.doAsync

/**
 * Created by QPay on 2/24/2019.
 */
class WakeHistoryRepository(context: Context) {

    private var mWakeHistoryDao: WakHistoryDao? = null
    private var mAllEatHisotry: List<WakeHistory>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mWakeHistoryDao = db.wakeHistoryDao()
        mAllEatHisotry = mWakeHistoryDao!!.getAllWakeHistory()
    }


    fun getWakeBy(currentDate: String): WakeHistory {
        return mWakeHistoryDao!!.getWakeHistory(currentDate)
    }


    fun insertWakeHistory(wakeHistory: WakeHistory) {
        doAsync {
            mWakeHistoryDao!!.insert(wakeHistory)
        }
    }

    fun updateWakeHistory(wakeUp:String,date:String) {
        mWakeHistoryDao!!.update(wakeUp,date)
    }

    fun deleteWakeHistory(id:Int){
        mWakeHistoryDao!!.delteWake(id)
    }
}