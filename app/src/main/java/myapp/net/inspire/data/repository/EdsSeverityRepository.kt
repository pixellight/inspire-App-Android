package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.EdsSeverityDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.EdsSeverity
import java.util.*

/**
 * Created by Alucard on 2/22/2019.
 */
class EdsSeverityRepository(context: Context) {

    private var mEdsSeverityDao: EdsSeverityDao? = null
    private var mAllEdsSeverity: List<EdsSeverity>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mEdsSeverityDao = db.edsSeverityDao()
        mAllEdsSeverity = mEdsSeverityDao!!.getAllEdsSeverity()
    }


    fun getAllEdsSeverity(): List<EdsSeverity> {
        return mAllEdsSeverity!!
    }


    fun getTodayEdsSeverity(currentDate: String): EdsSeverity {
        return mEdsSeverityDao!!.getTodayEdsSeverity(currentDate)
    }


    fun insertEdsSeverity(doseHistory: EdsSeverity) {
        mEdsSeverityDao!!.insert(doseHistory)
    }


    fun updateEdsSeverity(doseHistory: EdsSeverity) {
        mEdsSeverityDao!!.update(doseHistory)
    }

    fun getAllData(): List<EdsSeverity> {
        return mEdsSeverityDao!!.getAWeekData()
    }

    fun getMonthData(): List<EdsSeverity> {
        return mEdsSeverityDao!!.getMonthData()
    }

}