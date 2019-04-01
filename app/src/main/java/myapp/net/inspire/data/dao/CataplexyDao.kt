package myapp.net.inspire.data.dao

import android.arch.persistence.room.*
import myapp.net.inspire.data.entity.Cataplexy

/**
 * Created by Alucard on 2/22/2019.
 */

@Dao
interface CataplexyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cataplexy: Cataplexy): Long

    @Query("SELECT * FROM cataplexy_tbl")
    fun getAllCataplexy(): List<Cataplexy>

    @Query("SELECT * FROM cataplexy_tbl WHERE created_date=:date")
    fun getTodayCataplexy(date: String): Cataplexy

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cataplexy: Cataplexy)
}