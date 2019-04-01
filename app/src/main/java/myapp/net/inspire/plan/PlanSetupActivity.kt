package myapp.net.inspire.plan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import myapp.net.inspire.R
import myapp.net.inspire.fragment.PlanFragment
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by deadlydragger on 11/18/18.
 */
class PlanSetupActivity : AppCompatActivity() {
    var titleRight: TextView? = null
    var bottomView : LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppThemePlanSetup)
        setContentView(R.layout.activity_plan)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
         titleRight= findViewById<TextView>(R.id.titleRight)
        bottomView=findViewById(R.id.bottomView)
        toolbar.visibility = View.VISIBLE
        title.text = "PLAN SETUP [2/2]"
        titleRight!!.text = "Done"
        LoadFragment().addFragmentToActivity(supportFragmentManager, PlanFragment(), R.id.fragmennt)


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