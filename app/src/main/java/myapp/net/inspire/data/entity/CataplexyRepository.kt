package myapp.net.inspire.data.entity

import android.content.Context
import myapp.net.inspire.data.dao.CataplexyDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import org.jetbrains.anko.doAsync

/**
 * Created by Alucard on 2/22/2019.
 */
class CataplexyRepository(context:Context) {

    private var mCataplexyDao: CataplexyDao? = null
    private var mAllCataplexy: List<Cataplexy>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mCataplexyDao = db.cataplexyDao()
        mAllCataplexy = mCataplexyDao!!.getAllCataplexy()
    }


    fun getTodayCataplexy(currentDate: String): Cataplexy {
        return mCataplexyDao!!.getTodayCataplexy(currentDate)
    }


    fun insertCataplexy(doseHistory: Cataplexy) {
        mCataplexyDao!!.insert(doseHistory)
    }

    open fun upsert(doseHistory: Cataplexy) {
        var id: Long? = 0
        doAsync {
            id = mCataplexyDao!!.insert(doseHistory)

        }
        if (id == -1.toLong()) {
            mCataplexyDao!!.update(doseHistory)
        }
    }

    fun updateCataplexy(doseHistory: Cataplexy) {
        mCataplexyDao!!.update(doseHistory)
    }
}