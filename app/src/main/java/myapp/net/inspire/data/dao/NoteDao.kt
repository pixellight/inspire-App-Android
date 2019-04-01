package myapp.net.inspire.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import myapp.net.inspire.data.entity.Note

/**
 * Created by Alucard on 2/18/2019.
 */
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Query("SELECT * FROM note_tbl WHERE id=:id")
    fun getNoteById(id: Int): Note

    @Query("SELECT * FROM note_tbl WHERE type=:id")
    fun getAllDoctorNote(id: Int): List<Note>

    @Query("SELECT * FROM note_tbl WHERE type=:id")
    fun getAllTodNote(id: Int): List<Note>

    @Query("SELECT * FROM note_tbl WHERE type=:id")
    fun getAllOtherNote(id: Int): List<Note>

    @Query("UPDATE note_tbl SET title=:title, body=:body, type=:type, modified_date=:modifyDate WHERE id=:id")
    fun updateNote(id: Long, title: String, body: String, modifyDate: String, type: Int)

    @Query("DELETE FROM note_tbl WHERE id=:id")
    fun deleteNote(id: Long)
}