package myapp.net.inspire.report.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import kotlinx.android.synthetic.main.item_title.view.*
import myapp.net.inspire.R
import myapp.net.inspire.report.YearData

/**
 * Created by Alucard on 2/25/2019.
 */
class ProgressAdapter(val context: Context, val yearList: List<YearData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var itemView: View?
        var viewHolder: RecyclerView.ViewHolder?
        when (viewType) {
            0 -> {
                itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_title, parent, false)
                viewHolder = HeaderHolder(itemView)
            }

            else -> {
                itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_chart_body, parent, false)
                viewHolder = BodyHolder(itemView)
            }
        }
        return viewHolder!!
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var data = yearList.get(position)
        try {
            if (holder!!.itemViewType == 0) {
                var hol = holder as HeaderHolder
                hol.header.text = data.month
            } else {

                var hold = holder as BodyHolder
                hold!!.weekTitle!!.text=data.weekTitle
                edsSeverityChart(hold.chart1!!, data)
                cataplexyChart(hold.chart2!!, data)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return yearList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (yearList.get(position).featuredFlag == 0) {
            return 0
        } else {
            return 1
        }
    }

    class HeaderHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val header = itemView!!.mainTitle

    }

    class BodyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val weekTitle = itemView?.findViewById<TextView>(R.id.currentWeekTitle)
        val chart1 = itemView?.findViewById<LineChart>(R.id.chart1)
        val chart2 = itemView?.findViewById<LineChart>(R.id.chart2)
    }

    private fun edsSeverityChart(chart: LineChart, data: YearData) {
        chart.description.isEnabled = false


        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, data.sunday.toFloat()))
        entries.add(Entry(1f, data.monday!!.toFloat()))
        entries.add(Entry(2f, data.tuesday!!.toFloat()))
        entries.add(Entry(3f, data.wednesday!!.toFloat()))
        entries.add(Entry(4f, data.thursday!!.toFloat()))
        entries.add(Entry(5f, data.friday!!.toFloat()))
        entries.add(Entry(6f, data.saturday!!.toFloat()))

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
        chart.axisLeft.axisMinimum = 0f
        chart.axisLeft.axisMaximum = 3.5f

        // Setting Data
        val data = LineData(dataSet)
        chart.data = data

        chart.setPinchZoom(false)
        chart.description.isEnabled = false
        //refresh
        chart.invalidate()
    }

    private fun cataplexyChart(chart: LineChart, data: YearData) {
        chart.description.isEnabled = false


        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, data.sundayC.toFloat()))
        entries.add(Entry(1f, data.mondayC!!.toFloat()))
        entries.add(Entry(2f, data.tuesdayC!!.toFloat()))
        entries.add(Entry(3f, data.wednesdayC!!.toFloat()))
        entries.add(Entry(4f, data.thursdayC!!.toFloat()))
        entries.add(Entry(5f, data.fridayC!!.toFloat()))
        entries.add(Entry(6f, data.saturdayC!!.toFloat()))

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
        chart.axisLeft.axisMinimum = 0f
        chart.axisLeft.axisMaximum = 3.5f
        // Setting Data
        val data = LineData(dataSet)
        chart.data = data
        chart.setPinchZoom(false)
        chart.description.isEnabled = false
        //refresh
        chart.invalidate()
    }
}