package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.ReminderDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.Reminder
import org.jetbrains.anko.doAsync

/**
 * Created by Alucard on 2/16/2019.
 */
class ReminderRepository(context: Context) {

    private var mReminderDao: ReminderDao? = null
    private var mAllReminder: List<Reminder>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mReminderDao = db.reminderDao()
        mAllReminder = mReminderDao!!.getAll()
    }

    fun getReminderById(id: Int): Reminder {
        return mReminderDao!!.getReminderById(id)
    }

    fun insertReminder(plan: Reminder) {
        doAsync {
            mReminderDao!!.insertReminder(plan)
        }
    }

    fun updateReminder(plan: Reminder) {
        mReminderDao!!.updateReminder(plan.reminderEatBy,plan.reminderDoseOne,plan.reminderNap,plan.id!!.toLong())
    }

    fun deleteReminder(id:Int){
        mReminderDao!!.deleteReminder(id)
    }

}