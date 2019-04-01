package myapp.net.inspire.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import myapp.net.inspire.data.entity.Nap

/**
 * Created by Alucard on 2/16/2019.
 * NapDao class
 */

@Dao
interface NapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNap(nap: Nap)

    @Query("UPDATE nap_tbl SET nap_one=:napOne, nap_two=:napTwo, " +
            "nap_three=:napThree, nap_one_interval=:napeOneInt, nap_two_interval=:napTwoInt," +
            "nap_three_interval=:napThreeInt WHERE week_no=:id")
    fun updateNap(napOne: String, napTwo: String, napThree: String, napeOneInt: String,
                  napTwoInt: String, napThreeInt: String, id: Long)

    @Query("SELECT * FROM nap_tbl WHERE id=:id")
    fun getNapById(id: Int): Nap

    @Query("SELECT * FROM nap_tbl")
    fun getAll(): List<Nap>

    @Query("UPDATE nap_tbl SET nap_one=:napOne WHERE id=:id")
    fun updateNapOne(napOne: String,id: Long)

    @Query("UPDATE nap_tbl SET nap_two=:napTwo WHERE id=:id")
    fun updateNapTwo(napTwo: String,id: Long)

    @Query("UPDATE nap_tbl SET nap_three=:napThree WHERE id=:id")
    fun updateNapThree(napThree: String,id: Long)

    @Query("DELETE FROM nap_tbl WHERE id=:id")
    fun deleteNaps(id: Int)
}