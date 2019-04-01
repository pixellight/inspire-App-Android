package myapp.net.inspire.data.repository

import android.content.Context
import myapp.net.inspire.data.dao.NoteDao
import myapp.net.inspire.data.db.InspirePlusDatabase
import myapp.net.inspire.data.entity.Note
import myapp.net.inspire.utils.DateTimeUtils
import org.jetbrains.anko.doAsync

/**
 * Created by Alucard on 2/18/2019.
 */
class NoteRepository(context: Context) {

    private var mNoteDao: NoteDao? = null
//    private var mAllNap: List<Note>? = null

    init {
        var db = InspirePlusDatabase.getInstance(context)
        mNoteDao = db.noteDao()
//        mAllNap = mNoteDao!!.getAll()
    }

    fun getNoteById(id: Int): Note {
        return mNoteDao!!.getNoteById(id)
    }

    fun insertNote(note: Note) {
        doAsync {
          mNoteDao!!.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        mNoteDao!!.updateNote(note.id!!, note.title!!, note.body!!, DateTimeUtils.getCurrentDate()!!, note.type!!)
    }

    fun getAllNoteDoctor(id:Int): List<Note> {
        return mNoteDao!!.getAllDoctorNote(id)
    }
    fun getAllNoteToDo(id:Int): List<Note> {
        return mNoteDao!!.getAllTodNote(id)
    }
    fun getAllNoteOther(id:Int): List<Note> {
        return mNoteDao!!.getAllOtherNote(id)
    }

    fun deleteNoteById(id:Long){
        return mNoteDao!!.deleteNote(id)
    }
}