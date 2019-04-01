package myapp.net.inspire.progress

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_progress.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.WebActivity
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.fragment.TodaysProgressFragment
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by deadlydragger on 10/27/18.
 */
class ProgressActivity : AppCompatActivity() {
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
        title.text = "TODAY'S PROGRESS"
        titleRight.text = "Info"
        bottomUpLayout?.visibility = View.VISIBLE


        LoadFragment().addFragmentToActivity(supportFragmentManager, TodaysProgressFragment(), R.id.fragmennt)
        titleRight.setOnClickListener {
            Log.d("TYPE", Repository().getIsPlanSetup(this).toString())
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
        if (Repository().getIsNotificationTriggred(this@ProgressActivity)!!) {
            startActivity(Intent(this@ProgressActivity!!, MainActivity::class.java))
            finishAffinity()
        }
        super.onResume()
    }
}