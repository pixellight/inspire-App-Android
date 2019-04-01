package myapp.net.inspire.report

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.plan_report.*
import kotlinx.android.synthetic.main.reports_menu.*
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.PlanHistoryAll
import myapp.net.inspire.data.repository.PlanHistoryAllRepository
import myapp.net.inspire.report.adapter.PlanAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by deadlydragger on 10/28/18.
 */
class PlanGraphFragment : Fragment() {
    private var mPlanHistoryRepository: PlanHistoryAllRepository? = null
    val format = SimpleDateFormat("yyyy-MM-dd")
    val formatMonth = SimpleDateFormat("MMMM")
    val dayFormat = SimpleDateFormat("EEEE")
    val titleFormat = SimpleDateFormat("dd EEEE")
    private var hashMap: HashMap<String, List<PlanHistoryAll>>? = null
    private var listEds: MutableList<PlanHistoryAll>? = null
    private var febEds: MutableList<PlanHistoryAll>? = null
    private var marEds: MutableList<PlanHistoryAll>? = null
    private var weekEds: MutableList<MonthData>? = null

    private var janList: MutableList<PlanHistoryAll>? = null
    private var febList: MutableList<PlanHistoryAll>? = null
    private var marList: MutableList<PlanHistoryAll>? = null
    private var aprList: MutableList<PlanHistoryAll>? = null
    private var mayList: MutableList<PlanHistoryAll>? = null
    private var junList: MutableList<PlanHistoryAll>? = null
    private var julyList: MutableList<PlanHistoryAll>? = null
    private var augList: MutableList<PlanHistoryAll>? = null
    private var sepList: MutableList<PlanHistoryAll>? = null
    private var octList: MutableList<PlanHistoryAll>? = null
    private var novList: MutableList<PlanHistoryAll>? = null
    private var decList: MutableList<PlanHistoryAll>? = null
    private var janHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var febHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var marHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var aprHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var mayHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var junHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var julHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var augHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var sepHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var octHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var novHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var decHashMap: HashMap<Int, List<PlanHistoryAll>>? = null
    private var monthList: MutableList<PlanHistoryYear>? = null

    private var adapter: PlanAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.reports_menu, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
//            saveDate()
            graphWork()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun graphWork() {

        try {
            mPlanHistoryRepository = PlanHistoryAllRepository(activity!!)
            var mPlanHistoryAllList = mPlanHistoryRepository!!.getAWeekData()
            var mMonthData = mPlanHistoryRepository!!.getMonthData()

            hashMap = HashMap()
            marEds = ArrayList()
            weekEds = ArrayList()
            for (i in 0..mPlanHistoryAllList.size - 1) {
                println("data:: " + formatMonth.format(format.parse(mPlanHistoryAllList.get(i).created_date)))
                if (dayFormat.format(format.parse(mPlanHistoryAllList.get(i).created_date)).equals("Sunday", true)) {

                    if (mPlanHistoryAllList.get(i).napOne.isNotBlank()) {
                        napOneSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napOne.isBlank() && mPlanHistoryAllList.get(i).napOne.isEmpty()) {
                        napOneSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napOneSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).napTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).napTwo.isNotBlank()) {
                        napTwoSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napTwo.isBlank() && mPlanHistoryAllList.get(i).napTwo.isEmpty()) {
                        napTwoSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napTwoSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).napThree.isNullOrEmpty() && mPlanHistoryAllList.get(i).napThree.isNotBlank()) {
                        napThreeSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napThree.isBlank() && mPlanHistoryAllList.get(i).napThree.isEmpty()) {
                        napThreeSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napThreeSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).doseOne.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseOne.isNotBlank()) {
                        doseOneSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseOne.isBlank() && mPlanHistoryAllList.get(i).doseOne.isEmpty()) {
                        doseOneSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseOneSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).doseTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseTwo.isNotBlank()) {
                        doseTwoSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseTwo.isBlank() && mPlanHistoryAllList.get(i).doseTwo.isEmpty()) {
                        doseTwoSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseTwoSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).wakeUp.isNullOrEmpty() && mPlanHistoryAllList.get(i).wakeUp.isNotBlank()) {
                        wakeSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).wakeUp.isBlank() && mPlanHistoryAllList.get(i).wakeUp.isEmpty()) {
                        wakeSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        wakeSunday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                }
                if (dayFormat.format(format.parse(mPlanHistoryAllList.get(i).created_date)).equals("Monday", true)) {
                    if (mPlanHistoryAllList.get(i).napOne.isNotBlank()) {
                        napOneMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napOne.isBlank() && mPlanHistoryAllList.get(i).napOne.isEmpty()) {
                        napOneMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napOneMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).napTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).napTwo.isNotBlank()) {
                        napTwoMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napTwo.isBlank() && mPlanHistoryAllList.get(i).napTwo.isEmpty()) {
                        napTwoMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napTwoMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).napThree.isNullOrEmpty() && mPlanHistoryAllList.get(i).napThree.isNotBlank()) {
                        napThreeMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napThree.isBlank() && mPlanHistoryAllList.get(i).napThree.isEmpty()) {
                        napThreeMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napThreeMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).doseOne.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseOne.isNotBlank()) {
                        doseOneMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseOne.isBlank() && mPlanHistoryAllList.get(i).doseOne.isEmpty()) {
                        doseOneMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseOneMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).doseTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseTwo.isNotBlank()) {
                        doseTwoMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseTwo.isBlank() && mPlanHistoryAllList.get(i).doseTwo.isEmpty()) {
                        doseTwoMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseTwoMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).wakeUp.isNullOrEmpty() && mPlanHistoryAllList.get(i).wakeUp.isNotBlank()) {
                        wakeMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).wakeUp.isBlank() && mPlanHistoryAllList.get(i).wakeUp.isEmpty()) {
                        wakeMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        wakeMonday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                }
                if (dayFormat.format(format.parse(mPlanHistoryAllList.get(i).created_date)).equals("Tuesday", true)) {
                    if (mPlanHistoryAllList.get(i).napOne.isNotBlank()) {
                        napOneTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napOne.isBlank() && mPlanHistoryAllList.get(i).napOne.isEmpty()) {
                        napOneTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napOneTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).napTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).napTwo.isNotBlank()) {
                        napTwoTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napTwo.isBlank() && mPlanHistoryAllList.get(i).napTwo.isEmpty()) {
                        napTwoTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napTwoTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).napThree.isNullOrEmpty() && mPlanHistoryAllList.get(i).napThree.isNotBlank()) {
                        napThreeTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napThree.isBlank() && mPlanHistoryAllList.get(i).napThree.isEmpty()) {
                        napThreeTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napThreeTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).doseOne.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseOne.isNotBlank()) {
                        doseOneTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseOne.isBlank() && mPlanHistoryAllList.get(i).doseOne.isEmpty()) {
                        doseOneTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseOneTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).doseTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseTwo.isNotBlank()) {
                        doseTwoTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseTwo.isBlank() && mPlanHistoryAllList.get(i).doseTwo.isEmpty()) {
                        doseTwoTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseTwoTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).wakeUp.isNullOrEmpty() && mPlanHistoryAllList.get(i).wakeUp.isNotBlank()) {
                        wakeTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).wakeUp.isBlank() && mPlanHistoryAllList.get(i).wakeUp.isEmpty()) {
                        wakeTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        wakeTuesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                }

                if (dayFormat.format(format.parse(mPlanHistoryAllList.get(i).created_date)).equals("Wednesday", true)) {
                    if (mPlanHistoryAllList.get(i).napOne.isNotBlank()) {
                        napOneWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napOne.isBlank() && mPlanHistoryAllList.get(i).napOne.isEmpty()) {
                        napOneWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napOneWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).napTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).napTwo.isNotBlank()) {
                        napTwoWednesday.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napTwo.isBlank() && mPlanHistoryAllList.get(i).napTwo.isEmpty()) {
                        napTwoWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napTwoWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).napThree.isNullOrEmpty() && mPlanHistoryAllList.get(i).napThree.isNotBlank()) {
                        napThreeWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napThree.isBlank() && mPlanHistoryAllList.get(i).napThree.isEmpty()) {
                        napThreeWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napThreeWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).doseOne.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseOne.isNotBlank()) {
                        doseOneWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseOne.isBlank() && mPlanHistoryAllList.get(i).doseOne.isEmpty()) {
                        doseOneWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseOneWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).doseTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseTwo.isNotBlank()) {
                        doseTwoWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseTwo.isBlank() && mPlanHistoryAllList.get(i).doseTwo.isEmpty()) {
                        doseTwoWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseTwoWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).wakeUp.isNullOrEmpty() && mPlanHistoryAllList.get(i).wakeUp.isNotBlank()) {
                        wakeWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).wakeUp.isBlank() && mPlanHistoryAllList.get(i).wakeUp.isEmpty()) {
                        wakeWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        wakeWednesday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                }
                if (dayFormat.format(format.parse(mPlanHistoryAllList.get(i).created_date)).equals("Thursday", true)) {
                    if (mPlanHistoryAllList.get(i).napOne.isNotBlank()) {
                        napOneThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napOne.isBlank() && mPlanHistoryAllList.get(i).napOne.isEmpty()) {
                        napOneThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napOneThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).napTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).napTwo.isNotBlank()) {
                        napTwoThursday.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napTwo.isBlank() && mPlanHistoryAllList.get(i).napTwo.isEmpty()) {
                        napTwoThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napTwoThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).napThree.isNullOrEmpty() && mPlanHistoryAllList.get(i).napThree.isNotBlank()) {
                        napThreeThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napThree.isBlank() && mPlanHistoryAllList.get(i).napThree.isEmpty()) {
                        napThreeThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napThreeThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).doseOne.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseOne.isNotBlank()) {
                        doseOneThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseOne.isBlank() && mPlanHistoryAllList.get(i).doseOne.isEmpty()) {
                        doseOneThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseOneThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).doseTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseTwo.isNotBlank()) {
                        doseTwoThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseTwo.isBlank() && mPlanHistoryAllList.get(i).doseTwo.isEmpty()) {
                        doseTwoThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseTwoThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).wakeUp.isNullOrEmpty() && mPlanHistoryAllList.get(i).wakeUp.isNotBlank()) {
                        wakeThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).wakeUp.isBlank() && mPlanHistoryAllList.get(i).wakeUp.isEmpty()) {
                        wakeThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        wakeThursday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                }
                if (dayFormat.format(format.parse(mPlanHistoryAllList.get(i).created_date)).equals("Friday", true)) {
                    if (mPlanHistoryAllList.get(i).napOne.isNotBlank()) {
                        napOneFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napOne.isBlank() && mPlanHistoryAllList.get(i).napOne.isEmpty()) {
                        napOneFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napOneFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).napTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).napTwo.isNotBlank()) {
                        napTwoFriday.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napTwo.isBlank() && mPlanHistoryAllList.get(i).napTwo.isEmpty()) {
                        napTwoFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napTwoFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).napThree.isNullOrEmpty() && mPlanHistoryAllList.get(i).napThree.isNotBlank()) {
                        napThreeFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napThree.isBlank() && mPlanHistoryAllList.get(i).napThree.isEmpty()) {
                        napThreeFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napThreeFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).doseOne.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseOne.isNotBlank()) {
                        doseOneFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseOne.isBlank() && mPlanHistoryAllList.get(i).doseOne.isEmpty()) {
                        doseOneFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseOneFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).doseTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseTwo.isNotBlank()) {
                        doseTwoFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseTwo.isBlank() && mPlanHistoryAllList.get(i).doseTwo.isEmpty()) {
                        doseTwoFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseTwoFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).wakeUp.isNullOrEmpty() && mPlanHistoryAllList.get(i).wakeUp.isNotBlank()) {
                        wakeFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).wakeUp.isBlank() && mPlanHistoryAllList.get(i).wakeUp.isEmpty()) {
                        wakeFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        wakeFriday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                }
                if (dayFormat.format(format.parse(mPlanHistoryAllList.get(i).created_date)).equals("Saturday", true)) {
                    if (mPlanHistoryAllList.get(i).napOne.isNotBlank()) {
                        napOneSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napOne.isBlank() && mPlanHistoryAllList.get(i).napOne.isEmpty()) {
                        napOneSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napOneSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).napTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).napTwo.isNotBlank()) {
                        napTwoSaturday.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napTwo.isBlank() && mPlanHistoryAllList.get(i).napTwo.isEmpty()) {
                        napTwoSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napTwoSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).napThree.isNullOrEmpty() && mPlanHistoryAllList.get(i).napThree.isNotBlank()) {
                        napThreeSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).napThree.isBlank() && mPlanHistoryAllList.get(i).napThree.isEmpty()) {
                        napThreeSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        napThreeSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).doseOne.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseOne.isNotBlank()) {
                        doseOneSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseOne.isBlank() && mPlanHistoryAllList.get(i).doseOne.isEmpty()) {
                        doseOneSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseOneSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                    if (!mPlanHistoryAllList.get(i).doseTwo.isNullOrEmpty() && mPlanHistoryAllList.get(i).doseTwo.isNotBlank()) {
                        doseTwoSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).doseTwo.isBlank() && mPlanHistoryAllList.get(i).doseTwo.isEmpty()) {
                        doseTwoSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        doseTwoSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }

                    if (!mPlanHistoryAllList.get(i).wakeUp.isNullOrEmpty() && mPlanHistoryAllList.get(i).wakeUp.isNotBlank()) {
                        wakeSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.green_dot))
                    } else if (mPlanHistoryAllList.get(i).wakeUp.isBlank() && mPlanHistoryAllList.get(i).wakeUp.isEmpty()) {
                        wakeSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.red_dot))
                    } else {
                        wakeSaturday?.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.yellow_dot))
                    }
                }


            }

            if (mPlanHistoryAllList.isNotEmpty()) {
                planReportTitle?.text = titleFormat.format(format.parse(mPlanHistoryAllList.get(0).created_date)) + " - " + titleFormat.format(format.parse(mPlanHistoryAllList.get(mPlanHistoryAllList.size - 1).created_date))

            }
            monthData(mMonthData)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun saveDate() {
        var planHistory = PlanHistoryAllRepository(activity!!)
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "10:15 AM", "10:15 AM", "10:15 AM", "10:15 PM", "2019-03-01 01:01", 1))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "10:15 AM", "10:15 AM", "10:15 AM", "10:15 PM", "2019-03-02 01:01", 2))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "",
                "10:15 AM", "10:15 AM", "10:15 AM", "10:15 PM", "2019-03-03 01:01", 3))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "10:15 AM", "10:15 AM", "10:15 AM", "10:15 PM", "2019-03-04 01:01", 4))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "10:15 AM", "10:15 AM", "10:15 AM", "10:15 PM", "2019-03-05 01:01", 5))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "", "10:15 AM", "10:15 AM", "10:15 PM", "2019-03-06 01:01", 6))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "10:15 AM", "", "10:15 AM", "10:15 PM", "2019-03-07 01:01", 7))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "10:15 AM", "10:15 AM", "10:15 AM", "10:15 PM", "2019-03-08 01:01", 1))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "10:15 AM", "10:15 AM", "10:15 AM", "10:15 PM", "2019-03-09 01:01", 2))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "10:15 AM", "10:15 AM", "10:15 AM", "10:15 PM", "2019-03-10 01:01", 3))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "", "10:15 PM",
                "10:15 AM", "10:15 AM", "10:15 AM", "10:15 PM", "2019-03-11 01:01", 4))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "10:15 AM", "", "10:15 AM", "10:15 PM", "2019-03-12 01:12", 5))
        planHistory.insertPlanHistoryAll(PlanHistoryAll(null, "10:15 AM", "10:15 AM", "10:15 PM",
                "10:15 AM", "10:15 AM", "", "10:15 PM", "2019-03-13 01:01", 6))
    }

    private fun monthData(monthData: List<PlanHistoryAll>) {
        janHashMap = HashMap()
        febHashMap = HashMap()
        marHashMap = HashMap()
        aprHashMap = HashMap()
        mayHashMap = HashMap()
        junHashMap = HashMap()
        julHashMap = HashMap()
        augHashMap = HashMap()
        sepHashMap = HashMap()
        octHashMap = HashMap()
        novHashMap = HashMap()
        decHashMap = HashMap()
        monthList = ArrayList()
        janList = ArrayList()
        febList = ArrayList()
        marList = ArrayList()
        aprList = ArrayList()
        mayList = ArrayList()
        junList = ArrayList()
        julyList = ArrayList()
        augList = ArrayList()
        sepList = ArrayList()
        octList = ArrayList()
        novList = ArrayList()
        decList = ArrayList()

        for (i in 0..monthData.size - 1) {
            println("Month data plan<>><>< " + monthData.get(i))

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("January", true)) {
                janList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("February", true)) {
                febList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("March", true)) {
                marList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("April", true)) {
                aprList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("May", true)) {
                mayList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("June", true)) {
                junList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("July", true)) {
                julyList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("August", true)) {
                augList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("September", true)) {
                sepList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("October", true)) {
                octList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("November", true)) {
                novList!!.add(monthData.get(i))
            }

            if (formatMonth.format(format.parse(monthData.get(i).created_date)).equals("December", true)) {
                decList!!.add(monthData.get(i))
            }


            ///

            try {


                if (janList != null) {
                    for (i in 0..janList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(janList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in janList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        janHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..janHashMap!!.size) {
                        println("Map<><>< " + janHashMap!!.get(i))
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "January"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i

                        for (eds in janHashMap!!.get(i)!!) {

                            data.featuredFlag = 1

                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }

                        }
                        monthList!!.add(data)

                    }
                }

                if (febList != null) {
                    for (i in 0..janList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(febList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in janList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        febHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..febHashMap!!.size) {
                        println("Map<><>< " + febHashMap!!.get(i))
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "January"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i

                        for (eds in febHashMap!!.get(i)!!) {

                            data.featuredFlag = 1

                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }

                        }
                        monthList!!.add(data)

                    }
                }




                if (marList != null) {
                    println("====================================================")
                    for (i in 0..marList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(marList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in marList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        marHashMap!!.put(weekOfMonth, datesList!!)
                    }
                    println("map:: "+marHashMap.toString())

                    for (i in 1..marHashMap!!.size) {
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "March"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i

                        monthList!!.add(data)

                    }

                }

                if (aprList != null) {
                    println("====================================================")
                    for (i in 0..aprList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(aprList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in aprList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        aprHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..aprHashMap!!.size) {
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "April"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in aprHashMap!!.get(i)!!) {
                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }
                        }
                        monthList!!.add(data)

                    }
                }

                if (mayList != null) {
                    println("====================================================")
                    for (i in 0..mayList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(mayList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in mayList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        mayHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..mayHashMap!!.size) {
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "May"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in mayHashMap!!.get(i)!!) {
                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }
                            monthList!!.add(data)

                        }
                    }

                }

                if (junList != null) {
                    println("====================================================")
                    for (i in 0..junList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(junList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in junList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        junHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..junHashMap!!.size) {
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "June"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in junHashMap!!.get(i)!!) {
                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }

                        }
                        monthList!!.add(data)

                    }

                }


                if (julyList != null) {
                    println("====================================================")
                    for (i in 0..julyList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(julyList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in julyList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        julHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..julHashMap!!.size) {
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "July"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in julHashMap!!.get(i)!!) {
                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }
                        }
                        monthList!!.add(data)

                    }
                }

                if (augList != null) {

                    println("====================================================")
                    for (i in 0..augList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(augList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in augList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        augHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..augHashMap!!.size) {
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "August"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in augHashMap!!.get(i)!!) {
                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }
                        }
                        monthList!!.add(data)

                    }
                }

                if (sepList != null) {
                    println("====================================================")
                    for (i in 0..sepList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(sepList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in sepList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        sepHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..sepHashMap!!.size) {
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "September"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in sepHashMap!!.get(i)!!) {
                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }

                        }
                        monthList!!.add(data)

                    }

                }

                if (octList != null) {
                    println("====================================================")
                    for (i in 0..octList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(octList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in octList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        octHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..octHashMap!!.size) {
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "October"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in octHashMap!!.get(i)!!) {
                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }

                        }
                        monthList!!.add(data)

                    }

                }

                if (novList != null) {
                    println("====================================================")
                    for (i in 0..novList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(novList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in novList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        novHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..novHashMap!!.size) {
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "November"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in novHashMap!!.get(i)!!) {
                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }

                        }
                        monthList!!.add(data)

                    }
                }

                if (decList != null) {
                    println("====================================================")
                    for (i in 0..decList!!.size - 1) {
                        var datesList = ArrayList<PlanHistoryAll>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(decList!!.get(i).created_date)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in decList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.created_date)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        decHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..decHashMap!!.size) {
                        var data = PlanHistoryYear()
                        if (i == 1) {
                            var headData = PlanHistoryYear()
                            headData.featuredFlag = 0
                            headData.month = "December"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in decHashMap!!.get(i)!!) {
                            when (dayFormat.format(format.parse(eds.created_date))) {
                                "Sunday" -> {
                                    data.wakeUP = eds.wakeUp
                                    data.doseOne = eds.doseOne
                                    data.doseTwo = eds.doseTwo
                                    data.napOne = eds.napOne
                                    data.napTwo = eds.napTwo
                                    data.napThree = eds.napThree
                                }
                                "Monday" -> {
                                    data.wakeUPM = eds.wakeUp
                                    data.doseOneM = eds.doseOne
                                    data.doseTwoM = eds.doseTwo
                                    data.napOneM = eds.napOne
                                    data.napTwoM = eds.napTwo
                                    data.napThreeM = eds.napThree
                                }
                                "Tuesday" -> {
                                    data.wakeUPT = eds.wakeUp
                                    data.doseOneT = eds.doseOne
                                    data.doseTwoT = eds.doseTwo
                                    data.napOneT = eds.napOne
                                    data.napTwoT = eds.napTwo
                                    data.napThreeT = eds.napThree
                                }
                                "Wednesday" -> {
                                    data.wakeUPW = eds.wakeUp
                                    data.doseOneW = eds.doseOne
                                    data.doseTwoW = eds.doseTwo
                                    data.napOneW = eds.napOne
                                    data.napTwoW = eds.napTwo
                                    data.napThreeW = eds.napThree
                                }
                                "Thursday" -> {
                                    data.wakeUPTh = eds.wakeUp
                                    data.doseOneTh = eds.doseOne
                                    data.doseTwoTh = eds.doseTwo
                                    data.napOneTh = eds.napOne
                                    data.napTwoTh = eds.napTwo
                                    data.napThreeTh = eds.napThree
                                }
                                "Friday" -> {
                                    data.wakeUPF = eds.wakeUp
                                    data.doseOneF = eds.doseOne
                                    data.doseTwoF = eds.doseTwo
                                    data.napOneF = eds.napOne
                                    data.napTwoF = eds.napTwo
                                    data.napThreeF = eds.napThree
                                }
                                "Saturday" -> {
                                    data.wakeUPSa = eds.wakeUp
                                    data.doseOneSa = eds.doseOne
                                    data.doseTwoSa = eds.doseTwo
                                    data.napOneSa = eds.napOne
                                    data.napTwoSa = eds.napTwo
                                    data.napThreeSa = eds.napThree
                                }


                            }
                        }
                        monthList!!.add(data)

                    }
                }

                try {
                    planRecyclerView?.setHasFixedSize(true)
                    var layoutManager = LinearLayoutManager(activity!!)
                    planRecyclerView?.layoutManager = layoutManager
                    adapter = PlanAdapter(activity!!, monthList!!.toList())
                    planRecyclerView?.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }


        }

    }

}