package myapp.net.inspire.fragment

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.main_fragment.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.*
import myapp.net.inspire.data.repository.*
import myapp.net.inspire.notification.HandleNotificationActivity
import myapp.net.inspire.notification.receiver.PlanReceiver
import myapp.net.inspire.plan.PlanSetupTenFragment
import myapp.net.inspire.utils.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by deadlydragger on 10/26/18.
 */
class MainFragment : Fragment() {
    companion object {
        var TAG = "MainFragment"
    }

    private var isPlanSetup: Int? = 0
    private var mPlanRepository: PlanRepository? = null
    private var mNapRepository: NapRepository? = null
    private var isNapOne: Boolean? = false
    private var isNapTwo: Boolean? = false
    private var isNapThree: Boolean? = false
    private var isDoseOne: Boolean? = false
    private var isDoseTwo: Boolean? = false
    private var isWake: Boolean? = false
    private var napRepo: Nap? = null
    private var mNapHistoryRepository: NapHistoryRepository? = null
    private var mDoseHistoryRepository: DoseHistoryRepository? = null
    private var mWakeHistoryRepository: WakeHistoryRepository? = null
    private var mEatByHistoryRepository: EatByRepository? = null
    private var eventsList: MutableList<Date>? = null
    private var format = SimpleDateFormat("h:mm aa")
    private var formatTimeList: MutableList<String>? = null
    private var progressList: MutableList<ProgressPOJO>? = null

    var toolbarTitle: String? = null
    var mTitle: String? = null
    var des: String? = null
    var currentTime = DateTimeUtils.getCurrentTimeAlarm().toUpperCase()

    var notificationId: Int? = 0


    var napHistory: NapHistory? = null
    var doseHistory: DoseHistory? = null
    var wakeHistory: WakeHistory? = null
    var eatByHistory: EatByHistory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var title = (activity as MainActivity).findViewById<TextView>(R.id.title)
        var change = (activity as MainActivity).findViewById<TextView>(R.id.titleRight)
        var suspend = (activity as MainActivity).findViewById<TextView>(R.id.titleLeft)
        suspend.text = getString(R.string.suspend)
        change.text = getString(R.string.change)
        title.text = "Today's Plan"

        eatByAction?.isClickable = false

        try {
            formatTimeList = ArrayList()
            calculatePassedEvents()

            mNapHistoryRepository = NapHistoryRepository(activity!!)
            mDoseHistoryRepository = DoseHistoryRepository(activity)
            mWakeHistoryRepository = WakeHistoryRepository(activity)
            mEatByHistoryRepository = EatByRepository(activity!!)
            eventsList = ArrayList()
            var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())

            try {
                napHistory = mNapHistoryRepository!!.getNap(day!!)
                doseHistory = mDoseHistoryRepository!!.getDose(day!!)
                wakeHistory = mWakeHistoryRepository!!.getWakeBy(day!!.toString())
                eatByHistory = mEatByHistoryRepository!!.getEatBy(day!!.toString())

            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                if (!napHistory!!.napOne.isNullOrEmpty()) {
                    napOneLayoutPlan?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    napOneAction?.text = napHistory!!.napTwo
                    napTwoAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                if (!napHistory!!.napThree.isNullOrEmpty()) {
                    napThreeLayoutPlan?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    napThreeAction?.text = napHistory!!.napThree
                    napThreeAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {

                if (!wakeHistory!!.wakeUp.isNullOrEmpty()) {
                    wakeTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    wakeAction?.text = wakeHistory!!.wakeUp
                    wakeAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {

                if (!doseHistory!!.doseOne.isNullOrEmpty()) {
                    doseOneTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    doseOneAction?.text = doseHistory!!.doseOne
                    doseOneAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                if (!doseHistory!!.doseTwo.isNullOrEmpty()) {
                    doseTwoTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    doseTwoAction?.text = doseHistory!!.doseTwo
                    doseTwoAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {

                if (!eatByHistory!!.eatBy.isNullOrEmpty()) {
                    eatByAction?.isChecked = true
                    eatByAction?.isClickable = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }



        isPlanSetup = ((activity as MainActivity)).getIsPlanSetup()
        if ((isPlanSetup == PlanEnum.PLANSUSPENDED.ordinal)) {
            eventLayout?.visibility = View.GONE
            tipsLayout?.visibility = View.GONE
            defaultLayout?.visibility = View.VISIBLE
            inspireDefulatLayout?.visibility = View.GONE
            suspend?.text = ""


        } else if ((isPlanSetup == PlanEnum.NOPLAN.ordinal)) {
            eventLayout?.visibility = View.GONE
            tipsLayout?.visibility = View.GONE
            defaultLayout?.visibility = View.GONE
            suspend?.text = ""
            resumePlanBttn?.visibility = View.GONE
            inspireDefulatLayout?.visibility = View.VISIBLE

        } else {
            eventLayout?.visibility = View.VISIBLE
            tipsLayout?.visibility = View.VISIBLE
            defaultLayout?.visibility = View.GONE
            inspireDefulatLayout?.visibility = View.GONE
            setTodaysPlanData()
        }

        suspend.setOnClickListener {
            suspend?.text = ""
            Repository().setIsPlanSetup(activity!!, PlanEnum.PLANSUSPENDED.ordinal)
            isPlanSetup = Repository().getIsPlanSetup(activity!!)
            if ((isPlanSetup == PlanEnum.PLANSUSPENDED.ordinal) || (isPlanSetup == PlanEnum.NOPLAN.ordinal)) {
                eventLayout?.visibility = View.GONE
                tipsLayout?.visibility = View.GONE
                defaultLayout?.visibility = View.VISIBLE

            } else {
                eventLayout?.visibility = View.VISIBLE
                tipsLayout?.visibility = View.VISIBLE
                defaultLayout?.visibility = View.GONE
                setTodaysPlanData()
            }
        }

        change.setOnClickListener {
            var fragment = PlanSetupTenFragment()
            var bundle = Bundle()
            bundle.putBoolean("isSetup", false)
            fragment.arguments = bundle
            LoadFragment.addFragmentToBackStack(activity!!.supportFragmentManager, fragment, R.id.fragmennt, TAG)
        }


        resumePlanBttn?.setOnClickListener {
            if (Repository().getIsPlanSetup(activity) == PlanEnum.PLANSUSPENDED.ordinal) {
                Repository().setIsPlanSetup(activity!!, PlanEnum.PLANSETUP.ordinal)
                startActivity(Intent(activity, MainActivity::class.java))
                activity!!.finishAffinity()
            }
        }
        try {
            calculateFutureEvents()
            try {
                var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())
                napHistory = mNapHistoryRepository!!.getNap(day!!)
                doseHistory = mDoseHistoryRepository!!.getDose(day!!)
                wakeHistory = mWakeHistoryRepository!!.getWakeBy(day!!.toString())
                eatByHistory = mEatByHistoryRepository!!.getEatBy(day!!.toString())

            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                if (!napHistory!!.napOne.isNullOrEmpty()) {
                    napOneLayoutPlan?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    napOneAction?.text = napHistory!!.napOne
                    napOneAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                if (!napHistory!!.napTwo.isNullOrEmpty()) {
                    napTwoLayoutPlan?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    napTwoAction?.text = napHistory!!.napTwo
                    napTwoAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                if (!napHistory!!.napThree.isNullOrEmpty()) {
                    napThreeLayoutPlan?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    napThreeAction?.text = napHistory!!.napThree
                    napThreeAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {

                if (!wakeHistory!!.wakeUp.isNullOrEmpty()) {
                    wakeTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    wakeAction?.text = wakeHistory!!.wakeUp
                    wakeAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {

                if (!doseHistory!!.doseOne.isNullOrEmpty()) {
                    doseOneTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    doseOneAction?.text = doseHistory!!.doseOne
                    doseOneAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                if (!doseHistory!!.doseTwo.isNullOrEmpty()) {
                    doseTwoTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
                    doseTwoAction?.text = doseHistory!!.doseTwo
                    doseTwoAction?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorLightGreen))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {

                if (!eatByHistory!!.eatBy.isNullOrEmpty()) {
                    eatByAction?.isChecked = true
                    eatByAction?.isClickable = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun setTodaysPlanData() {
        try {
            mPlanRepository = PlanRepository(activity)
            mNapRepository = NapRepository(activity)
            var plan = mPlanRepository!!.getPlanById(DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay()))
            eatByTodaysPlan?.text = plan.eatBy
            if (format.parse(eatByTodaysPlan!!.text.toString()).after(DateTimeUtils.getCurrentDateTimeWithAmPm())) {

                eventsList!!.add(format.parse(eatByTodaysPlan!!.text.toString()))

            }
            formatTimeList?.add(plan.eatBy)


            var tokens = plan.doseOne.split(":")
            var hrs = tokens[0]
            var mins = tokens[1]
            if (hrs.equals("0")) {
                hrs = 12.toString()
            }
            doseOneTodaysPlan?.text = hrs + ":" + mins
            if (format.parse(doseOneTodaysPlan!!.text.toString()).after(DateTimeUtils.getCurrentDateTimeWithAmPm())) {
                eventsList!!.add(format.parse(doseOneTodaysPlan!!.text.toString()))

            }
            formatTimeList?.add(hrs + ":" + mins)

            var tokenDosetwo = plan.doseTwo.split(":")
            var hrsDoseTwo = tokenDosetwo[0]
            var minsDoseTwo = tokenDosetwo[1]
            if (hrsDoseTwo.equals("0")) {
                hrsDoseTwo = 12.toString()
            }
            doseTwoTodaysPlan?.text = hrsDoseTwo + ":" + minsDoseTwo
            if (format.parse(doseTwoTodaysPlan!!.text.toString()).after(DateTimeUtils.getCurrentDateTimeWithAmPm())) {
                eventsList!!.add(format.parse(doseTwoTodaysPlan!!.text.toString()))

            }
            formatTimeList?.add(hrsDoseTwo + ":" + minsDoseTwo)

            var tokenWake = plan.wakeUp.split(":")
            var hrsWake = tokenWake[0]
            var minsWake = tokenWake[1]
            if (hrsWake.equals("0")) {
                hrsWake = 12.toString()
            }
            wakeTodaysPlan?.text = hrsWake + ":" + minsWake
            if (format.parse(wakeTodaysPlan!!.text.toString()).after(DateTimeUtils.getCurrentDateTimeWithAmPm())) {
                eventsList!!.add(format.parse(wakeTodaysPlan!!.text.toString()))

            }
            formatTimeList?.add(hrsWake + ":" + minsWake)

            var dates = DateTimeUtils.getTodayTomorrowDate()
            rightDay?.text = dates.get(0)
            leftDay?.text = dates.get(1)


            doseOneAction?.setOnClickListener {
                isDoseOne = true
                isDoseTwo = false
                isNapOne = false
                isNapTwo = false
                isNapThree = false
                isWake = false
                var doseOneText = doseOneAction?.text.toString()
                if (doseOneText.equals("Confirm >")) {
                    Repository().setNotificationHandler(context, "Dose 1", "Dose 1", "Dose Taken", currentTime!!, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }

                if (doseOneText.equals("Early >")) {
                    futureEventsConfirm("DOSE1", plan.doseOne)
                    Repository().setNotificationHandler(context, "Dose 1", "Dose 1", "Dose Taken", currentTime!!, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }


            }

            doseTwoAction?.setOnClickListener {
                isDoseOne = false
                isDoseTwo = true
                isNapOne = false
                isNapTwo = false
                isNapThree = false
                isWake = false
                var doseTwoText = doseTwoAction?.text.toString()
                if (doseTwoText.equals("Confirm >")) {
                    Repository().setNotificationHandler(context, "Dose 2", "Dose 2", "Dose Taken", currentTime, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }

                if (doseTwoText.equals("Early >")) {
                    futureEventsConfirm("DOSE2", plan.doseTwo)
                    Repository().setNotificationHandler(context, "Dose 2", "Dose 2", "Dose Taken", currentTime, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }

            }

            wakeAction?.setOnClickListener {
                isDoseOne = false
                isDoseTwo = false
                isNapOne = false
                isNapTwo = false
                isNapThree = false
                isWake = true
                var wakeText = wakeAction?.text.toString()
                if (wakeText.equals("Confirm >")) {
                    Repository().setNotificationHandler(context, "Wake up", "Wake up", "Wake up", currentTime, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }

                if (wakeText.equals("Early >")) {
                    futureEventsConfirm("WAKE", plan.wakeUp)
                    Repository().setNotificationHandler(context, "Wake up", "Wake up", "Wake up", currentTime, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }
            }

            napOneAction?.setOnClickListener {
                isDoseOne = false
                isDoseTwo = false
                isNapOne = true
                isNapTwo = false
                isNapThree = false
                isWake = false
                var napOneText = napOneAction?.text.toString()
                if (napOneText.equals("Confirm >")) {
                    Repository().setNotificationHandler(context, "Nap", "Nap 1 taken", "Nap 1", currentTime, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }
                if (napOneText.equals("Early >")) {
                    futureEventsConfirm("NAP1", napRepo!!.napOne)
                    Repository().setNotificationHandler(context, "Nap", "Nap 1 taken", "Nap 1", currentTime, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }
            }

            napTwoAction?.setOnClickListener {
                isDoseOne = false
                isDoseTwo = false
                isNapOne = false
                isNapTwo = true
                isNapThree = false
                isWake = false
                var napTwoText = napTwoAction?.text.toString()
                if (napTwoText.equals("Confirm >")) {
                    Repository().setNotificationHandler(context, "Nap", "Nap 2 taken", "Nap 2", currentTime, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }

                if (napTwoText.equals("Early >")) {
                    futureEventsConfirm("NAP3", napRepo!!.napTwo)
                    Repository().setNotificationHandler(context, "Nap", "Nap 2 taken", "Nap 2", currentTime, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }
            }

            napThreeAction?.setOnClickListener {
                isDoseOne = false
                isDoseTwo = false
                isNapOne = false
                isNapTwo = false
                isNapThree = true
                isWake = false
                var napThreeText = napThreeAction?.text.toString()
                if (napThreeText.equals("Confirm >")) {
                    Repository().setNotificationHandler(context, "Nap", "Nap 3 taken", "Nap 3", currentTime, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }
                if (napThreeText.equals("Early >")) {
                    futureEventsConfirm("NAP3", napRepo!!.napThree)
                    Repository().setNotificationHandler(context, "Nap", "Nap 3 taken", "Nap 3", currentTime, -1)
                    (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.GONE
                    Repository().setIsNotificationTriggred(activity!!, false)
                    LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, HandleNotificationActivity(), R.id.fragmennt)
                }
            }


            napRepo = mNapRepository!!.getNapById(DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay()))
            var napOne = napRepo!!.napOne
            var napTwo = napRepo!!.napTwo
            var napThree = napRepo!!.napThree

            if (napOne!!.equals("-1") || napOne!!.equals("00")) {
                napOneLayoutPlan?.visibility = View.GONE
            } else {
                napOneLayoutPlan?.visibility = View.VISIBLE
                var tokens = napOne.split(":")
                var hrs = tokens[0]
                var mins = tokens[1]
                if (hrs.equals("0")) {
                    hrs = 12.toString()
                }
                napOne = hrs + ":" + mins
                napOneTodaysPlan?.text = napOne
                if (format.parse(napOneTodaysPlan!!.text.toString()).after(DateTimeUtils.getCurrentDateTimeWithAmPm())) {
                    eventsList!!.add(format.parse(napOneTodaysPlan!!.text.toString()))

                }
                formatTimeList?.add(hrs + ":" + mins)
            }

            if (napTwo!!.equals("-1") || napTwo!!.equals("00")) {
                napTwoLayoutPlan?.visibility = View.GONE
            } else {
                napTwoLayoutPlan?.visibility = View.VISIBLE
                var tokens = napTwo.split(":")
                var hrs = tokens[0]
                var mins = tokens[1]
                if (hrs.equals("0")) {
                    hrs = 12.toString()
                }
                napTwo = hrs + ":" + mins
                formatTimeList?.add(hrs + ":" + mins)
                napTwoTodaysPlan?.text = napTwo
                if (format.parse(napTwoTodaysPlan!!.text.toString()).after(DateTimeUtils.getCurrentDateTimeWithAmPm())) {
                    eventsList!!.add(format.parse(napTwoTodaysPlan!!.text.toString()))

                }
            }

            if (napThree!!.equals("-1") || napThree!!.equals("00")) {
                napThreeLayoutPlan?.visibility = View.GONE
            } else {
                napThreeLayoutPlan?.visibility = View.VISIBLE
                var tokens = napThree.split(":")
                var hrs = tokens[0]
                var mins = tokens[1]
                if (hrs.equals("0")) {
                    hrs = 12.toString()
                }
                napThree = hrs + ":" + mins
                formatTimeList?.add(hrs + ":" + mins)
                napThreeTodaysPlan?.text = napThree
                if (format.parse(napThreeTodaysPlan!!.text.toString()).after(DateTimeUtils.getCurrentDateTimeWithAmPm())) {
                    eventsList!!.add(format.parse(napThreeTodaysPlan!!.text.toString()))

                }
            }

            setProgress()


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun setProgress() {
        mPlanRepository = PlanRepository(activity!!)
        var yesterDay = mPlanRepository!!.getPlanById(DateTimeUtils.getCurrentDayInt(DateTimeUtils.getYesterDay()))
//        DateTimeUtils.getFullDateTiem(yesterDay.eatBy)

        var planToday = mPlanRepository!!.getPlanById(DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay()))
        seekbarIndicator?.setProgress(0)
        seekbarIndicator?.max = 100
        seekbarIndicator?.isEnabled = false
        seekbarIndicator.setProgressDrawable(ContextCompat.getDrawable(activity!!, R.drawable.seekbar_progress))
        sortEventList()


//        if (DateTimeUtils.getCurrentDateTimeWithAmPm().after(DateTimeUtils.getFullDateTiem(planToday.wakeUp))
//                && DateTimeUtils.getCurrentDateTimeWithAmPm().before(DateTimeUtils.getFullDateTiem(planToday.eatBy))) {
//            println("Previous:: " + DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.wakeUp)), DateTimeUtils.getCurrentDateTimeWithAmPm()))
//            println("Prevous Next:: " + DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.eatBy)), DateTimeUtils.getCurrentDateTimeWithAmPm()))
//
//            var previousTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.wakeUp)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
//            var nextTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.eatBy)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
//            previousTextViewTime?.text = previousTokens[0] + " hr " + previousTokens[1] + " mins ago"
//            previousTextViewTitle?.text = "Wake Scheduled"
//            upNextTextViewTime?.text = "in  " + nextTokens[0] + " hr " + nextTokens[1] + " mins"
//            upNextTextViewTitle?.text = "Eat by Scheduled"
//            seekbarIndicator?.progress = 25
//        }
//        if (DateTimeUtils.getCurrentDateTimeWithAmPm().after(DateTimeUtils.getFullDateTiem(planToday.eatBy))
//                && DateTimeUtils.getCurrentDateTimeWithAmPm().before(DateTimeUtils.getFullDateTiem(planToday.doseOne))) {
//            var previousTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.eatBy)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
//            var nextTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.doseOne)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
//            previousTextViewTime?.text = previousTokens[0] + " hr " + previousTokens[1] + " mins ago"
//            previousTextViewTitle?.text = "Eat by Scheduled"
//            upNextTextViewTime?.text = "in  " + nextTokens[0] + " hr " + nextTokens[1] + " mins"
//            upNextTextViewTitle?.text = "Dose 1 by Scheduled"
//            seekbarIndicator?.progress = 50
//        }
//
//        if (DateTimeUtils.getCurrentDateTimeWithAmPm().after(DateTimeUtils.getFullDateTiem(planToday.doseOne))
//                && DateTimeUtils.getCurrentDateTimeWithAmPm().before(DateTimeUtils.getFullDateTiem(planToday.doseTwo))) {
//            var previousTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.doseOne)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
//            var nextTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.doseTwo)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
//            previousTextViewTime?.text = previousTokens[0] + " hr " + previousTokens[1] + " mins ago"
//            previousTextViewTitle?.text = "Dose 1 by Scheduled"
//            upNextTextViewTime?.text = "in  " + nextTokens[0] + " hr " + nextTokens[1] + " mins"
//            upNextTextViewTitle?.text = "Dose 2 by Scheduled"
//            seekbarIndicator?.progress = 75
//        }
//
//        if (DateTimeUtils.getCurrentDateTimeWithAmPm().after(DateTimeUtils.getFullDateTiem(planToday.doseTwo))
//                && DateTimeUtils.getCurrentDateTimeWithAmPm().before(DateTimeUtils.getFullDateTiem(planToday.wakeUp))) {
//            var previousTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.doseTwo)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
//            var nextTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.wakeUp)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
//            previousTextViewTime?.text = previousTokens[0] + " hr " + previousTokens[1] + " mins ago"
//            previousTextViewTitle?.text = "Dose 2 by Scheduled"
//            upNextTextViewTime?.text = "in  " + nextTokens[0] + " hr " + nextTokens[1] + " mins"
//            upNextTextViewTitle?.text = "Wake by Scheduled"
//            seekbarIndicator?.progress = 100
//        }

    }

    override fun onResume() {
        super.onResume()
        if ((isPlanSetup == PlanEnum.NOPLAN.ordinal)) {
            (activity as MainActivity).findViewById<TextView>(R.id.cross).visibility = View.GONE
            (activity as MainActivity).findViewById<TextView>(R.id.titleLeft).visibility = View.VISIBLE
            var layout = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomView)
            layout.visibility = View.GONE

        } else {
            (activity as MainActivity).findViewById<TextView>(R.id.cross).visibility = View.GONE
            (activity as MainActivity).findViewById<TextView>(R.id.titleLeft).visibility = View.VISIBLE
            var layout = (activity as MainActivity).findViewById<LinearLayout>(R.id.bottomView)
            layout.visibility = View.VISIBLE
        }
        try {
            tipsGreyBar()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun calculateFutureEvents() {
        val closest = Collections.min(eventsList!!.toList(), object : Comparator<Date> {
            override fun compare(d1: Date, d2: Date): Int {
                val diff1 = Math.abs(d1.time - DateTimeUtils.getCurrentDateTimeWithAmPm().time)
                val diff2 = Math.abs(d2.time - DateTimeUtils.getCurrentDateTimeWithAmPm().time)
                return if (diff1 < diff2) -1 else 1
            }
        })
        var closestTime = format.format(closest).toUpperCase()
        println("Closed time:: " + closestTime)
        var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())
        var planRepo = PlanRepository(activity).getPlanById(day!!)
        var napRepo = NapRepository(activity).getNapById(day!!)


        if (planRepo.doseOne.equals(closestTime, true)) {

            doseOneAction?.text = "Early >"

        }

        if (planRepo.doseTwo.equals(closestTime, true)) {

            doseTwoAction?.text = "Early >"


        }
        if (planRepo.wakeUp.equals(closestTime, true)) {

            wakeAction?.text = "Early >"

        }

        if (napRepo.napOne.equals(closestTime, true)) {

            napOneAction?.text = "Early >"

        }

        if (napRepo.napTwo.equals(closestTime, true)) {

            napTwoAction?.text = "Early >"

        }

        if (napRepo.napThree.equals(closestTime, true)) {

            napThreeAction?.text = "Early >"

        }

    }


    private fun sortEventList() {
        progressList = ArrayList()
        println("future events:: " + formatTimeList!!.toList())
        var plan = mPlanRepository!!.getPlanById(DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay()))
        var napRepo = mNapRepository!!.getNapById(DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay()))
        val dates = ArrayList<Date>()
        val format = SimpleDateFormat("h:mm aa")
        for (time in formatTimeList!!) {
            dates.add(format.parse(time))
        }
        Collections.sort(dates)
        System.out.println(dates)
        for (time in formatTimeList!!) {
            if (plan.eatBy.equals(time, true)) {
                progressList!!.add(ProgressPOJO(time, "Eat By"))
            }

            if (plan.doseOne.equals(time, true)) {
                progressList!!.add(ProgressPOJO(time, "Dose 1"))
            }

            if (plan.doseTwo.equals(time, true)) {
                progressList!!.add(ProgressPOJO(time, "Dose 2"))
            }

            if (napRepo.napOne.equals(time, true)) {
                progressList!!.add(ProgressPOJO(time, "Nap 1"))
            }

            if (napRepo.napTwo.equals(time, true)) {
                progressList!!.add(ProgressPOJO(time, "Nap 2"))
            }

            if (napRepo.napThree.equals(time, true)) {
                progressList!!.add(ProgressPOJO(time, "Nap 3"))
            }
        }

        var count = 0
        var i = 1
        var size = progressList!!.size
        var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())
        var napHistory = mNapHistoryRepository!!.getNap(day!!)
        var doseHistory = mDoseHistoryRepository!!.getDose(day!!)
        var wakeHistory = mWakeHistoryRepository!!.getWakeBy(day!!.toString())
        var eatByHistory = mEatByHistoryRepository!!.getEatBy(day!!.toString())

        var timeCatch: ProgressPOJO? = null
        try {
            for (time in progressList!!) {
                timeCatch = time

                if (DateTimeUtils.getCurrentDateTimeWithAmPm().after(DateTimeUtils.getFullDateTiem(time.time))
                        && DateTimeUtils.getCurrentDateTimeWithAmPm().before(DateTimeUtils.getFullDateTiem(if (count + 1 != size) progressList!!.get(count + 1).time else progressList!!.get(size).time))) {
//                println("Previous:: " + DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.wakeUp)), DateTimeUtils.getCurrentDateTimeWithAmPm()))
//                println("Prevous Next:: " + DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(planToday.eatBy)), DateTimeUtils.getCurrentDateTimeWithAmPm()))

                    var previousTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(time.time)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
                    var nextTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(progressList!!.get(count + 1).time)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
                    previousTextViewTime?.text = previousTokens[0] + " hr " + previousTokens[1] + " mins ago"
                    previousTextViewTitle?.text = time.eventName + " Scheduled"
                    upNextTextViewTime?.text = "in  " + nextTokens[0] + " hr " + nextTokens[1] + " mins"
                    upNextTextViewTitle?.text = progressList!!.get(count + 1).eventName + " Scheduled"
                    println("value of i:: " + i + " count:: " + count + " Div:: " + (count * 100 / progressList!!.size))
                    seekbarIndicator?.progress = (count * 100) / progressList!!.size!!

//                    if (plan.eatBy.equals(time.time, true)) {
//                        eatByTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
//                    }
//
//                    if (plan.doseOne.equals(time.time, true)) {
//                        doseOneTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
//                        if (!doseHistory.doseOne.isNullOrEmpty()) {
//
//                        } else {
//                            doseOneAction?.text = "No Data"
//                            doseOneAction?.setTextColor(Color.RED)
//                        }
//                    }
//
//                    if (plan.doseTwo.equals(time.time, true)) {
//                        doseTwoTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
//                        if (!doseHistory.doseTwo.isNullOrEmpty()) {
//
//                        } else {
//                            doseTwoAction?.text = "No Data"
//                            doseTwoAction?.setTextColor(Color.RED)
//                        }
//                    }
//
//                    if (napRepo.napOne.equals(time.time, true)) {
//                        napOneTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
//                        if (!napHistory.napOne.isNullOrEmpty()) {
//
//                        } else {
//                            napOneAction?.text = "No Data"
//                            napOneAction?.setTextColor(Color.RED)
//                        }
//                    }
//
//                    if (napRepo.napTwo.equals(time.time, true)) {
//                        napTwoTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
//                        if (!napHistory.napTwo.isNullOrEmpty()) {
//
//                        } else {
//                            napTwoAction?.text = "No Data"
//                            napTwoAction?.setTextColor(Color.RED)
//                        }
//                    }
//
//                    if (napRepo.napThree.equals(time.time, true)) {
//                        napThreeTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreyPlan))
//
//                        if (!napHistory.napThree.isNullOrEmpty()) {
//
//                        } else {
//                            napThreeAction?.text = "No Data"
//                            napThreeAction?.setTextColor(Color.RED)
//                        }
//                    }

                }
                count++
                i++

            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (DateTimeUtils.getCurrentDateTimeWithAmPm().after(DateTimeUtils.getFullDateTiem(timeCatch!!.time))
                    && DateTimeUtils.getCurrentDateTimeWithAmPm().before(DateTimeUtils.getFullDateTiem(progressList!!.get(size).time))) {

                var previousTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(timeCatch.time)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
                var nextTokens = DateTimeUtils.calculateTimeBackProgress((DateTimeUtils.getFullDateTiem(progressList!!.get(size).time)), DateTimeUtils.getCurrentDateTimeWithAmPm()).split(":")
                previousTextViewTime?.text = previousTokens[0] + " hr " + previousTokens[1] + " mins ago"
                previousTextViewTitle?.text = timeCatch.eventName + " Scheduled"
                upNextTextViewTime?.text = "in  " + nextTokens[0] + " hr " + nextTokens[1] + " mins"
                upNextTextViewTitle?.text = progressList!!.get(count + 1).eventName + " Scheduled"
                println("value of i:: " + i + " count:: " + count + " Div:: " + (count * 100 / progressList!!.size))
                seekbarIndicator?.progress = (count * 100) / progressList!!.size!!
            }
            seekbarIndicator?.progress = 75


        }
    }

    private fun calculatePassedEvents() {
        var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())
        var planRepo = PlanRepository(activity).getPlanById(day!!)
        var napRepo = NapRepository(activity).getNapById(day!!)


        if (planRepo != null) {
            var doseOne = planRepo.doseOne
            var doseTwo = planRepo.doseTwo
            var wake = planRepo.wakeUp
            if (planRepo != null) {
                var doseOne = planRepo.doseOne
                var doseTwo = planRepo.doseTwo
                var wake = planRepo.wakeUp
                if (doseOne != null && !doseOne.isNullOrEmpty()) {
                    if (calculateTimeInMills(doseOne)) {
                        doseOneAction?.text = "Confirm >"
                        doseOneTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))

                    }

                    if (calculateTimeInMills(doseOne) && doseOne.isNullOrEmpty()) {
                        doseOneAction?.text = "No Data"
                        doseOneAction?.setTextColor(Color.RED)
                        doseOneTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))

                    }
                }

                if (doseTwo != null && !doseTwo.isNullOrEmpty()) {
                    if (calculateTimeInMills(doseTwo)) {
                        doseTwoAction?.text = "Confirm >"
                        doseTwoTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    }
                    if (calculateTimeInMills(doseTwo) && doseTwo.isNullOrEmpty()) {
                        doseTwoAction?.text = "No Data"
                        doseTwoAction?.setTextColor(Color.RED)
                        doseTwoTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    }
                }

                if (wake != null && !wake.isNullOrEmpty()) {
                    if (calculateTimeInMills(wake)) {
                        wakeAction?.text = "Confirm >"
                        wakeTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    }

                    if (calculateTimeInMills(wake) && wake.isNullOrEmpty()) {
                        wakeAction?.text = "No Data"
                        wakeAction?.setTextColor(Color.RED)
                        wakeTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    }
                }

            }

            if (napRepo != null) {
                if (napRepo.napOne != null && !napRepo.napOne.equals("-1")) {
                    if (calculateTimeInMills(napRepo.napOne)) {
                        napOneAction?.text = "Confirm >"
                        napOneTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    }

                    if (calculateTimeInMills(napRepo.napOne) && napRepo.napOne.equals("-1")) {
                        napOneAction?.text = "No Data"
                        napOneAction?.setTextColor(Color.RED)
                        napOneTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    }


                }
                if (napRepo.napTwo != null && !napRepo.napTwo.equals("-1")) {
                    if (calculateTimeInMills(napRepo.napTwo)) {
                        napTwoAction?.text = "Confirm >"
                        napTwoTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    }

                    if (calculateTimeInMills(napRepo.napTwo) && napRepo.napTwo.equals("-1")) {
                        napTwoAction?.text = "No Data"
                        napTwoAction?.setTextColor(Color.RED)
                        napTwoTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    }
                }
                if (napRepo.napThree != null && !napRepo.napThree.equals("-1")) {
                    if (calculateTimeInMills(napRepo.napThree)) {
                        napThreeAction?.text = "Confirm >"
                        napThreeTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    }

                    if (calculateTimeInMills(napRepo.napThree) && napRepo.napThree.equals("-1")) {
                        napThreeAction?.text = "No Data"
                        napThreeAction?.setTextColor(Color.RED)
                        napThreeTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    }
                }
            }

            if (!planRepo.eatBy.isNullOrEmpty()) {
                if (calculateTimeInMills(planRepo.eatBy)) {
                    eatByTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorLightGrey))
                    eatByAction?.isChecked = true
                }
            }



        if(Repository(activity!!)!!.getCurrentlySetupPlan(activity!!)!!){
            if (!doseOne.isNullOrEmpty()) {
                if (calculateTimeInMills(doseOne)) {
                    doseOneAction?.text = "... "
                    doseOneTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.white))

                }


            }

//            if (doseTwo != null && !doseTwo.isNullOrEmpty()) {
//                if (calculateTimeInMills(doseTwo)) {
//                    doseTwoAction?.text = "... "
//                    doseTwoTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.white))
//
//                }
//
//            }

            if (wake != null && !wake.isNullOrEmpty()) {
                if (calculateTimeInMills(wake)) {
                    wakeAction?.text = "... "
                    wakeTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.white))
                }

            }

        }

//            if (napRepo != null) {
//                if (napRepo.napOne != null && !napRepo.napOne.equals("-1")) {
//                    if (calculateTimeInMills(napRepo.napOne)) {
//                        napOneAction?.text = "... "
//                        napOneTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.white))
//                    }
//
//                }
//                if (napRepo.napTwo != null && !napRepo.napTwo.equals("-1")) {
//                    if (calculateTimeInMills(napRepo.napTwo)) {
//                        napTwoAction?.text = "... "
//                        napTwoTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.white))
//                    }
//
//                }
//                if (napRepo.napThree != null && !napRepo.napThree.equals("-1")) {
//                    if (calculateTimeInMills(napRepo.napThree)) {
//                        napThreeAction?.text = "... "
//                        napThreeTodaysPlanLayout?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.white))
//                    }
//
//                }
//            }
//
            if (!planRepo.eatBy.isNullOrEmpty()) {
                if (calculateTimeInMills(planRepo.eatBy)) {
                    Repository(activity!!).setCurrentlySetupPlan(activity!!,false)
                }
            }

        }
    }

    private fun calculateTimeInMills(time: String): Boolean {
        var parser = SimpleDateFormat("h:mm aa")
        var savedTime = parser.parse(time)
        var systemTime = DateTimeUtils.getCurrentDateTimeWithAmPm()
        if (systemTime.after(savedTime)) {
            return true
        }

        return false
    }

    private fun futureEventsConfirm(eventName: String, eventTime: String) {
        try {
            var currentNotificationId = DateTimeUtils.getFutureNotification() + "_" + eventName.toUpperCase() + "_NOTIFICATION_ID"
            var requesetCodePendingId = DateTimeUtils.getFutureNotification() + "_" + eventName.toUpperCase() + "_REQUEST_CODE"
            var notificationId = Constants().javaClass.getField(currentNotificationId).getInt(null)
            var requestId = Constants().javaClass.getField(requesetCodePendingId).getInt(null)


            val am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var nm = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            var intent = Intent(activity, MainActivity::class.java)
            var pIntent = PendingIntent.getBroadcast(activity, requestId, intent, 0)
            am.cancel(pIntent)

            nm.cancel(notificationId)

            var pendingIntent = Intent(context, PlanReceiver::class.java).let { intent ->
                intent.putExtra("requestCode", requestId)
                intent.putExtra("time", eventTime)
                PendingIntent.getBroadcast(context, notificationId!!, intent, 0)
            }
            var tuesdayEatAlarmManager: AlarmManager? = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            NotificationUtils.setNotificationForFuturePlan(eventTime, activity, DateTimeUtils.getTomorrowDay(), notificationId, pendingIntent, tuesdayEatAlarmManager!!)
        } catch (e: Exception) {
            e.printStackTrace()


        }
    }


    private fun tipsGreyBar() {
        try {
            var currentTime = DateTimeUtils.getCurrentDateWithoutTimeForPlan()
            var map = Repository(activity!!).getConsecutiveDate(activity!!)
            var days10Counter = Repository(activity!!).get10Days(activity!!)
            if (days10Counter!! > 10) {
                Repository(activity!!).set10Days(activity!!, 0)
                Repository(activity!!).set10Days(activity!!, (days10Counter!! + 1))

            }

            if (map.getValue("isFirst").equals("1")) {
                var counter = map.getValue("counter").toInt()
                Repository(activity!!).setConsecutiveDate(activity!!, DateTimeUtils.getCurrentDateWithoutTimeForPlan(), (counter + 1).toString(), "0")
                Repository(activity!!).set10Days(activity!!, (days10Counter!! + 1))
                var tips = arrayOf(getString(R.string.tips_13), getString(R.string.tips_14), getString(R.string.tips_15), getString(R.string.tips_16), getString(R.string.tips_17), getString(R.string.tips_18), getString(R.string.tips_19))
                var random = Random().nextInt(tips.size)
                var displayTips = tips.get(random)
                Repository(activity!!).setTipsData(activity!!, DateTimeUtils.getCurrentDateWithoutTimeForPlan(), displayTips)
                tipsTextView?.text = Repository(activity!!).getTipsData(activity!!).getValue("tips")

            } else {
                if (DateTimeUtils.comparePassedDateWithCurrentDate(map.getValue("consecutive_date"), currentTime) == 0) {


                } else if (DateTimeUtils.comparePassedDateWithCurrentDate(map.getValue("consecutive_date"), currentTime) == 1) {
                    var counter = map.getValue("counter").toInt()
                    Repository(activity!!).setConsecutiveDate(activity!!, DateTimeUtils.getCurrentDateWithoutTimeForPlan(), (counter + 1).toString(), "0")
                    Repository(activity!!).set10Days(activity!!, (days10Counter!! + 1))
                } else {
                    var counter = map.getValue("counter").toInt()
                    Repository(activity!!).setConsecutiveDate(activity!!, DateTimeUtils.getCurrentDateWithoutTimeForPlan(), (counter + 1).toString(), "0")
                    Repository(activity!!).set10Days(activity!!, (days10Counter!! + 1))
                }

                if (DateTimeUtils.comparePassedDateWithCurrentDate(Repository(activity!!).getTipsData(activity!!).getValue("tips_date"), currentTime) == 0) {
                    tipsTextView?.text = Repository(activity!!).getTipsData(activity!!).getValue("tips")
                } else if (DateTimeUtils.comparePassedDateWithCurrentDate(Repository(activity!!).getTipsData(activity!!).getValue("tips_date"), currentTime) == 1) {
                    var tips = arrayOf(getString(R.string.tips_13), getString(R.string.tips_14), getString(R.string.tips_15), getString(R.string.tips_16), getString(R.string.tips_17), getString(R.string.tips_18), getString(R.string.tips_19))
                    var random = Random().nextInt(tips.size)
                    var displayTips = tips.get(random)
                    Repository(activity!!).setTipsData(activity!!, DateTimeUtils.getCurrentDateWithoutTimeForPlan(), displayTips)
                    tipsTextView?.text = Repository(activity!!).getTipsData(activity!!).getValue("tips")
                } else {
                    tipsTextView?.text = Repository(activity!!).getTipsData(activity!!).getValue("tips")
                }
            }

            var consecutiveDays = Repository(activity!!).getConsecutiveDate(activity!!).getValue("counter").toInt()
            if (consecutiveDays == 7) {

                tipsTextView?.text = getString(R.string.days_tips_7)
            } else if (consecutiveDays == 14) {
                tipsTextView?.text = getString(R.string.days_tips_14)
            } else if (consecutiveDays == 28) {
                tipsTextView?.text = getString(R.string.days_tips_28)
            } else if (consecutiveDays == 30) {
                tipsTextView?.text = getString(R.string.days_tips_30)

            } else if (consecutiveDays == 45) {
                tipsTextView?.text = getString(R.string.days_tips_45)
            } else if (consecutiveDays == 90) {
                tipsTextView?.text = getString(R.string.days_tips_90)
            }

            if (days10Counter == 10) {
                tipsTextView?.text = getString(R.string.every_10_days_not_confirm_all_events_within_15mins)
            }
            var day = DateTimeUtils.getCurrentDayInt(DateTimeUtils.getCurrentDay())
            try {
                var plan = PlanRepository(activity!!).getPlanById(day!!)
                if (plan != null) {
                    if (!plan.eatBy.isNullOrEmpty() && plan.wakeUp.isNullOrEmpty() && plan.doseOne.isNullOrEmpty() && plan.doseTwo.isNullOrEmpty()) {
                        var nap = NapRepository(activity!!).getNapById(day!!)
                        if (nap.napOne.isNullOrEmpty() && nap.napTwo.isNullOrEmpty() && nap.napThree.isNullOrEmpty()) {
                            tipsTextView?.text = getString(R.string.confirm_eatby_but_not_other_events_within_10days)

                        } else {
                            tipsTextView?.text = getString(R.string.confirm_eatby_but_not_other_events_within_10days)

                        }
                    }
                } else {
                    var tips = arrayOf(getString(R.string.tips_13), getString(R.string.tips_14), getString(R.string.tips_15), getString(R.string.tips_16), getString(R.string.tips_17), getString(R.string.tips_18), getString(R.string.tips_19))
                    var random = Random().nextInt(tips.size)
                    var displayTips = tips.get(random)
                    tipsTextView?.text = displayTips
                }

            } catch (e: Exception) {
                e.printStackTrace()
                tipsTextView?.text = Repository(activity!!).getTipsData(activity!!).getValue("tips")
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }


    }


}