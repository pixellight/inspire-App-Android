package myapp.net.inspire.note

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_progress.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.WebActivity
import myapp.net.inspire.data.entity.Note
import myapp.net.inspire.data.repository.NoteRepository
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.progress.ProgressInfoActivity
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by deadlydragger on 11/3/18.
 */
class NotesActivity : AppCompatActivity() {

    companion object {
        var TAG = "NotesActivity"
    }

    private var mNoteRepository: NoteRepository? = null
    private var isDefultNoteSaved: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_progress)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        val titleRight = findViewById<TextView>(R.id.titleRight);
        toolbar.visibility = View.VISIBLE
        title.text = "NOTES"
        val bottomUp = findViewById<LinearLayout>(R.id.bottomUpLayout)
        bottomUp.visibility = View.VISIBLE
        LoadFragment().addFragmentToActivity(supportFragmentManager, NoteFragment(), R.id.fragmennt)
        titleRight.setOnClickListener {
            startActivity(Intent(this, ProgressInfoActivity::class.java))
        }
        fullPrescribingInformationTrack?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Full Prescribing Information")
            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
            startActivity(intent)

        }

        importantSafetyInformationTrack?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Important Safety Information")
            intent.putExtra("url", "file:///android_asset/ISI/ISI.htm")
            startActivity(intent)
        }

        if (!Repository().getIsDefaultNoteSave(this@NotesActivity)!!) {
            mNoteRepository = NoteRepository(this@NotesActivity)
            var noteTitle = "Questions to ask"
            var body = String.format(getString(R.string.question))
            var type = 0

            var note = Note(id = null, title = noteTitle!!, body = body!!, type = type!!, createdDate = DateTimeUtils.getCurrentDate(), modifiedDate = null)
            mNoteRepository!!.insertNote(note)

            noteTitle = "Things to tell your doctor"
            body = String.format(getString(R.string.things_to_tell_your_doctor))
            var note2 = Note(id = null, title = noteTitle!!, body = body!!, type = type!!, createdDate = DateTimeUtils.getCurrentDate(), modifiedDate = null)
            mNoteRepository!!.insertNote(note2)

            Repository().setIsDefaultNoteSave(this@NotesActivity, true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        if (Repository().getIsNotificationTriggred(this@NotesActivity)!!) {
            startActivity(Intent(this@NotesActivity!!, MainActivity::class.java))
            finishAffinity()
        }
        super.onResume()
    }
}