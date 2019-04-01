package myapp.net.inspire.more

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_version.*
import myapp.net.inspire.BuildConfig
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository

/**
 * Created by QPay on 2/28/2019.
 */
class VersionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppThemePlanSetup)
        setContentView(R.layout.fragment_version)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        toolbar.visibility = View.VISIBLE
        title.text = "More"
        val versionName = BuildConfig.VERSION_NAME
        versionTextView?.text = versionName
        builTextView?.text = "20192203"+versionName
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

    override fun onResume() {
        if (Repository().getIsNotificationTriggred(this@VersionActivity)!!) {
            startActivity(Intent(this@VersionActivity!!, MainActivity::class.java))
            finishAffinity()
        }
        super.onResume()
    }
}