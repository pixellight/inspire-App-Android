package myapp.net.inspire.note

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_addnote.*
import myapp.net.inspire.R
import myapp.net.inspire.WebActivity
import myapp.net.inspire.data.entity.Note
import myapp.net.inspire.data.repository.NoteRepository
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.GeneralUtils

/**
 * Created by Alucard on 2/18/2019.
 */
class AddNoteFragment : AppCompatActivity() {

    private var mNoteRepository: NoteRepository? = null
    private var type: Int? = 0
    private var isUpdate: Boolean? = false
    private var note: Note? = null
    private var noteTitle: String? = DateTimeUtils.getCurrentTimeForNote()
    private var body: String? = null
    private var bundle: Bundle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_addnote)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        val titleRight = findViewById<TextView>(R.id.titleRight);
//        toolbar.visibility = View.VISIBLE
//        val bottomUp = (this@AddNoteFragment!! as NotesActivity).findViewById<LinearLayout>(R.id.bottomUpLayout)
//        bottomUp.visibility = View.GONE

//        save?.text = "Save"
//        println("NOte" + arguments.getInt("type"))
        bundle = this.intent.extras
        type = bundle!!.getInt("type")
        isUpdate = bundle!!.getBoolean("isUpdate")

        if (isUpdate!!) {
            note = bundle!!.getParcelable("note")
            body = note!!.body
            noteTitle = note!!.title
            titleEditText?.setText(note!!.title)
            bodyEditText?.setText(note!!.body)
            title?.text = "Edit a note"
        } else {
            title?.text = "Add a note"
        }
        fullPrescribingInformationNote?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Full Prescribing Information")
            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
            startActivity(intent)

        }

        importantSafetyInformationNote?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Important Safety Information")
            intent.putExtra("url", "file:///android_asset/ISI/ISI.htm")
            startActivity(intent)
        }

        titleEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                noteTitle = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                noteTitle = s.toString()
            }

        })

        bodyEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                body = s.toString()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                body = s.toString()
            }

        })
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                try {
                    onBackPressed()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        try {
            noteTitle = titleEditText?.text.toString().trim()
            body = bodyEditText?.text.toString().trim()
            if(noteTitle.isNullOrEmpty()){
             noteTitle=DateTimeUtils.getCurrentTimeForNote()
            }
            if (TextUtils.isEmpty(noteTitle!!) && TextUtils.isEmpty(body)) {
                Toast.makeText(this@AddNoteFragment, "Empty notes!!", Toast.LENGTH_LONG).show()
            } else {
                mNoteRepository = NoteRepository(this@AddNoteFragment)
                if (isUpdate!!) {
                    var note = Note(id = note!!.id, title = noteTitle!!, body = body!!, type = type!!, createdDate = null, modifiedDate = DateTimeUtils.getCurrentDate())
                    mNoteRepository!!.updateNote(note)
                } else {
                    var note = Note(id = null, title = noteTitle!!, body = body!!, type = type!!, createdDate = DateTimeUtils.getCurrentDate(), modifiedDate = null)
                    mNoteRepository!!.insertNote(note)


                }

                GeneralUtils.hideKeyboard(this@AddNoteFragment!!)
//                startActivity(Intent(this@AddNoteFragment, NotesActivity::class.java))
                finish()
            }
            super.onBackPressed()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}