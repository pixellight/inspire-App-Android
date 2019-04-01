package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.NapHistoryDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.NapHistory
import org.jetbrains.anko.doAsync

/**
 * Created by QPay on 2/22/2019.
 */
class NapHistoryRepository(context: Context) {

    private var mNapHistoryDao: NapHistoryDao? = null
    private var mAllNapHisotry: List<NapHistory>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mNapHistoryDao = db.napHistoryDao()
        mAllNapHisotry = mNapHistoryDao!!.getAllNapHistory()
    }


    fun getNapOne(currentDate: String, napName: String): NapHistory {
        return mNapHistoryDao!!.getNapOne(currentDate, napName)
    }

    fun getNapTwo(currentDate: String, napName: String): NapHistory {
        return mNapHistoryDao!!.getNapTwo(currentDate, napName)
    }

    fun getNapThree(currentDate: String, napName: String): NapHistory {
        return mNapHistoryDao!!.getNapThree(currentDate, napName)
    }

    fun getNap(id:Int): NapHistory {
        return mNapHistoryDao!!.getNap(id)
    }

    fun insertNapHistory(napHistory: NapHistory) {
        doAsync {
            mNapHistoryDao!!.insert(napHistory)
        }
    }

//    fun updateNapHistory(napHistory: NapHistory) {
//        mNapHistoryDao!!.update(napHistory.napOne, napHistory.napTwo, napHistory.napThree, napHistory.currentDate)
//    }

    fun updateNapOneHistory(napHistory: NapHistory) {
        mNapHistoryDao!!.updateNapOneHistory(napHistory!!.napOne, napHistory!!.id.toString())
    }

    fun updateNapTwoHistory(napHistory: NapHistory) {
        mNapHistoryDao!!.updateNapTwoHistory(napHistory!!.napTwo, napHistory!!.id.toString())
    }

    fun updateNapThreeHistory(napHistory: NapHistory) {
        mNapHistoryDao!!.updateNapThreeHistory(napHistory!!.napThree, napHistory!!.id.toString())
    }


    fun deleteNapHistory(id:Int){
        mNapHistoryDao!!.deleteNapHistory(id)
    }


}