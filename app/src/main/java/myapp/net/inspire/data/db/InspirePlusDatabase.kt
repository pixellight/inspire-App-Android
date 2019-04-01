package myapp.net.inspire.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import myapp.net.inspire.data.dao.*
import myapp.net.inspire.data.entity.*
import myapp.net.inspire.utils.Constants

/**
 * Created by Alucard on 2/16/2019.
 * Singleton class
 */
@Database(entities = arrayOf(Plan::class, Reminder::class, Nap::class, AlarmSound::class, Note::class,
        EatByHistory::class, NapHistory::class, DoseHistory::class, EdsSeverity::class, PlanHistoryAll::class,
        Cataplexy::class, WakeHistory::class), version = 1, exportSchema = false)
abstract class InspirePlusDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao
    abstract fun reminderDao(): ReminderDao
    abstract fun napDao(): NapDao
    abstract fun alarmSoundDao(): AlarmSoundDao
    abstract fun noteDao(): NoteDao
    abstract fun eatByDao(): EatByDao
    abstract fun napHistoryDao(): NapHistoryDao
    abstract fun doseHistoryDao(): DoseHistoryDao
    abstract fun edsSeverityDao(): EdsSeverityDao
    abstract fun cataplexyDao(): CataplexyDao
    abstract fun wakeHistoryDao(): WakHistoryDao
    abstract fun planHistoryAllDao(): PlanHistoryAllDao

    companion object {
        @Volatile private var INSTANCE: InspirePlusDatabase? = null

        fun getInstance(context: Context): InspirePlusDatabase {
            if (INSTANCE == null) {
                synchronized(InspirePlusDatabase::class.java) {
                    if (INSTANCE == null) {
                        //creation of database
                        INSTANCE = Room.databaseBuilder<InspirePlusDatabase>(context.applicationContext,
                                InspirePlusDatabase::class.java!!, Constants.DATABASE).allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE!!
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}