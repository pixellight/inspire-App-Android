package myapp.net.inspire

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_the_inspire_program.*
import myapp.net.inspire.data.repository.Repository

class TheInspireProgram : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_inspire_program)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        title.text = "Learn"

        enrollBttn?.setOnClickListener {
            val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://inspire-program.com/enroll"))
            startActivity(browserIntent)
        }

        fullPrescribingInformationProgram?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Full Prescribing Information")
            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
            startActivity(intent)
        }

        importantSafetyInformationProgram?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Important Safety Information")
            intent.putExtra("url", "file:///android_asset/ISI/ISI.htm")
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        if (Repository().getIsNotificationTriggred(this@TheInspireProgram)!!) {
            startActivity(Intent(this@TheInspireProgram!!,MainActivity::class.java))
            finishAffinity()
        }
        super.onResume()
    }

}
