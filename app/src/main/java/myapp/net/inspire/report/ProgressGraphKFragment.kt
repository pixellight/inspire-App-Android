package myapp.net.inspire.report

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import kotlinx.android.synthetic.main.report_graph.*
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.EdsSeverity
import myapp.net.inspire.data.repository.EdsSeverityRepository
import myapp.net.inspire.report.adapter.ProgressAdapter
import myapp.net.inspire.utils.DateTimeUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by QPay on 3/12/2019.
 */
class ProgressGraphKFragment : Fragment() {
    private var mEdsSeverityRepository: EdsSeverityRepository? = null
    val format = SimpleDateFormat("yyyy-MM-dd")
    val formatMonth = SimpleDateFormat("MMMM")
    val dayFormat = SimpleDateFormat("EEEE")
    val titleFormat = SimpleDateFormat("MMMM d")
    private var hashMap: HashMap<String, List<EdsSeverity>>? = null
    private var listEds: MutableList<EdsSeverity>? = null
    private var febEds: MutableList<EdsSeverity>? = null
    private var marEds: MutableList<EdsSeverity>? = null
    private var weekEds: MutableList<MonthData>? = null
    var week: Int = 0
    var woy = -1
    private var cataplexyList: MutableList<Float>? = null
    private var edsSeverityList: MutableList<Float>? = null
    private var sumEds: Int? = -1
    private var sunEds: Int? = -1
    private var monEds: Int? = -1
    private var tueEds: Int? = -1
    private var wedEds: Int? = -1
    private var thuEds: Int? = -1
    private var friEds: Int? = -1
    private var satEds: Int? = -1
    private var sunCat: Int? = -1
    private var monCat: Int? = -1
    private var tueCat: Int? = -1
    private var wedCat: Int? = -1
    private var thuCat: Int? = -1
    private var friCat: Int? = -1
    private var satCat: Int? = -1

    private var janList: MutableList<EdsSeverity>? = null
    private var febList: MutableList<EdsSeverity>? = null
    private var marList: MutableList<EdsSeverity>? = null
    private var aprList: MutableList<EdsSeverity>? = null
    private var mayList: MutableList<EdsSeverity>? = null
    private var junList: MutableList<EdsSeverity>? = null
    private var julyList: MutableList<EdsSeverity>? = null
    private var augList: MutableList<EdsSeverity>? = null
    private var sepList: MutableList<EdsSeverity>? = null
    private var octList: MutableList<EdsSeverity>? = null
    private var novList: MutableList<EdsSeverity>? = null
    private var decList: MutableList<EdsSeverity>? = null
    private var janHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var febHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var marHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var aprHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var mayHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var junHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var julHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var augHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var sepHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var octHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var novHashMap: HashMap<Int, List<EdsSeverity>>? = null
    private var decHashMap: HashMap<Int, List<EdsSeverity>>? = null

    private var adapter: ProgressAdapter? = null


    private var monthList: MutableList<YearData>? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.report_graph, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        graphWork()

        try {
            edsSeverityChart()
            firstOne()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun edsSeverityChart() {
        val chart = view!!.findViewById<View>(R.id.chart) as LineChart
        chart.description.isEnabled = false


        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, sunEds!!.toFloat()))
        entries.add(Entry(1f, monEds!!.toFloat()))
        entries.add(Entry(2f, tueEds!!.toFloat()))
        entries.add(Entry(3f, wedEds!!.toFloat()))

        entries.add(Entry(4f, thuEds!!.toFloat()))
        entries.add(Entry(5f, friEds!!.toFloat()))
        entries.add(Entry(6f, satEds!!.toFloat()))

        val dataSet = LineDataSet(entries, null)
        dataSet.color = ContextCompat.getColor(context, R.color.planBackground)
        dataSet.setCircleColor(ContextCompat.getColor(context, R.color.circleColour))
        dataSet.circleHoleColor = ContextCompat.getColor(context, R.color.circleColour)




        dataSet.circleRadius = 3.0f
        dataSet.lineWidth = 2.0f
        dataSet.valueTextColor = ContextCompat.getColor(context, R.color.transparent)

        //****
        // Controlling X axis
        val xAxis = chart.xAxis
        // Set the xAxis position to bottom. Default is top
        xAxis.position = XAxis.XAxisPosition.BOTH_SIDED


        //Customizing activity_graph axis value
        val months = arrayOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")

        val formatter = IAxisValueFormatter { value, axis -> months[value.toInt()] }
        xAxis.granularity = 1f // minimum axis-step (interval) is 1
        xAxis.valueFormatter = formatter

        //***
        // Controlling right side of y axis
        val yAxisRight = chart.axisRight
        yAxisRight.isEnabled = false


        //***
        // Controlling left side of y axis
        val yAxisLeft = chart.axisLeft
        yAxisLeft.granularity = 1f
        chart.axisLeft.axisMinimum = -0.2f
        chart.axisLeft.axisMaximum = 3.5f

        // Setting Data
        val data = LineData(dataSet)
        chart.data = data

        chart.setPinchZoom(false)
        chart.description.isEnabled = false
        //refresh
        chart.invalidate()
    }

    private fun firstOne() {
        val chart = view!!.findViewById<View>(R.id.chart1) as LineChart
        chart.description.isEnabled = false


        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, sunCat!!.toFloat()))
        entries.add(Entry(1f, monCat!!.toFloat()))
        entries.add(Entry(2f, tueCat!!.toFloat()))
        entries.add(Entry(3f, wedCat!!.toFloat()))
        entries.add(Entry(4f, thuCat!!.toFloat()))
        entries.add(Entry(5f, friCat!!.toFloat()))
        entries.add(Entry(6f, satCat!!.toFloat()))

        val dataSet = LineDataSet(entries, null)
        dataSet.color = ContextCompat.getColor(context, R.color.colorPrimary)
        dataSet.setCircleColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        dataSet.circleHoleColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)

        dataSet.circleRadius = 3.0f
        dataSet.lineWidth = 2.0f
        dataSet.valueTextColor = ContextCompat.getColor(context, R.color.transparent)

        //****
        // Controlling X axis
        val xAxis = chart.xAxis
        // Set the xAxis position to bottom. Default is top
        xAxis.position = XAxis.XAxisPosition.BOTH_SIDED


        //Customizing activity_graph axis value
        val months = arrayOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")


        val formatter = IAxisValueFormatter { value, axis -> months[value.toInt()] }
        xAxis.granularity = 1f // minimum axis-step (interval) is 1
        xAxis.valueFormatter = formatter

        //***
        // Controlling right side of y axis
        val yAxisRight = chart.axisRight
        yAxisRight.isEnabled = false

        //***
        // Controlling left side of y axis
        val yAxisLeft = chart.axisLeft
        yAxisLeft.granularity = 1f
        chart.axisLeft.axisMinimum = -0.2f
        chart.axisLeft.axisMaximum = 3.5f
        // Setting Data
        val data = LineData(dataSet)
        chart.data = data
        chart.setPinchZoom(false)
        chart.description.isEnabled = false
        //refresh
        chart.invalidate()
    }

    private fun graphWork() {

        try {
            mEdsSeverityRepository = EdsSeverityRepository(activity!!)
            var mEdsSeverityList = mEdsSeverityRepository!!.getAllData()
            var monthData = mEdsSeverityRepository!!.getMonthData()

            hashMap = HashMap()
            marEds = ArrayList()
            weekEds = ArrayList()
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
            for (i in 0..mEdsSeverityList.size - 1) {

                var edsSeverity = EdsSeverity(mEdsSeverityList.get(i).id, mEdsSeverityList.get(i).questionOne, mEdsSeverityList.get(i).questionTwo, mEdsSeverityList.get(i).questionThree, mEdsSeverityList.get(i).questionFour,
                        mEdsSeverityList.get(i).questionFive, mEdsSeverityList.get(i).questionSix, mEdsSeverityList.get(i).questionSeven, mEdsSeverityList.get(i).questionEight, mEdsSeverityList.get(i).sum,
                        mEdsSeverityList.get(i).cataplexy, mEdsSeverityList.get(i).createdDate)
                marEds!!.add(edsSeverity!!)


            }

            for (i in 0..monthData.size - 1) {
                println("Month data:: " + monthData.get(i))

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("January", true)) {
                    janList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("February", true)) {
                    febList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("March", true)) {
                    marList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("April", true)) {
                    aprList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("May", true)) {
                    mayList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("June", true)) {
                    junList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("July", true)) {
                    julyList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("August", true)) {
                    augList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("September", true)) {
                    sepList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("October", true)) {
                    octList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("November", true)) {
                    novList!!.add(monthData.get(i))
                }

                if (formatMonth.format(format.parse(monthData.get(i).createdDate)).equals("December", true)) {
                    decList!!.add(monthData.get(i))
                }


            }

            if (mEdsSeverityList.isNotEmpty()) {
                var first=DateTimeUtils.getDayNumberSuffix(titleFormat.format(format.parse(mEdsSeverityList.get(0).createdDate)).split(" ")[1].toInt())
                var second=DateTimeUtils.getDayNumberSuffix(titleFormat.format(format.parse(mEdsSeverityList.get(mEdsSeverityList.size-1).createdDate)).split(" ")[1].toInt())


                currentWeekTitle?.text = titleFormat.format(format.parse(mEdsSeverityList.get(0).createdDate)) +first+ " - " + titleFormat.format(format.parse(mEdsSeverityList.get(mEdsSeverityList.size - 1).createdDate))+second

            }




            for (date in marEds!!) {

                println("Data:: " + date)
                if (date.sum <= 10) {
                    sumEds = 0
                } else if (date.sum > 10 && date.sum <= 12) {
                    sumEds = 1
                } else if (date.sum > 12 && date.sum <= 15) {
                    sumEds = 2
                } else {
                    sumEds = 3
                }

                when (dayFormat.format(format.parse(date.createdDate))) {
                    "Sunday" -> {
                        sunEds = sumEds
                        sunCat = date.cataplexy
                    }
                    "Monday" -> {
                        monEds = sumEds
                        monCat = date.cataplexy
                    }
                    "Tuesday" -> {
                        tueEds = sumEds
                        tueCat = date.cataplexy
                    }
                    "Wednesday" -> {
                        wedEds = sumEds
                        wedCat = date.cataplexy
                    }
                    "Thursday" -> {
                        thuEds = sumEds
                        thuCat = date.cataplexy
                    }
                    "Friday" -> {
                        friEds = sumEds
                        friCat = date.cataplexy
                    }
                    "Saturday" -> {
                        satEds = sumEds
                        satCat = date.cataplexy
                    }


                }

            }

            try {


                if (janList != null) {
                    for (i in 0..janList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(janList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in janList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        janHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..janHashMap!!.size) {
                        println("Map<><>< "+janHashMap!!.get(i))
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "January"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i

                        for (eds in janHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }
                            data.featuredFlag = 1

                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }

                        }
                        monthList!!.add(data)

                    }
                }


//                if (febList != null) {
//                    println("====================================================")
//                    for (i in 0..febList!!.size - 1) {
//                        var datesList = ArrayList<EdsSeverity>()
//                        var calendar = Calendar.getInstance()
//                        calendar.time = format.parse(febList!!.get(i).createdDate)
//
//                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
//                        for (date in febList!!) {
//                            val c = Calendar.getInstance()
//                            c.time = format.parse(date.createdDate)
//                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
//                                datesList.add(date)
//                            }
//                        }
//                        febHashMap!!.put(weekOfMonth, datesList!!)
//                    }
//
//                    for (i in 1..febHashMap!!.size) {
//                        var data = YearData()
//                        data.featuredFlag = 0
//                        data.month = "February"
//                        for (eds in febHashMap!!.get(i)!!) {
//                            if (eds.sum <= 10) {
//                                sumEds = 0
//                            } else if (eds.sum > 10 && eds.sum <= 12) {
//                                sumEds = 1
//                            } else if (eds.sum > 12 && eds.sum <= 15) {
//                                sumEds = 2
//                            } else {
//                                sumEds = 3
//                            }
//                            data.featuredFlag = 1
//                            when (dayFormat.format(format.parse(eds.createdDate))) {
//                                "Sunday" -> {
//                                    data.sunday = sumEds!!
//                                    data.sundayC = eds.cataplexy
//                                }
//                                "Monday" -> {
//                                    data.monday = sumEds!!
//                                    data.mondayC = eds.cataplexy
//                                }
//                                "Tuesday" -> {
//                                    data.tuesday = sumEds!!
//                                    data.tuesdayC = eds.cataplexy
//                                }
//                                "Wednesday" -> {
//                                    data.wednesday = sumEds!!
//                                    data.wednesdayC = eds.cataplexy
//                                }
//                                "Thursday" -> {
//                                    data.thursday = sumEds!!
//                                    data.thursdayC = eds.cataplexy
//                                }
//                                "Friday" -> {
//                                    data.friday = sumEds!!
//                                    data.fridayC = eds.cataplexy
//                                }
//                                "Saturday" -> {
//                                    data.saturday = sumEds!!
//                                    data.saturdayC = eds.cataplexy
//                                }
//
//
//                            }
//                            monthList!!.add(data)
//
//                        }
//                    }
//
//                }

                if (marList != null) {
                    println("====================================================")
                    for (i in 0..marList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(marList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in marList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        marHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..marHashMap!!.size) {
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "March"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in marHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }
                            data.featuredFlag = 1
                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }

                        }
                        monthList!!.add(data)

                    }

                }

                if (aprList != null) {
                    println("====================================================")
                    for (i in 0..aprList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(aprList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in aprList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        aprHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..aprHashMap!!.size) {
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "April"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in aprHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }
                            data.featuredFlag = 1
                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }
                        }
                        monthList!!.add(data)

                    }
                }

                if (mayList != null) {
                    println("====================================================")
                    for (i in 0..mayList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(mayList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in mayList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        mayHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..mayHashMap!!.size) {
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "May"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in mayHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }

                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }
                            monthList!!.add(data)

                        }
                    }

                }

                if (junList != null) {
                    println("====================================================")
                    for (i in 0..junList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(junList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in junList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        junHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..junHashMap!!.size) {
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "June"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in junHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }
                            data.featuredFlag = 1
                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }

                        }
                        monthList!!.add(data)

                    }

                }


                if (julyList != null) {
                    println("====================================================")
                    for (i in 0..julyList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(julyList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in julyList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        julHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..julHashMap!!.size) {
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "July"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in julHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }
                            data.featuredFlag = 1
                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }
                        }
                        monthList!!.add(data)

                    }
                }

                if (augList != null) {

                    println("====================================================")
                    for (i in 0..augList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(augList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in augList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        augHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..augHashMap!!.size) {
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "August"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in augHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }
                            data.featuredFlag = 1
                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }
                        }
                        monthList!!.add(data)

                    }
                }

                if (sepList != null) {
                    println("====================================================")
                    for (i in 0..sepList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(sepList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in sepList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        sepHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..sepHashMap!!.size) {
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "September"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in sepHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }
                            data.featuredFlag = 1
                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }

                        }
                        monthList!!.add(data)

                    }

                }

                if (octList != null) {
                    println("====================================================")
                    for (i in 0..octList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(octList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in octList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        octHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..octHashMap!!.size) {
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "October"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in octHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }
                            data.featuredFlag = 1
                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }

                        }
                        monthList!!.add(data)

                    }

                }

                if (novList != null) {
                    println("====================================================")
                    for (i in 0..novList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(novList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in novList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        novHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..novHashMap!!.size) {
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "November"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in novHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }
                            data.featuredFlag = 1
                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }

                        }
                        monthList!!.add(data)

                    }
                }

                if (decList != null) {
                    println("====================================================")
                    for (i in 0..decList!!.size - 1) {
                        var datesList = ArrayList<EdsSeverity>()
                        var calendar = Calendar.getInstance()
                        calendar.time = format.parse(decList!!.get(i).createdDate)

                        var weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH)
                        for (date in decList!!) {
                            val c = Calendar.getInstance()
                            c.time = format.parse(date.createdDate)
                            if (weekOfMonth === c.get(Calendar.WEEK_OF_MONTH)) {
                                datesList.add(date)
                            }
                        }
                        decHashMap!!.put(weekOfMonth, datesList!!)
                    }

                    for (i in 1..decHashMap!!.size) {
                        var data = YearData()
                        if (i == 1) {
                            var headData = YearData()
                            headData.featuredFlag = 0
                            headData.month = "December"
                            monthList!!.add(headData)
                        }
                        data.weekTitle = "Week " + i
                        for (eds in decHashMap!!.get(i)!!) {
                            if (eds.sum <= 10) {
                                sumEds = 0
                            } else if (eds.sum > 10 && eds.sum <= 12) {
                                sumEds = 1
                            } else if (eds.sum > 12 && eds.sum <= 15) {
                                sumEds = 2
                            } else {
                                sumEds = 3
                            }
                            data.featuredFlag = 1
                            when (dayFormat.format(format.parse(eds.createdDate))) {
                                "Sunday" -> {
                                    data.sunday = sumEds!!
                                    data.sundayC = eds.cataplexy
                                }
                                "Monday" -> {
                                    data.monday = sumEds!!
                                    data.mondayC = eds.cataplexy
                                }
                                "Tuesday" -> {
                                    data.tuesday = sumEds!!
                                    data.tuesdayC = eds.cataplexy
                                }
                                "Wednesday" -> {
                                    data.wednesday = sumEds!!
                                    data.wednesdayC = eds.cataplexy
                                }
                                "Thursday" -> {
                                    data.thursday = sumEds!!
                                    data.thursdayC = eds.cataplexy
                                }
                                "Friday" -> {
                                    data.friday = sumEds!!
                                    data.fridayC = eds.cataplexy
                                }
                                "Saturday" -> {
                                    data.saturday = sumEds!!
                                    data.saturdayC = eds.cataplexy
                                }


                            }
                        }
                        monthList!!.add(data)

                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                recyclerView?.setHasFixedSize(true)
                var layoutManager = LinearLayoutManager(activity!!)
                recyclerView?.layoutManager = layoutManager
                adapter = ProgressAdapter(activity!!, monthList!!.toList())
                recyclerView?.adapter = adapter
                adapter!!.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getWeekOfYear(date: Date): Int {
        var calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }


}