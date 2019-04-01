package myapp.net.inspire.note

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import myapp.net.inspire.R
import myapp.net.inspire.progress.ProgressInfoActivity
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by deadlydragger on 11/3/18.
 */

class EditNotesActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_progress)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        val titleRight= findViewById<TextView>(R.id.titleRight);
        toolbar.visibility = View.VISIBLE
        title.text = "EDIT NOTE"
        titleRight.text=""
        val bottomUp = findViewById<LinearLayout>(R.id.bottomUp)
        bottomUp.visibility= View.GONE
        LoadFragment().addFragmentToActivity(supportFragmentManager, EditFragment(), R.id.fragmennt)
        titleRight.setOnClickListener {
            startActivity(Intent(this, ProgressInfoActivity::class.java))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
