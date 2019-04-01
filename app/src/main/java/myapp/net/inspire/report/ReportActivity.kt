package myapp.net.inspire.report

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_report.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.WebActivity
import myapp.net.inspire.data.entity.EdsSeverity
import myapp.net.inspire.data.repository.EdsSeverityRepository
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.LoadFragment
import myapp.net.inspire.utils.PlanEnum
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * Created by deadlydragger on 10-28-18.
 */
class ReportActivity : AppCompatActivity() {
    private var mEdsSeverityRepository: EdsSeverityRepository? = null
    val format = SimpleDateFormat("yyyy-MM-dd")
    val formatMonth = SimpleDateFormat("MMMM")
    private var hashMap: HashMap<String, List<EdsSeverity>>? = null
    private var listEds: MutableList<EdsSeverity>? = null
    private var febEds: MutableList<EdsSeverity>? = null
    private var marEds: MutableList<EdsSeverity>? = null
    var week: Int = 0
    var woy = -1
    private var planFragment: PlanGraphFragment? = null
    private var progressFragment: ProgressGraphKFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_report)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)
        val titleRight = findViewById<TextView>(R.id.titleRight);
        toolbar.visibility = View.VISIBLE
        title.text = "REPORTS"
        titleRight.text = "Email"
//        savedata()
//        graphWork()
        titleRight.setOnClickListener {
            startActivity(Intent(this, EmailReportActivity::class.java))
        }

        if (Repository().getIsPlanSetup(this@ReportActivity) == PlanEnum.NOPLAN.ordinal) {
            noReportLayout?.visibility = View.VISIBLE
            mainLayoutGraphReport?.visibility = View.GONE
            toolbar.visibility = View.GONE
            doneBttnReport?.setOnClickListener {
                onBackPressed()
            }
        } else {
            mainLayoutGraphReport?.visibility = View.VISIBLE
            noReportLayout?.visibility = View.GONE
            if (planFragment == null) {
                planFragment = PlanGraphFragment()
                LoadFragment().addFragmentToActivity(supportFragmentManager, planFragment!!, R.id.fragmennt)

            }
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

        planGraph?.setOnClickListener {
            planGraph?.setBackgroundColor(ContextCompat.getColor(this, R.color.planBackground))
            progressGraph?.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            progressGraph?.setTextColor(Color.BLACK)
            planGraph?.setTextColor(Color.WHITE)
            if (planFragment == null) {
                planFragment = PlanGraphFragment()
                LoadFragment().addFragmentToActivity(supportFragmentManager, planFragment!!, R.id.fragmennt)

            } else {
                LoadFragment().addFragmentToActivity(supportFragmentManager, planFragment!!, R.id.fragmennt)
            }


        }

        progressGraph?.setOnClickListener {
            planGraph?.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            progressGraph?.setBackgroundColor(ContextCompat.getColor(this, R.color.planBackground))
            progressGraph?.setTextColor(Color.WHITE)
            planGraph?.setTextColor(Color.BLACK)
            if (progressFragment == null) {
                progressFragment = ProgressGraphKFragment()

                LoadFragment().addFragmentToActivity(supportFragmentManager, progressFragment!!, R.id.fragmennt)

            } else {
                LoadFragment().addFragmentToActivity(supportFragmentManager, progressFragment!!, R.id.fragmennt)

            }

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


    private fun graphWork() {

        try {
            mEdsSeverityRepository = EdsSeverityRepository(this@ReportActivity)
            var mEdsSeverityList = mEdsSeverityRepository!!.getAllData()

            hashMap = HashMap()
            febEds = ArrayList()
            listEds = ArrayList()
            marEds = ArrayList()
            for (i in 0..mEdsSeverityList.size - 1) {

                println("data:: " + formatMonth.format(format.parse(mEdsSeverityList.get(i).createdDate)))
                if (formatMonth.format(format.parse(mEdsSeverityList.get(i).createdDate)).equals("January", true)) {
                    var edsSeverity = EdsSeverity(mEdsSeverityList.get(i).id, mEdsSeverityList.get(i).questionOne, mEdsSeverityList.get(i).questionTwo, mEdsSeverityList.get(i).questionThree, mEdsSeverityList.get(i).questionFour,
                            mEdsSeverityList.get(i).questionFive, mEdsSeverityList.get(i).questionSix, mEdsSeverityList.get(i).questionSeven, mEdsSeverityList.get(i).questionEight, mEdsSeverityList.get(i).sum,
                            mEdsSeverityList.get(i).cataplexy, mEdsSeverityList.get(i).createdDate)
                    listEds!!.add(edsSeverity!!)
                    hashMap!!.put("January", listEds!!)

                } else if (formatMonth.format(format.parse(mEdsSeverityList.get(i).createdDate)).equals("February", true)) {
                    var edsSeverity = EdsSeverity(mEdsSeverityList.get(i).id, mEdsSeverityList.get(i).questionOne, mEdsSeverityList.get(i).questionTwo, mEdsSeverityList.get(i).questionThree, mEdsSeverityList.get(i).questionFour,
                            mEdsSeverityList.get(i).questionFive, mEdsSeverityList.get(i).questionSix, mEdsSeverityList.get(i).questionSeven, mEdsSeverityList.get(i).questionEight, mEdsSeverityList.get(i).sum,
                            mEdsSeverityList.get(i).cataplexy, mEdsSeverityList.get(i).createdDate)
                    febEds!!.add(edsSeverity!!)
                    hashMap!!.put("February", febEds!!)

                } else if (formatMonth.format(format.parse(mEdsSeverityList.get(i).createdDate)).equals("March", true)) {
                    var edsSeverity = EdsSeverity(mEdsSeverityList.get(i).id, mEdsSeverityList.get(i).questionOne, mEdsSeverityList.get(i).questionTwo, mEdsSeverityList.get(i).questionThree, mEdsSeverityList.get(i).questionFour,
                            mEdsSeverityList.get(i).questionFive, mEdsSeverityList.get(i).questionSix, mEdsSeverityList.get(i).questionSeven, mEdsSeverityList.get(i).questionEight, mEdsSeverityList.get(i).sum,
                            mEdsSeverityList.get(i).cataplexy, mEdsSeverityList.get(i).createdDate)
                    marEds!!.add(edsSeverity!!)
                    hashMap!!.put("March", marEds!!)

                }

            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun savedata() {
        var edsSeverityRepos = EdsSeverityRepository(this@ReportActivity)
        var edsSeverity = EdsSeverity(null, 0, 0, 0,
                0, 0, 0, 0,
                0, 8, 0, "2019-01-01 01:01")
        edsSeverityRepos.insertEdsSeverity(edsSeverity)

        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0, 0, 13, 1, "2019-01-03 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 11, 1, "2019-01-08 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 16, 3, "2019-01-09 01:01"))

        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 13, 3, "2019-01-12 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 16, 3, "2019-01-15 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 11, 3, "2019-01-18 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 16, 3, "2019-01-20 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 14, 3, "2019-01-21 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 16, 3, "2019-01-25 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 8, 3, "2019-01-26 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 15, 3, "2019-01-27 01:01"))


        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 14, 3, "2019-01-28 01:01"))


        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 11, 3, "2019-01-29 01:01"))


        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 9, 3, "2019-01-30 01:01"))










        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0, 0, 0,
                0, 0, 0, 0, 13, 1, "2019-02-02 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 11, 1, "2019-02-08 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 16, 3, "2019-02-09 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 2, 3, "2019-02-11 01:01"))

        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0, 0, 0,
                0, 0, 0, 0, 13, 1, "2019-03-09 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 11, 1, "2019-03-10 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 16, 3, "2019-03-11 01:01"))
        edsSeverityRepos.insertEdsSeverity(EdsSeverity(null, 0, 0,
                0, 0, 0, 0, 0,
                0, 2, 3, "2019-03-12 01:01"))


    }

    fun getWeekOfYear(date: Date): Int {
        var calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }
    override fun onResume() {
        if (Repository().getIsNotificationTriggred(this@ReportActivity)!!) {
            startActivity(Intent(this@ReportActivity!!, MainActivity::class.java))
            finishAffinity()
        }
        super.onResume()
    }

}