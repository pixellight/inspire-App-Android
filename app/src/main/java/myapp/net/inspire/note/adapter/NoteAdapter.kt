package myapp.net.inspire.note.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_note.view.*
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.Note
import android.R.attr.data



/**
 * Created by Alucard on 2/18/2019.
 */
class NoteAdapter(val noteList: MutableList<Note>, val context: Context, val callback: NoteCallback) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        var note = noteList.get(position)
        if (note != null) {
            holder!!.noteDate?.text = note.createdDate!!.split(" ")[0]

            if (TextUtils.isEmpty(note.title)) {
                holder!!.title?.text = "Untitled note"
            } else {
                holder!!.title?.text = note.title

            }
            holder!!.itemLayout?.setOnClickListener {
                callback?.onNoteClickListener(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent!!.context!!).inflate(R.layout.item_note, parent, false))
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun removeItem(position: Int) {
        noteList!!.removeAt(position)
        notifyItemRemoved(position)
//        notifyItemRangeChanged(position,noteList.size)
    }

    fun restoreItem(note: Note, position: Int) {
        noteList!!.add(position, note)
        notifyItemInserted(position)
    }

    fun getData(): MutableList<Note> {
        return noteList!!
    }


    fun updateDate(notes:MutableList<Note>){

    }

    class NoteViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val title = itemView?.titleNote
        val noteDate = itemView?.noteDate
        val itemLayout = itemView?.itemLayout
    }

    interface NoteCallback {
        fun onNoteClickListener(note: Note)
    }
}