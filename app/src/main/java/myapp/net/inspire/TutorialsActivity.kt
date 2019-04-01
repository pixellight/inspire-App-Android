package myapp.net.inspire

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_tutorials.*
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.tutorial.*

class TutorialsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_tutorials)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        val titleRight = findViewById<TextView>(R.id.titleRight)
        toolbar.visibility = View.VISIBLE
        title.text = "More"

        inspirePlusTutorialLayout?.setOnClickListener {
            startActivity(Intent(this@TutorialsActivity, InspirePlusTutorialActivity::class.java))
        }

        inspirePlusV2TutorialLayout?.setOnClickListener {
            startActivity(Intent(this@TutorialsActivity, InspirePlusGeneralTutorialActivity::class.java))
        }

        learnTutorialLayout?.setOnClickListener {
            startActivity(Intent(this@TutorialsActivity, LearnTutorialMoreActivity::class.java))
        }

        planTutorialLayout?.setOnClickListener {
            startActivity(Intent(this@TutorialsActivity, PlanTutorialMoreActivity::class.java))
        }

        trackTutorialLayout?.setOnClickListener {
            startActivity(Intent(this@TutorialsActivity, TrackTutorialMoreActivity::class.java))
        }
        fullPrescribeInformationTutorial?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Full Prescribing Information")
            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
            startActivity(intent)
        }

        importantSafetyInformationTutorial?.setOnClickListener {
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
            android.R.id.home ->onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        if (Repository().getIsNotificationTriggred(this@TutorialsActivity)!!) {
            startActivity(Intent(this@TutorialsActivity!!,MainActivity::class.java))
           finishAffinity()
        }
        super.onResume()
    }
}
