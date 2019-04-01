package myapp.net.inspire

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import myapp.net.inspire.data.entity.EatByHistory
import myapp.net.inspire.data.entity.PlanHistoryAll
import myapp.net.inspire.data.repository.EatByRepository
import myapp.net.inspire.data.repository.PlanHistoryAllRepository
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.fragment.LearnFragment
import myapp.net.inspire.fragment.LearnTutorialActivity
import myapp.net.inspire.fragment.MainFragment
import myapp.net.inspire.fragment.TrackFragment
import myapp.net.inspire.more.MoreFragment
import myapp.net.inspire.notification.HandleNotificationActivity
import myapp.net.inspire.plan.PlanSetupFristActivity
import myapp.net.inspire.plan.PlanTutorialActivity
import myapp.net.inspire.track.TrackTutorialActivity
import myapp.net.inspire.utils.*

class MainActivity : AppCompatActivity() {
    var titleRight: TextView? = null
    var flag: String? = null
    private var manager: NotificationManager? = null
    private var toolbar:Toolbar?=null

    companion object {
        var TAG = "MainActivity"
    }

    private var isPlanSetup: Int? = 0

    var toolbarTitle: String? = null
    var mTitle: String? = null
    var des: String? = null
    var currentTime: String? = null
    var notificationId: Int? = 0
    var mEatByHistory: EatByRepository? = null
    private var mPlanHistoryAllRepository: PlanHistoryAllRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        try {
            try{
                MediaPlayerUtils.stopPlaying()

            }catch (e:Exception){
                e.printStackTrace()
            }
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            sendBroadcast(it)
            manager!!.cancel(Constants.REFILL_NOTIFICATION_ID!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        bottomView?.visibility=View.GONE
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        var title = findViewById<TextView>(R.id.title)
        titleRight = findViewById(R.id.titleRight)
        isPlanSetup = Repository().getIsPlanSetup(this)
        if ((isPlanSetup == PlanEnum.NOPLAN.ordinal)) {
            toolbar!!.visibility = View.GONE
            title.text = "Learn"
            titleLeft?.text = ""

        } else if ((isPlanSetup == PlanEnum.PLANSUSPENDED.ordinal)) {
            toolbar!!.visibility = View.VISIBLE
            title.text = "Today's Plan"
            titleLeft?.text = ""
        } else {
            toolbar!!.visibility = View.VISIBLE
            title.text = "Today's Plan"

        }

        DateTimeUtils.increaseTimeByOne("3:04 PM")


        var loadImage = findViewById<ImageView>(R.id.loadImage)
        var loadLearn = findViewById<TextView>(R.id.loadText)

        var imagePlane = findViewById<ImageView>(R.id.imagePlane)
        var textPlane = findViewById<TextView>(R.id.textPlane)

        var imageTrack = findViewById<ImageView>(R.id.imageTrack)
        var textTrack = findViewById<TextView>(R.id.textTrack)

        var imageMore = findViewById<ImageView>(R.id.imageMore)
        var textMore = findViewById<TextView>(R.id.moreText)
        try {
            flag = intent.extras.getString("fragment")
            if (flag.equals("track", true)) {
                bottomView!!.visibility = View.GONE

                textMore.setTextColor(resources.getColor(R.color.imageTint))
                imageMore.setColorFilter(ContextCompat.getColor(this,
                        R.color.imageTint))

                loadLearn.setTextColor(resources.getColor(R.color.imageTint))
                loadImage.setColorFilter(ContextCompat.getColor(this,
                        R.color.imageTint))

                textPlane.setTextColor(resources.getColor(R.color.imageTint))
                imagePlane.setColorFilter(ContextCompat.getColor(this,
                        R.color.imageTint))

                textTrack.setTextColor(resources.getColor(R.color.clickText))
                imageTrack.setColorFilter(ContextCompat.getColor(this,
                        R.color.clickText))

                titleRight!!.text = null
                toolbar!!.visibility = View.VISIBLE
                title.text = "Track"
                titleLeft?.text = ""
                bottomView?.visibility=View.VISIBLE
                LoadFragment().addFragmentToActivity(supportFragmentManager, TrackFragment(), R.id.fragmennt)
            } else if (flag.equals("plan", true)) {
                startActivity(Intent(this, PlanSetupFristActivity::class.java))
            } else if (flag.equals("learn", true)) {
                bottomView!!.visibility = View.VISIBLE

                textMore.setTextColor(resources.getColor(R.color.imageTint))
                imageMore.setColorFilter(ContextCompat.getColor(this,
                        R.color.imageTint))

                textPlane.setTextColor(resources.getColor(R.color.imageTint))
                imagePlane.setColorFilter(ContextCompat.getColor(this,
                        R.color.imageTint))

                textTrack.setTextColor(resources.getColor(R.color.imageTint))
                imageTrack.setColorFilter(ContextCompat.getColor(this,
                        R.color.imageTint))

                loadLearn.setTextColor(resources.getColor(R.color.clickText))
                loadImage.setColorFilter(ContextCompat.getColor(this,
                        R.color.clickText))


                titleRight!!.text = null
                toolbar!!.visibility = View.VISIBLE
                title.text = "Learn"
                titleLeft?.text = ""
                bottomView?.visibility=View.VISIBLE
                LoadFragment().addFragmentToActivity(supportFragmentManager, LearnFragment(), R.id.fragmennt)

            } else {
                if (Repository().getIsNotificationTriggred(this@MainActivity)!!) {
                    toolbar!!.visibility=View.GONE
                    bottomView?.visibility=View.VISIBLE
                    var map = Repository().getNotificationHandler(this@MainActivity)
                    toolbarTitle = map.get("toolbarTitle")
                    mTitle = map.get("title")
                    des = map.get("des")
                    currentTime = map.get("time")
                    notificationId = map.get("notifyId")!!.toInt()
                    val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                    sendBroadcast(it)
                    manager!!.cancel(notificationId!!)
                    var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())
                    if (des.equals("Eat by", ignoreCase = true)) {
                        mEatByHistory = EatByRepository(this@MainActivity!!)
                        var eatBy = EatByHistory(id = day!!.toLong(), eatBy = currentTime!!, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mEatByHistory!!.insertEatByHistory(eatBy)

                        try {
                            mPlanHistoryAllRepository = PlanHistoryAllRepository(this@MainActivity!!)
                            if (mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()) != null) {
                                var id = mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
                                var plan = PlanHistoryAll(id, "", "", "", "", "", "", currentTime!!, DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                                mPlanHistoryAllRepository!!.updateEatBy(plan)
                            } else {
                                var plan = PlanHistoryAll(null, "", "", "", "", "", "", currentTime!!, DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                                mPlanHistoryAllRepository!!.insertPlanHistoryAll(plan)
                            }
                            Repository().setIsNotificationTriggred(this@MainActivity, false)
                            bottomView?.visibility=View.GONE
                            LoadFragment().addFragmentToActivity(supportFragmentManager, MainFragment(), R.id.fragmennt)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }else{
//                        LoadFragment().addFragmentToActivity(supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                        bottomView?.visibility=View.GONE
                        LoadFragment().addFragmentToActivity(supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)

                    }

                }else{
                    bottomView?.visibility=View.GONE
                    LoadFragment().addFragmentToActivity(supportFragmentManager, MainFragment(), R.id.fragmennt)

                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (Repository().getIsNotificationTriggred(this@MainActivity)!!) {
                toolbar!!.visibility=View.GONE
                bottomView?.visibility=View.VISIBLE
                var map = Repository().getNotificationHandler(this@MainActivity)
                toolbarTitle = map.get("toolbarTitle")
                mTitle = map.get("title")
                des = map.get("des")
                currentTime = map.get("time")
                notificationId = map.get("notifyId")!!.toInt()
                val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                sendBroadcast(it)
                manager!!.cancel(notificationId!!)
                var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())
                if (des.equals("Eat by", ignoreCase = true)) {
                    mEatByHistory = EatByRepository(this@MainActivity!!)
                    var eatBy = EatByHistory(id = day!!.toLong(), eatBy = currentTime!!, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                    mEatByHistory!!.insertEatByHistory(eatBy)

                    try {
                        mPlanHistoryAllRepository = PlanHistoryAllRepository(this@MainActivity!!)
                        if (mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()) != null) {
                            var id = mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
                            var plan = PlanHistoryAll(id, "", "", "", "", "", "", currentTime!!, DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.updateEatBy(plan)
                        } else {
                            var plan = PlanHistoryAll(null, "", "", "", "", "", "", currentTime!!, DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.insertPlanHistoryAll(plan)
                        }
                        Repository().setIsNotificationTriggred(this@MainActivity, false)
                        bottomView?.visibility=View.GONE
                        LoadFragment().addFragmentToActivity(supportFragmentManager, MainFragment(), R.id.fragmennt)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
//                        LoadFragment().addFragmentToActivity(supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                    bottomView?.visibility=View.GONE
                    LoadFragment().addFragmentToActivity(supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)

                }
            }else{
                bottomView?.visibility=View.GONE
                LoadFragment().addFragmentToActivity(supportFragmentManager, MainFragment(), R.id.fragmennt)

            }
        }

        var bottomView = findViewById<LinearLayout>(R.id.bottomView)
        bottomView.visibility = View.VISIBLE




        findViewById<LinearLayout>(R.id.loadMore).setOnClickListener {

            bottomView.visibility = View.VISIBLE

            textMore.setTextColor(resources.getColor(R.color.clickText))
            imageMore.setColorFilter(ContextCompat.getColor(this,
                    R.color.clickText))

            loadLearn.setTextColor(resources.getColor(R.color.imageTint))
            loadImage.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            textPlane.setTextColor(resources.getColor(R.color.imageTint))
            imagePlane.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            textTrack.setTextColor(resources.getColor(R.color.imageTint))
            imageTrack.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            titleRight!!.text = null
            toolbar!!.visibility = View.VISIBLE
            title.text = "More"
            titleLeft?.text = ""

            toolbar!!.visibility=View.VISIBLE
            bottomView?.visibility=View.VISIBLE
            LoadFragment().addFragmentToActivity(supportFragmentManager, MoreFragment(), R.id.fragmennt)


        }
        findViewById<LinearLayout>(R.id.loadTrack).setOnClickListener {
            bottomView.visibility = View.GONE

            textMore.setTextColor(resources.getColor(R.color.imageTint))
            imageMore.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            loadLearn.setTextColor(resources.getColor(R.color.imageTint))
            loadImage.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            textPlane.setTextColor(resources.getColor(R.color.imageTint))
            imagePlane.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            textTrack.setTextColor(resources.getColor(R.color.clickText))
            imageTrack.setColorFilter(ContextCompat.getColor(this,
                    R.color.clickText))

            titleRight!!.text = null
            toolbar!!.visibility = View.VISIBLE
            title.text = "Track"
            titleLeft?.text = ""
            if (Repository().getIsFirstTimeTrack(this)!!) {
                startActivity(Intent(this, TrackTutorialActivity::class.java))

            } else {
                toolbar!!.visibility=View.VISIBLE
                bottomView?.visibility=View.VISIBLE
                LoadFragment().addFragmentToActivity(supportFragmentManager, TrackFragment(), R.id.fragmennt)
            }
//            LoadFragment().addFragmentToActivity(supportFragmentManager, TrackFragment(), R.id.fragmennt)

        }
        findViewById<LinearLayout>(R.id.loadLearn).setOnClickListener {
            bottomView.visibility = View.VISIBLE



            textMore.setTextColor(resources.getColor(R.color.imageTint))
            imageMore.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            textPlane.setTextColor(resources.getColor(R.color.imageTint))
            imagePlane.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            textTrack.setTextColor(resources.getColor(R.color.imageTint))
            imageTrack.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            loadLearn.setTextColor(resources.getColor(R.color.clickText))
            loadImage.setColorFilter(ContextCompat.getColor(this,
                    R.color.clickText))


            titleRight!!.text = null
            toolbar!!.visibility = View.VISIBLE
            title.text = "Learn"
            titleLeft?.text = ""

            if (Repository().getIsFirstTimeLearn(this)!!) {
                startActivity(Intent(this, LearnTutorialActivity::class.java))

            } else {
                toolbar!!.visibility=View.VISIBLE
                LoadFragment().addFragmentToActivity(supportFragmentManager, LearnFragment(), R.id.fragmennt)
            }

        }
        findViewById<LinearLayout>(R.id.loadPlane).setOnClickListener {

            textMore.setTextColor(resources.getColor(R.color.imageTint))
            imageMore.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            textPlane.setTextColor(resources.getColor(R.color.clickText))
            imagePlane.setColorFilter(ContextCompat.getColor(this,
                    R.color.clickText))

            textTrack.setTextColor(resources.getColor(R.color.imageTint))
            imageTrack.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))

            loadLearn.setTextColor(resources.getColor(R.color.imageTint))
            loadImage.setColorFilter(ContextCompat.getColor(this,
                    R.color.imageTint))


            if (Repository().getIsFirstTimePlan(this)!!) {
                startActivity(Intent(this, PlanTutorialActivity::class.java))
            } else {
                if (Repository().getIsNotificationTriggred(this@MainActivity)!!) {
                    toolbar!!.visibility=View.GONE
                    bottomView?.visibility=View.VISIBLE
                    LoadFragment().addFragmentToActivity(supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                } else {
                    isPlanSetup = Repository().getIsPlanSetup(this)
                    if ((isPlanSetup == PlanEnum.PLANSETUP.ordinal) || (isPlanSetup == PlanEnum.PLANSUSPENDED.ordinal)) {
                        bottomView?.visibility=View.GONE
                        LoadFragment().addFragmentToActivity(supportFragmentManager, MainFragment(), R.id.fragmennt)
                    } else {
                        bottomView?.visibility=View.GONE
                        startActivity(Intent(this, PlanSetupFristActivity::class.java))

                    }
                }

            }


        }

        fullPrescribeInformation?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Full Prescribing Information")
            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
            startActivity(intent)
        }

        importantSafetyInformation?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Important Safety Information")
            intent.putExtra("url", "file:///android_asset/ISI/ISI.htm")
            startActivity(intent)
        }

    }

    override fun onResume() {
        if ((isPlanSetup == PlanEnum.NOPLAN.ordinal)) {
            toolbar!!.visibility=View.GONE

        }


        findViewById<TextView>(R.id.cross).visibility = View.GONE
        titleLeft?.visibility = View.VISIBLE
        super.onResume()

    }

    fun getIsPlanSetup(): Int {
        return isPlanSetup!!
    }
}
