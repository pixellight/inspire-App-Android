package myapp.net.inspire.quiz

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main_quiz.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.WebActivity
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.LoadFragment

class QuizActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_quiz)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        toolbar.visibility = View.VISIBLE
        title.text = "Learn"
        toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        LoadFragment().addFragmentToActivity(supportFragmentManager!!, QuizStartFragment(), R.id.containerQuzi)


        fullPrescribingInformationQuizs?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Full Prescribing Information")
            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
            startActivity(intent)
        }

        importantSafetyInformationQuizs?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Important Safety Information")
            intent.putExtra("url", "file:///android_asset/ISI/ISI.htm")
            startActivity(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onResume() {
        if (Repository().getIsNotificationTriggred(this@QuizActivity)!!) {
            startActivity(Intent(this@QuizActivity!!, MainActivity::class.java))
            finishAffinity()
        }
        super.onResume()
    }
}
