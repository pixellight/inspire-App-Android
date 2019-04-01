package myapp.net.inspire.notification

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_handle_notification.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.*
import myapp.net.inspire.data.repository.*
import myapp.net.inspire.fragment.MainFragment
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.LoadFragment
import myapp.net.inspire.utils.MediaPlayerUtils


/**
 * Created by QPay on 2/24/2019.
 */
class HandleNotificationActivity() : Fragment() {
    var titleRight: TextView? = null
    var bottomView: LinearLayout? = null
    var titleLeft: TextView? = null

    var toolbarTitle: String? = null
    var mTitle: String? = null
    var des: String? = null
    var currentTime: String? = null
    var notificationId: Int? = 0

    var mEatByHistory: EatByRepository? = null
    var mNapHistory: NapHistoryRepository? = null
    var mDosHistory: DoseHistoryRepository? = null
    var mWakeUpHistory: WakeHistoryRepository? = null
    private var manager: NotificationManager? = null
    private var mPlanHistoryAllRepository: PlanHistoryAllRepository? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.activity_handle_notification, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {

            MediaPlayerUtils.stopPlaying()
            manager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        var intent = getIntent()

        if (Repository().getNotificationHandler(activity!!) != null) {
            try {
                var map = Repository().getNotificationHandler(activity!!)
                toolbarTitle = map.get("toolbarTitle")
                mTitle = map.get("title")
                des = map.get("des")
                currentTime = map.get("time")
                notificationId = map.get("notifyId")!!.toInt()
                val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                activity!!.sendBroadcast(it)
                if (notificationId == -1) {

                } else {
                    manager!!.cancel(notificationId!!)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
        var title = view!!.findViewById<TextView>(R.id.title)
        titleRight = view!!.findViewById<TextView>(R.id.titleRight)
        titleLeft = view!!.findViewById(R.id.titleLeft)
        bottomView = view!!.findViewById(R.id.bottomView)
        title.text = toolbarTitle
        titleRight!!.text = "Skip"
        titleLeft!!.setOnClickListener {
            (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE
            LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, MainFragment(), R.id.fragmennt)
        }
        titleRight!!.setOnClickListener {
            (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE
            LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, MainFragment(), R.id.fragmennt)
        }

        titleDes?.text = des
        titleReceiver?.text = mTitle


        println("title:: " + mTitle + " Desc:: " + des + " " + currentTime)
        var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())

        if (des.equals("Wake up", true)) {
            titleRight!!.visibility = View.GONE
            confirmLayout.visibility = View.GONE
            confirmLayoutWake.visibility = View.VISIBLE
        } else if (mTitle.equals("Dose 1", true)) {
            titleRight!!.visibility = View.GONE
            descriptionBelow?.visibility = View.VISIBLE
        } else if (mTitle.equals("Dose 2", true)) {
            titleRight!!.visibility = View.GONE
            descriptionBelow?.visibility = View.VISIBLE
        } else {
            confirmLayout.visibility = View.VISIBLE
            confirmLayoutWake.visibility = View.GONE
        }

        confirmLayoutWake?.setOnClickListener {
            if (des.equals("Wake up", ignoreCase = true)) {
                mWakeUpHistory = WakeHistoryRepository(activity!!)
                try {
                    Repository().setIsNotificationTriggred(activity!!, false)

                    mPlanHistoryAllRepository = PlanHistoryAllRepository(activity!!)
                    if (mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()) != null) {
                        var id = mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
                        var plan = PlanHistoryAll(id, currentTime!!, "", "", "", "", "", "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                        mPlanHistoryAllRepository!!.updateWake(plan)
                    } else {
                        var plan = PlanHistoryAll(null, currentTime!!, "", "", "", "", "", "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                        mPlanHistoryAllRepository!!.insertPlanHistoryAll(plan)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                var wake = WakeHistory(id = day!!.toLong(), wakeUp = currentTime!!, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day!!)
                mWakeUpHistory!!.insertWakeHistory(wake)
                (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE
                LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, MainFragment(), R.id.fragmennt)
            }
        }


        confirmLayout.setOnClickListener {
            try {
                Repository().setIsNotificationTriggred(activity!!, false)

                println("Day:: " + day)
                mDosHistory = DoseHistoryRepository(activity!!)
                mNapHistory = NapHistoryRepository(activity!!)
                var dose = mDosHistory!!.getDose(day!!)
                var nap = mNapHistory!!.getNap(day!!)
                if (mTitle.equals("Dose 1", ignoreCase = true)) {

                    if (dose != null) {
                        var id = dose.id
                        var dose = DoseHistory(id = id, doseOne = currentTime!!, doseTwo = dose.doseTwo!!, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mDosHistory!!.updateDoseOneHistory(dose)
                    } else {
                        var dose = DoseHistory(id = day!!.toLong(), doseOne = currentTime!!, doseTwo = ""!!, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mDosHistory!!.insertDoseHistory(dose)
                    }

                    try {
                        mPlanHistoryAllRepository = PlanHistoryAllRepository(activity!!)
                        if (mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()) != null) {
                            var id = mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
                            var plan = PlanHistoryAll(id, "", currentTime!!, "", "", "", "", "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.updateDoseOne(plan)
                        } else {
                            var plan = PlanHistoryAll(null, "", currentTime!!, "", "", "", "", "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.insertPlanHistoryAll(plan)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                if (mTitle.equals("Dose 2", ignoreCase = true)) {
                    if (dose != null) {
                        var id = dose.id
                        var dose = DoseHistory(id = id, doseOne = dose.doseOne!!, doseTwo = currentTime!!, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mDosHistory!!.updateDoseTwoHistory(dose)
                    } else {
                        var dose = DoseHistory(id = day!!.toLong(), doseOne = ""!!, doseTwo = currentTime!!, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mDosHistory!!.insertDoseHistory(dose)
                    }

                    try {
                        mPlanHistoryAllRepository = PlanHistoryAllRepository(activity!!)
                        if (mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()) != null) {
                            var id = mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
                            var plan = PlanHistoryAll(id, "", "", currentTime!!, "", "", "", "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.updateDoseTwo(plan)
                        } else {
                            var plan = PlanHistoryAll(null, "", "", currentTime!!, "", "", "", "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.insertPlanHistoryAll(plan)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                if (des.equals("Nap 1", ignoreCase = true)) {
                    if (nap != null) {
                        var id = nap.id
                        var nap = NapHistory(id = id, napOne = currentTime!!, napTwo = nap.napTwo!!, napThree = nap.napThree, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mNapHistory!!.updateNapOneHistory(nap)
                    } else {
                        var nap = NapHistory(id = day!!.toLong(), napOne = currentTime!!, napTwo = "", napThree = "", currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mNapHistory!!.insertNapHistory(nap)
                    }

                    try {
                        mPlanHistoryAllRepository = PlanHistoryAllRepository(activity!!)
                        if (mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()) != null) {
                            var id = mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
                            var plan = PlanHistoryAll(id, "", "", "", currentTime!!, "", "", "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.updateNapOne(plan)
                        } else {
                            var plan = PlanHistoryAll(null, "", "", "", currentTime!!, "", "", "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.insertPlanHistoryAll(plan)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                if (des.equals("Nap 2", ignoreCase = true)) {
                    if (nap != null) {
                        var id = nap.id
                        var nap = NapHistory(id = id, napOne = nap.napOne!!, napTwo = currentTime!!, napThree = nap.napThree, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mNapHistory!!.updateNapTwoHistory(nap)
                    } else {
                        var nap = NapHistory(id = day!!.toLong(), napOne = "", napTwo = currentTime!!, napThree = "", currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mNapHistory!!.insertNapHistory(nap)
                    }

                    try {
                        mPlanHistoryAllRepository = PlanHistoryAllRepository(activity!!)
                        if (mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()) != null) {
                            var id = mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
                            var plan = PlanHistoryAll(id, "", "", "", "", currentTime!!, "", "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.updateNapTwo(plan)
                        } else {
                            var plan = PlanHistoryAll(null, "", "", "", "", currentTime!!, "", "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.insertPlanHistoryAll(plan)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                if (des.equals("Nap 3", ignoreCase = true)) {
                    if (nap != null) {
                        var id = nap.id
                        var nap = NapHistory(id = id, napOne = nap.napOne!!, napTwo = nap.napTwo!!, napThree = currentTime!!, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mNapHistory!!.updateNapThreeHistory(nap)
                    } else {
                        var nap = NapHistory(id = day!!.toLong(), napOne = "", napTwo = ""!!, napThree = currentTime!!, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                        mNapHistory!!.insertNapHistory(nap)
                    }

                    try {
                        mPlanHistoryAllRepository = PlanHistoryAllRepository(activity!!)
                        if (mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()) != null) {
                            var id = mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
                            var plan = PlanHistoryAll(id, "", "", "", "", "", currentTime!!, "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.updateNapThree(plan)
                        } else {
                            var plan = PlanHistoryAll(null, "", "", "", "", "", currentTime!!, "", DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.insertPlanHistoryAll(plan)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }



                if (des.equals("Eat by", ignoreCase = true)) {
                    mEatByHistory = EatByRepository(activity!!)
                    var eatBy = EatByHistory(id = day!!.toLong(), eatBy = currentTime!!, currentDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity(), achieve = day)
                    mEatByHistory!!.insertEatByHistory(eatBy)

                    try {
                        mPlanHistoryAllRepository = PlanHistoryAllRepository(activity!!)
                        if (mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()) != null) {
                            var id = mPlanHistoryAllRepository!!.getCurrentPlanHistoryAll(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
                            var plan = PlanHistoryAll(id, "", "", "", "", "", "", currentTime!!, DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.updateEatBy(plan)
                        } else {
                            var plan = PlanHistoryAll(null, "", "", "", "", "", "", currentTime!!, DateTimeUtils.getCurrentDateTimeforEdsSeverity(), day!!)
                            mPlanHistoryAllRepository!!.insertPlanHistoryAll(plan)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

//                startActivity(Intent(activity!!, MainActivity::class.java))
//                finishAffinity()
                (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE
                LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, MainFragment(), R.id.fragmennt)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_handle_notification)


//        fullPrescribeInformation?.setOnClickListener {
//            val intent = Intent(this, WebActivity::class.java)
//            intent.putExtra("title", "Information")
//            intent.putExtra("heading", "Full Prescribing Information")
//            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
//            startActivity(intent)
//        }
//
//        importantSafetyInformation?.setOnClickListener {
//            val intent = Intent(this, WebActivity::class.java)
//            intent.putExtra("title", "Information")
//            intent.putExtra("heading", "Important Safety Information")
//            intent.putExtra("url", "file:///android_asset/ISI/ISI.htm")
//            startActivity(intent)
//        }
    }
}