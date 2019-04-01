package myapp.net.inspire.note

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.notes_doctor_loyout.*
import kotlinx.android.synthetic.main.notes_main.*
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.Note
import myapp.net.inspire.data.repository.NoteRepository
import myapp.net.inspire.note.adapter.NoteAdapter
import myapp.net.inspire.utils.GeneralUtils
import myapp.net.inspire.utils.NoteEnum


/**
 * Created by deadlydragger on 11/3/18.
 */
class NoteFragment : Fragment(), RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private var doctorList: List<Note>? = ArrayList()
    private var toDoList: List<Note>? = ArrayList()
    private var otherList: List<Note>? = ArrayList()
    private var mNoteRepository: NoteRepository? = null
    private var isDoctor: Boolean? = false
    private var isToDo: Boolean? = false
    private var isOther: Boolean? = false
    private var mAdapterDoctor: NoteAdapter? = null
    private var mAdapterToDo: NoteAdapter? = null
    private var mAdapterOther: NoteAdapter? = null

    companion object {
        var TAG = "NoteFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.notes_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var actionBar = (activity as NotesActivity).supportActionBar
        actionBar!!.setHomeButtonEnabled(false)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = (activity as NotesActivity).findViewById<TextView>(R.id.title)
        var save = (activity as NotesActivity).findViewById<TextView>(R.id.titleRight)
        title?.text = "Notes"
        save?.visibility = View.GONE
        val bottomUp = (activity as NotesActivity).findViewById<LinearLayout>(R.id.bottomUpLayout)
        bottomUp.visibility = View.VISIBLE

        view!!.findViewById<LinearLayout>(R.id.askQuestion).setOnClickListener {
            activity.startActivity(Intent(activity, EditNotesActivity::class.java))
        }
//        noteTab()

        addNoteLayoutDoctor?.setOnClickListener {
            //            var doctorAddNote = AddNoteFragment()
            var intent = Intent(activity!!, AddNoteFragment::class.java)
            var bundle = Bundle()
            bundle.putInt("type", 0)
            bundle.putBoolean("isUpdate", false)
            intent.putExtras(bundle)
//            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, doctorAddNote, R.id.fragmennt, TAG)
            startActivity(intent)
//            activity!!.finish()
        }

        addNoteLayoutToDo?.setOnClickListener {
            //            var toDoAddNote = AddNoteFragment()
            var intent = Intent(activity!!, AddNoteFragment::class.java)
            var toDoBundle = Bundle()
            toDoBundle.putInt("type", 1)
            toDoBundle.putBoolean("isUpdate", false)
            intent.putExtras(toDoBundle)
            startActivity(intent)
//            activity!!.finish()
        }

        addNoteLayoutOther?.setOnClickListener {
            //            var otherAddNote = AddNoteFragment()
            var intent = Intent(activity!!, AddNoteFragment::class.java)
            var otherBundle = Bundle()
            otherBundle.putInt("type", 2)
            otherBundle.putBoolean("isUpdate", false)
            intent.putExtras(otherBundle)
            startActivity(intent)
//            activity!!.finish()
        }



        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(doctorNoteRecyclerView)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(toDoNoteRecyclerView)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(otherNoteRecyclerView)


    }

    fun noteTab() {
        mNoteRepository = NoteRepository(activity)
        loadDoctorNotes(mNoteRepository!!)
        mNoteRepository = NoteRepository(activity)
        loadToDoNotes(mNoteRepository!!)
        mNoteRepository = NoteRepository(activity)
        loadOtherNotes(mNoteRepository!!)
        doctorTextView?.setOnClickListener {
            doctorTextView?.setBackgroundColor(GeneralUtils.getColorWrapper(activity, R.color.planBackground))
            toDoTextView?.setBackgroundColor(GeneralUtils.getColorWrapper(activity, R.color.white))
            otherTextView?.setBackgroundColor(GeneralUtils.getColorWrapper(activity, R.color.white))

            addNoteLayoutDoctor?.visibility = View.VISIBLE
            addNoteLayoutToDo?.visibility = View.GONE
            addNoteLayoutOther?.visibility = View.GONE

            doctorTextView?.setTextColor(Color.WHITE)
            toDoTextView?.setTextColor(Color.BLACK)
            otherTextView?.setTextColor(Color.BLACK)

            isDoctor = true
            isToDo = false
            isOther = false

            doctorNoteRecyclerView?.visibility = View.VISIBLE
            toDoNoteRecyclerView?.visibility = View.GONE
            otherNoteRecyclerView?.visibility = View.GONE
            mNoteRepository = NoteRepository(activity)
            loadDoctorNotes(mNoteRepository!!)

        }


        toDoTextView?.setOnClickListener {
            doctorTextView?.setBackgroundColor(GeneralUtils.getColorWrapper(activity, R.color.white))
            toDoTextView?.setBackgroundColor(GeneralUtils.getColorWrapper(activity, R.color.planBackground))
            otherTextView?.setBackgroundColor(GeneralUtils.getColorWrapper(activity, R.color.white))

            addNoteLayoutDoctor?.visibility = View.GONE
            addNoteLayoutToDo?.visibility = View.VISIBLE
            addNoteLayoutOther?.visibility = View.GONE

            doctorTextView?.setTextColor(Color.BLACK)
            toDoTextView?.setTextColor(Color.WHITE)
            otherTextView?.setTextColor(Color.BLACK)

            isDoctor = false
            isToDo = true
            isOther = false

            doctorNoteRecyclerView?.visibility = View.GONE
            toDoNoteRecyclerView?.visibility = View.VISIBLE
            otherNoteRecyclerView?.visibility = View.GONE
            mNoteRepository = NoteRepository(activity)
            loadToDoNotes(mNoteRepository!!)
        }

        otherTextView?.setOnClickListener {
            doctorTextView?.setBackgroundColor(GeneralUtils.getColorWrapper(activity, R.color.white))
            toDoTextView?.setBackgroundColor(GeneralUtils.getColorWrapper(activity, R.color.white))
            otherTextView?.setBackgroundColor(GeneralUtils.getColorWrapper(activity, R.color.planBackground))

            addNoteLayoutDoctor?.visibility = View.GONE
            addNoteLayoutToDo?.visibility = View.GONE
            addNoteLayoutOther?.visibility = View.VISIBLE
            isDoctor = false
            isToDo = false
            isOther = true


            doctorTextView?.setTextColor(Color.BLACK)
            toDoTextView?.setTextColor(Color.BLACK)
            otherTextView?.setTextColor(Color.WHITE)

            doctorNoteRecyclerView?.visibility = View.GONE
            toDoNoteRecyclerView?.visibility = View.GONE
            otherNoteRecyclerView?.visibility = View.VISIBLE
            mNoteRepository = NoteRepository(activity)
            loadOtherNotes(mNoteRepository!!)
        }
    }


    override fun onResume() {
        noteTab()
        super.onResume()
        if (view == null) {
            return
        }

        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()

        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return if (event!!.getAction() === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    activity!!.finish()
                    true
                } else false
            }

        })

    }

    fun loadDoctorNotes(noteRepository: NoteRepository) {
        if (noteRepository != null) {
            doctorNoteRecyclerView?.layoutManager = LinearLayoutManager(activity)
            doctorNoteRecyclerView?.itemAnimator = DefaultItemAnimator()
            doctorNoteRecyclerView?.isNestedScrollingEnabled = false

            isDoctor = true
            isToDo = false
            isOther = false

            doctorList = noteRepository.getAllNoteDoctor(NoteEnum.DOCTOR.ordinal)
            mAdapterDoctor = NoteAdapter(doctorList!!.toMutableList(), activity, object : NoteAdapter.NoteCallback {
                override fun onNoteClickListener(note: Note) {
                    var intent = Intent(activity!!, AddNoteFragment::class.java)
                    var bundle = Bundle()
                    bundle.putInt("type", note.type!!)
                    bundle.putBoolean("isUpdate", true)
                    bundle.putParcelable("note", note)
                    intent.putExtras(bundle)
                    startActivity(intent)
//                    activity!!.finish()

                }

            })
            doctorNoteRecyclerView?.adapter = mAdapterDoctor

//            var itemTouchHelpeCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
//            ItemTouchHelper(itemTouchHelpeCallback).attachToRecyclerView(doctorNoteRecyclerView)

        }
    }

    private fun loadToDoNotes(noteRepository: NoteRepository) {
        toDoNoteRecyclerView?.layoutManager = LinearLayoutManager(activity)
        toDoNoteRecyclerView?.itemAnimator = DefaultItemAnimator()
        toDoNoteRecyclerView?.isNestedScrollingEnabled = false
        toDoList = noteRepository.getAllNoteDoctor(NoteEnum.TODO.ordinal)
        mAdapterToDo = NoteAdapter(toDoList!!.toMutableList(), activity, object : NoteAdapter.NoteCallback {
            override fun onNoteClickListener(note: Note) {

                var intent = Intent(activity!!, AddNoteFragment::class.java)
                var toDoBundle = Bundle()
                toDoBundle.putInt("type", note.type!!)
                toDoBundle.putBoolean("isUpdate", true)
                toDoBundle.putParcelable("note", note)
                intent.putExtras(toDoBundle)
                startActivity(intent)
//                activity!!.finish()

            }

        })
        toDoNoteRecyclerView?.adapter = mAdapterToDo
    }

    private fun loadOtherNotes(noteRepository: NoteRepository) {
        otherNoteRecyclerView?.layoutManager = LinearLayoutManager(activity)
        otherNoteRecyclerView?.itemAnimator = DefaultItemAnimator()
        otherNoteRecyclerView?.isNestedScrollingEnabled = false
        otherList = noteRepository.getAllNoteDoctor(NoteEnum.OTHER.ordinal)
        mAdapterOther = NoteAdapter(otherList!!.toMutableList(), activity, object : NoteAdapter.NoteCallback {
            override fun onNoteClickListener(note: Note) {

                var intent = Intent(activity!!, AddNoteFragment::class.java)
                var otherBundle = Bundle()
                otherBundle.putInt("type", note.type!!)
                otherBundle.putBoolean("isUpdate", true)
                otherBundle.putParcelable("note", note)
                intent.putExtras(otherBundle)
                startActivity(intent)
//                activity!!.finish()

            }

        })

        otherNoteRecyclerView?.adapter = mAdapterOther
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is NoteAdapter.NoteViewHolder) {
            // get the removed item name to display it in snack bar
//            val name = doctorList!!.get(viewHolder.adapterPosition).title

            // backup of removed item for undo purpose
//            val deletedItem = doctorList!!.get(viewHolder.adapterPosition)
//            val deletedIndex = viewHolder.adapterPosition

            try {
                var note: Note? = null
                if (isDoctor!!) {
                    note = doctorList?.get(viewHolder.adapterPosition)
                    dialogDelete(note!!, viewHolder.adapterPosition)

                }

                if (isToDo!!) {
                    note = toDoList?.get(viewHolder.adapterPosition)
                    dialogDelete(note!!, viewHolder.adapterPosition)

                }

                if (isOther!!) {
                    note = otherList?.get(viewHolder.adapterPosition)
                    dialogDelete(note!!, viewHolder.adapterPosition)

                }

            } catch (e: Exception) {

            }


        }

    }

    fun dialogDelete(note: Note, position: Int) {
        val builder = android.support.v7.app.AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val v = inflater.inflate(R.layout.dialog_delete_note, null)
        builder.setView(v)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        v.findViewById<TextView>(R.id.deleteNote).setOnClickListener(View.OnClickListener {
            // remove the item from recycler view
            if (isDoctor!!) {
                mNoteRepository!!.deleteNoteById(doctorList!!.get(position).id!!)
                mNoteRepository = NoteRepository(activity)
                doctorList = mNoteRepository!!.getAllNoteDoctor(NoteEnum.DOCTOR.ordinal)
                doctorNoteRecyclerView?.adapter = NoteAdapter(doctorList!!.toMutableList(), activity, object : NoteAdapter.NoteCallback {
                    override fun onNoteClickListener(note: Note) {
                        var intent = Intent(activity!!, AddNoteFragment::class.java)
                        var bundle = Bundle()
                        bundle.putInt("type", note.type!!)
                        bundle.putBoolean("isUpdate", true)
                        bundle.putParcelable("note", note)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        activity!!.finish()
                    }

                })
                doctorNoteRecyclerView!!.invalidate()
            }

            if (isToDo!!) {
                mNoteRepository!!.deleteNoteById(toDoList!!.get(position).id!!)
                mNoteRepository = NoteRepository(activity)
                toDoList = mNoteRepository!!.getAllNoteToDo(NoteEnum.TODO.ordinal)
                toDoNoteRecyclerView?.adapter = NoteAdapter(toDoList!!.toMutableList(), activity, object : NoteAdapter.NoteCallback {
                    override fun onNoteClickListener(note: Note) {
                        var intent = Intent(activity!!, AddNoteFragment::class.java)
                        var toDoBundle = Bundle()
                        toDoBundle.putInt("type", note.type!!)
                        toDoBundle.putBoolean("isUpdate", true)
                        toDoBundle.putParcelable("note", note)
                        intent.putExtras(toDoBundle)
                        startActivity(intent)
                        activity!!.finish()
                    }

                })
                toDoNoteRecyclerView!!.invalidate()
            }

            if (isOther!!) {
                mNoteRepository!!.deleteNoteById(otherList!!.get(position).id!!)
                mNoteRepository = NoteRepository(activity)
                otherList = mNoteRepository!!.getAllNoteOther(NoteEnum.OTHER.ordinal)
                otherNoteRecyclerView?.adapter = NoteAdapter(otherList!!.toMutableList(), activity, object : NoteAdapter.NoteCallback {
                    override fun onNoteClickListener(note: Note) {
                        var intent = Intent(activity!!, AddNoteFragment::class.java)
                        var otherBundle = Bundle()
                        otherBundle.putInt("type", note.type!!)
                        otherBundle.putBoolean("isUpdate", true)
                        otherBundle.putParcelable("note", note)
                        intent.putExtras(otherBundle)
                        startActivity(intent)
                        activity!!.finish()
                    }

                })
                otherNoteRecyclerView!!.invalidate()
            }
//            mAdapterDoctor!!.removeItem(position)

            dialog.dismiss()
        })
        v.findViewById<TextView>(R.id.cancel).setOnClickListener {
            mNoteRepository = NoteRepository(activity)

            if (isDoctor!!) {

                doctorList = mNoteRepository!!.getAllNoteDoctor(NoteEnum.DOCTOR.ordinal)
                mAdapterDoctor = NoteAdapter(doctorList!!.toMutableList(), activity, object : NoteAdapter.NoteCallback {
                    override fun onNoteClickListener(note: Note) {
                        var intent = Intent(activity!!, AddNoteFragment::class.java)
                        var bundle = Bundle()
                        bundle.putInt("type", note.type!!)
                        bundle.putBoolean("isUpdate", true)
                        bundle.putParcelable("note", note)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        activity!!.finish()
                    }

                })
                doctorNoteRecyclerView?.adapter = mAdapterDoctor
                mAdapterDoctor!!.notifyDataSetChanged()
                doctorNoteRecyclerView!!.invalidate()

            }

            if (isToDo!!) {
                toDoList = mNoteRepository!!.getAllNoteToDo(NoteEnum.TODO.ordinal)
                mAdapterToDo = NoteAdapter(toDoList!!.toMutableList(), activity, object : NoteAdapter.NoteCallback {
                    override fun onNoteClickListener(note: Note) {
                        var intent = Intent(activity!!, AddNoteFragment::class.java)
                        var toDoBundle = Bundle()
                        toDoBundle.putInt("type", note.type!!)
                        toDoBundle.putBoolean("isUpdate", true)
                        toDoBundle.putParcelable("note", note)
                        intent.putExtras(toDoBundle)
                        startActivity(intent)
                        activity!!.finish()
                    }

                })
                toDoNoteRecyclerView?.adapter = mAdapterToDo
                mAdapterToDo!!.notifyDataSetChanged()
                toDoNoteRecyclerView!!.invalidate()
            }

            if (isOther!!) {
                otherList = mNoteRepository!!.getAllNoteOther(NoteEnum.OTHER.ordinal)
                mAdapterOther = NoteAdapter(otherList!!.toMutableList(), activity, object : NoteAdapter.NoteCallback {
                    override fun onNoteClickListener(note: Note) {
                        var intent = Intent(activity!!, AddNoteFragment::class.java)
                        var otherBundle = Bundle()
                        otherBundle.putInt("type", note.type!!)
                        otherBundle.putBoolean("isUpdate", true)
                        otherBundle.putParcelable("note", note)
                        intent.putExtras(otherBundle)
                        startActivity(intent)
                        activity!!.finish()
                    }

                })
                otherNoteRecyclerView?.adapter = mAdapterOther
                mAdapterOther!!.notifyDataSetChanged()
                otherNoteRecyclerView!!.invalidate()
            }

            dialog.dismiss()
        }
        dialog.show()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        val density = activity.resources.displayMetrics.density
        lp.width = (300 * density).toInt()
        lp.height = (325 * density).toInt()
        lp.gravity = Gravity.CENTER
        dialog.window!!.attributes = lp
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                activity!!.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }




}