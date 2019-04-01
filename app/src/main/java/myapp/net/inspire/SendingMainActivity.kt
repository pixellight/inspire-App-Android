package myapp.net.inspire

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView

/**
 * Created by deadlydragger on 10/28/18.
 */
class SendingMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.mail_report)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        val titleRight= findViewById<TextView>(R.id.titleRight);
        toolbar.visibility = View.VISIBLE
        title.text = "inspire+Report"
        titleRight.text="Send"
//        LoadFragment().addFragmentToActivity(supportFragmentManager, TodaysProgressFragment(),R.id.fragmennt)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}