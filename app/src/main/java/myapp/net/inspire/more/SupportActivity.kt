package myapp.net.inspire.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_support.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.WebActivity
import myapp.net.inspire.data.repository.Repository


/**
 * Created by QPay on 3/26/2019.
 */
class SupportActivity : AppCompatActivity() {
    private var checked: Boolean? = false
    private var subject= arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        val titleRight = findViewById<TextView>(R.id.titleRight)
        toolbar.visibility = View.VISIBLE
        title.text = "More"

        planQuestion?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                emailBttn?.isEnabled = true
//                progressQuestion?.isChecked = false
//                reportQuestion?.isChecked = false
                checked = true
                subject.add(0,buttonView?.text.toString())
            } else {
//                checked = false
//                subject=""
                subject.removeAt(0)
            }
        }

        progressQuestion?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                emailBttn?.isEnabled = true
//                planQuestion?.isChecked = false
//                reportQuestion?.isChecked = false
                checked = true
                subject.add(1,buttonView?.text.toString())
            } else {
//                checked = false
//                subject=""
                subject.removeAt(1)
            }
        }

        reportQuestion?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                emailBttn?.isEnabled = true
//                planQuestion?.isChecked = false
//                progressQuestion?.isChecked = false
                checked = true
                subject.add(2,buttonView?.text.toString())
            } else {
//                checked = false
//                subject=""
                subject.removeAt(2)

            }
        }


        emailBttn?.setOnClickListener {
            if (checked!!){
                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", getString(R.string.app_email), null))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Request")
                if(subject.size==1){
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Need support with "+subject.get(0))

                }
                if(subject.size==2){
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Need support with "+subject.get(0)+" and "+subject.get(1))
                }
                if(subject.size==3){
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Need support with "+subject.get(0)+", "+subject.get(1)+" and "+subject.get(2))
                }
                startActivity(Intent.createChooser(emailIntent, "Send email"))
            }else{
              Toast.makeText(this@SupportActivity,"Please select your issues",Toast.LENGTH_LONG).show()
            }

        }

        fullPrescribeInformationSupport?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Full Prescribing Information")
            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
            startActivity(intent)
        }

        importantSafetyInformationSupport?.setOnClickListener {
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
        if (Repository().getIsNotificationTriggred(this@SupportActivity)!!) {
            startActivity(Intent(this@SupportActivity!!, MainActivity::class.java))
            finishAffinity()
        }
        super.onResume()
    }
}