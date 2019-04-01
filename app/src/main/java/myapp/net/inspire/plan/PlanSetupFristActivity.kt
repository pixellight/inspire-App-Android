package myapp.net.inspire.plan

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_plansetup.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.WebActivity
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by QPay on 2/12/2019.
 */
class PlanSetupFristActivity : AppCompatActivity() {
    var titleRight: TextView? = null
    var bottomView: LinearLayout? = null
    var titleLeft: TextView? = null

    companion object {
        val TAG = "PlanSetupFristActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppThemePlanSetup)
        setContentView(R.layout.activity_plansetup)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        var title = findViewById<TextView>(R.id.title)
        titleRight = findViewById<TextView>(R.id.titleRight)
        titleLeft = findViewById(R.id.titleLeft)
        bottomView = findViewById(R.id.bottomView)
        toolbar.visibility = View.VISIBLE
        title.text = "PLAN SETUP [1/10]"
        titleRight!!.text = ""
        titleLeft!!.text = getString(R.string.cancel)
        titleLeft!!.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        fullPrescribingInformationPlan?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Full Prescribing Information")
            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
            startActivity(intent)
        }

        importantSafetyInformationPlan?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Important Safety Information")
            intent.putExtra("url", "file:///android_asset/ISI/ISI.htm")
            startActivity(intent)
        }


        LoadFragment.addFragmentToBackStack(supportFragmentManager, PlanSetupFirstFragment(), R.id.container, TAG)
    }
}