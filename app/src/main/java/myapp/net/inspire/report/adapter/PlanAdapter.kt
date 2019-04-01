package myapp.net.inspire.report.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_plan_report.view.*
import kotlinx.android.synthetic.main.item_title.view.*
import myapp.net.inspire.R
import myapp.net.inspire.report.PlanHistoryYear
import java.text.SimpleDateFormat

/**
 * Created by QPay on 3/18/2019.
 */
class PlanAdapter(val context: Context, val yearList: List<PlanHistoryYear>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val format = SimpleDateFormat("yyyy-MM-dd")
    val formatMonth = SimpleDateFormat("MMMM")
    val dayFormat = SimpleDateFormat("EEEE")
    val titleFormat = SimpleDateFormat("dd EEEE")
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var itemView: View?
        var viewHolder: RecyclerView.ViewHolder?
        when (viewType) {
            0 -> {
                itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_title, parent, false)
                viewHolder = ProgressAdapter.HeaderHolder(itemView)
            }

            else -> {
                itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_plan_report, parent, false)
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
//                hold!!.weekTitle!!.text=data.weekTitle
                planReportTable(data,hold!!)

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
        val napOneSundayM = itemView!!.napOneSundayM
        val napTwoSundayM = itemView!!.napTwoSundayM
        val napThreeSundayM = itemView!!.napThreeSundayM
        val doseOneSundayM = itemView!!.doseOneSundayM
        val doseTwoSundayM = itemView!!.doseTwoSundayM
        val wakeSundayM = itemView!!.wakeSundayM

        val napOneMondayM = itemView!!.napOneMondayM
        val napTwoMondayM = itemView!!.napTwoMondayM
        val napThreeMondayM = itemView!!.napThreeMondayM
        val doseOneMondayM = itemView!!.doseOneMondayM
        val doseTwoMondayM = itemView!!.doseTwoMondayM
        val wakeMondayM = itemView!!.wakeMondayM

        val napOneTuesdayM = itemView!!.napOneTuesdayM
        val napTwoTuesdayM = itemView!!.napTwoTuesdayM
        val napThreeTuesdayM = itemView!!.napThreeTuesdayM
        val doseOneTuesdayM = itemView!!.doseOneTuesdayM
        val doseTwoTuesdayM = itemView!!.doseTwoTuesdayM
        val wakeTuesdayM = itemView!!.wakeTuesdayM

        val napOneWednesdayM = itemView!!.napOneWednesdayM
        val napTwoWednesdayM = itemView!!.napTwoWednesdayM
        val napThreeWednesdayM = itemView!!.napThreeWednesdayM
        val doseOneWednesdayM = itemView!!.doseOneWednesdayM
        val doseTwoWednesdayM = itemView!!.doseTwoWednesdayM
        val wakeWednesdayM = itemView!!.wakeWednesdayM

        val napOneThursdayM = itemView!!.napOneThursdayM
        val napTwoThursdayM = itemView!!.napTwoThursdayM
        val napThreeThursdayM = itemView!!.napThreeThursdayM
        val doseOneThursdayM = itemView!!.doseOneThursdayM
        val doseTwoThursdayM = itemView!!.doseTwoThursdayM
        val wakeThursdayM = itemView!!.wakeThursdayM

        val napOneFridayM = itemView!!.napOneFridayM
        val napTwoFridayM = itemView!!.napTwoFridayM
        val napThreeFridayM = itemView!!.napThreeFridayM
        val doseOneFridayM = itemView!!.doseOneFridayM
        val doseTwoFridayM = itemView!!.doseTwoFridayM
        val wakeFridayM = itemView!!.wakeFridayM

        val napOneSaturdayM = itemView!!.napOneSaturdayM
        val napTwoSaturdayM = itemView!!.napTwoSaturdayM
        val napThreeSaturdayM = itemView!!.napThreeSaturdayM
        val doseOneSaturdayM = itemView!!.doseOneSaturdayM
        val doseTwoSaturdayM = itemView!!.doseTwoSaturdayM
        val wakeSaturdayM = itemView!!.wakeSaturdayM

    }

    private fun planReportTable(data: PlanHistoryYear, holder: BodyHolder) {
        if (dayFormat.format(format.parse(data.createdDate)).equals("Sunday", true)) {

            if (data.napOne.isNotBlank()) {
                holder.napOneSundayM?.text = data.napOne
                holder.napOneSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napOne.isBlank() && data.napOne.isEmpty()) {
                holder.napOneSundayM?.text = data.napOne
                holder.napOneSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napOneSundayM?.text = data.napOne
                holder.napOneSundayM?.setTextColor(Color.YELLOW)
            }
            if (!data.napTwo.isNullOrEmpty() && data.napTwo.isNotBlank()) {
                holder.napTwoSundayM?.text = data.napTwo
                holder.napTwoSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napTwo.isBlank() && data.napTwo.isEmpty()) {
                holder.napTwoSundayM?.text = data.napTwo
                holder.napTwoSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napTwoSundayM?.text = data.napTwo
                holder.napTwoSundayM?.setTextColor(Color.YELLOW)
            }

            if (!data.napThree.isNullOrEmpty() && data.napThree.isNotBlank()) {
                holder.napThreeSundayM?.text = data.napThree
                holder.napThreeSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napThree.isBlank() && data.napThree.isEmpty()) {
                holder.napThreeSundayM?.text = data.napThree
                holder.napThreeSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napThreeSundayM?.text = data.napThree
                holder.napThreeSundayM?.setTextColor(Color.YELLOW)
            }

            if (!data.doseOne.isNullOrEmpty() && data.doseOne.isNotBlank()) {
                holder.doseOneSundayM?.text = data.doseOne
                holder.doseOneSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseOne.isBlank() && data.doseOne.isEmpty()) {
                holder.doseOneSundayM?.text = data.doseOne
                holder.doseOneSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseOneSundayM?.text = data.doseOne
                holder.doseOneSundayM?.setTextColor(Color.YELLOW)
            }
            if (!data.doseTwo.isNullOrEmpty() && data.doseTwo.isNotBlank()) {
                holder.doseTwoSundayM?.text = data.doseTwo
                holder.doseTwoSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseTwo.isBlank() && data.doseTwo.isEmpty()) {
                holder.doseTwoSundayM?.text = data.doseTwo
                holder.doseTwoSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseTwoSundayM?.text = data.doseTwo
                holder.doseTwoSundayM?.setTextColor(Color.YELLOW)
            }

            if (!data.wakeUP.isNullOrEmpty() && data.wakeUP.isNotBlank()) {
                holder.wakeSundayM?.text = data.wakeUP
                holder.wakeSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.wakeUP.isBlank() && data.wakeUP.isEmpty()) {
                holder.wakeSundayM?.text = data.wakeUP
                holder.wakeSundayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.wakeSundayM?.text = data.wakeUP
                holder.wakeSundayM?.setTextColor(Color.YELLOW)
            }
        }
        if (dayFormat.format(format.parse(data.createdDate)).equals("Monday", true)) {
            if (data.napOneM.isNotBlank()) {
                holder.napOneMondayM?.text = data.napOneM
                holder.napOneMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napOneM.isBlank() && data.napOneM.isEmpty()) {
                holder.napOneMondayM?.text = data.napOneM
                holder.napOneMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napOneMondayM?.text = data.napOneM
                holder.napOneMondayM?.setTextColor(Color.YELLOW)
            }
            if (!data.napTwoM.isNullOrEmpty() && data.napTwoM.isNotBlank()) {
                holder.napTwoMondayM?.text = data.napTwoM
                holder.napTwoMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napTwoM.isBlank() && data.napTwoM.isEmpty()) {
                holder.napTwoMondayM?.text = data.napTwoM
                holder.napTwoMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napTwoMondayM?.text = data.napTwoM
                holder.napTwoMondayM?.setTextColor(Color.YELLOW)
            }

            if (!data.napThreeM.isNullOrEmpty() && data.napThreeM.isNotBlank()) {
                holder.napThreeMondayM?.text = data.napThreeM
                holder.napThreeMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napThreeM.isBlank() && data.napThreeM.isEmpty()) {
                holder.napThreeMondayM?.text = data.napThreeM
                holder.napThreeMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napThreeMondayM?.text = data.napThreeM
                holder.napThreeMondayM?.setTextColor(Color.YELLOW)
            }

            if (!data.doseOneM.isNullOrEmpty() && data.doseOneM.isNotBlank()) {
                holder.doseOneMondayM?.text = data.doseOneM
                holder.doseOneMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseOneM.isBlank() && data.doseOneM.isEmpty()) {
                holder.doseOneMondayM?.text = data.doseOneM
                holder.doseOneMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseOneMondayM?.text = data.doseOneM
                holder.doseOneMondayM?.setTextColor(Color.YELLOW)
            }
            if (!data.doseTwoM.isNullOrEmpty() && data.doseTwoM.isNotBlank()) {
                holder.doseTwoMondayM?.text = data.doseTwoM
                holder.doseTwoMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseTwoM.isBlank() && data.doseTwoM.isEmpty()) {
                holder.doseTwoMondayM?.text = data.doseTwoM
                holder.doseTwoMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseTwoMondayM?.text = data.doseTwoM
                holder.doseTwoMondayM?.setTextColor(Color.YELLOW)
            }

            if (!data.wakeUPM.isNullOrEmpty() && data.wakeUPM.isNotBlank()) {
                holder.wakeMondayM?.text = data.wakeUPM
                holder.wakeMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.wakeUPM.isBlank() && data.wakeUPM.isEmpty()) {
                holder.wakeMondayM?.text = data.wakeUPM
                holder.wakeMondayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.wakeMondayM?.text = data.wakeUPM
                holder.wakeMondayM?.setTextColor(Color.YELLOW)
            }
        }
        if (dayFormat.format(format.parse(data.createdDate)).equals("Tuesday", true)) {
            if (data.napOneT.isNotBlank()) {
                holder.napOneTuesdayM?.text = data.napOneT
                holder.napOneTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napOneT.isBlank() && data.napOneT.isEmpty()) {
                holder.napOneTuesdayM?.text = data.napOneT
                holder.napOneTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napOneTuesdayM?.text = data.napOneT
                holder.napOneTuesdayM?.setTextColor(Color.YELLOW)
            }
            if (!data.napTwoT.isNullOrEmpty() && data.napTwoT.isNotBlank()) {
                holder.napTwoTuesdayM?.text = data.napTwoT
                holder.napTwoTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napTwoT.isBlank() && data.napTwoT.isEmpty()) {
                holder.napTwoTuesdayM?.text = data.napTwoT
                holder.napTwoTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napTwoTuesdayM?.text = data.napTwoT
                holder.napTwoTuesdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.napThreeT.isNullOrEmpty() && data.napThreeT.isNotBlank()) {
                holder.napThreeTuesdayM?.text = data.napThreeT
                holder.napThreeTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napThreeT.isBlank() && data.napThreeT.isEmpty()) {
                holder.napThreeTuesdayM?.text = data.napThreeT
                holder.napThreeTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napThreeTuesdayM?.text = data.napThreeT
                holder.napThreeTuesdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.doseOneT.isNullOrEmpty() && data.doseOneT.isNotBlank()) {
                holder.doseOneTuesdayM?.text = data.doseOneT
                holder.doseOneTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseOneT.isBlank() && data.doseOneT.isEmpty()) {
                holder.doseOneTuesdayM?.text = data.doseOneT
                holder.doseOneTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseOneTuesdayM?.text = data.doseOneT
                holder.doseOneTuesdayM?.setTextColor(Color.YELLOW)
            }
            if (!data.doseTwoT.isNullOrEmpty() && data.doseTwoT.isNotBlank()) {
                holder.doseTwoTuesdayM?.text = data.doseTwoT
                holder.doseTwoTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseTwoM.isBlank() && data.doseTwoT.isEmpty()) {
                holder.doseTwoTuesdayM?.text = data.doseTwoT
                holder.doseTwoTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseTwoTuesdayM?.text = data.doseTwoT
                holder.doseTwoTuesdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.wakeUPT.isNullOrEmpty() && data.wakeUPT.isNotBlank()) {
                holder.wakeTuesdayM?.text = data.wakeUPT
                holder.wakeTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.wakeUPM.isBlank() && data.wakeUPT.isEmpty()) {
                holder.wakeTuesdayM?.text = data.wakeUPT
                holder.wakeTuesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.wakeTuesdayM?.text = data.wakeUPT
                holder.wakeTuesdayM?.setTextColor(Color.YELLOW)
            }
        }
        if (dayFormat.format(format.parse(data.createdDate)).equals("Wednesday", true)) {
            if (data.napOneW.isNotBlank()) {
                holder.napOneWednesdayM?.text = data.napOneW
                holder.napOneWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napOneW.isBlank() && data.napOneW.isEmpty()) {
                holder.napOneWednesdayM?.text = data.napOneW
                holder.napOneWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napOneWednesdayM?.text = data.napOneW
                holder.napOneWednesdayM?.setTextColor(Color.YELLOW)
            }
            if (!data.napTwoW.isNullOrEmpty() && data.napTwoW.isNotBlank()) {
                holder.napTwoWednesdayM?.text = data.napTwoW
                holder.napTwoWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napTwoW.isBlank() && data.napTwoW.isEmpty()) {
                holder.napTwoWednesdayM?.text = data.napTwoW
                holder.napTwoWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napTwoWednesdayM?.text = data.napTwoW
                holder.napTwoWednesdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.napThreeW.isNullOrEmpty() && data.napThreeW.isNotBlank()) {
                holder.napThreeWednesdayM?.text = data.napThreeW
                holder.napThreeWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napThreeW.isBlank() && data.napThreeW.isEmpty()) {
                holder.napThreeWednesdayM?.text = data.napThreeW
                holder.napThreeWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napThreeWednesdayM?.text = data.napThreeW
                holder.napThreeWednesdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.doseOneW.isNullOrEmpty() && data.doseOneW.isNotBlank()) {
                holder.doseOneWednesdayM?.text = data.doseOneW
                holder.doseOneWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseOneW.isBlank() && data.doseOneW.isEmpty()) {
                holder.doseOneWednesdayM?.text = data.doseOneW
                holder.doseOneWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseOneWednesdayM?.text = data.doseOneW
                holder.doseOneWednesdayM?.setTextColor(Color.YELLOW)
            }
            if (!data.doseTwoW.isNullOrEmpty() && data.doseTwoW.isNotBlank()) {
                holder.doseTwoWednesdayM?.text = data.doseTwoW
                holder.doseTwoWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseTwoW.isBlank() && data.doseTwoW.isEmpty()) {
                holder.doseTwoWednesdayM?.text = data.doseTwoT
                holder.doseTwoWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseTwoWednesdayM?.text = data.doseTwoW
                holder.doseTwoWednesdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.wakeUPW.isNullOrEmpty() && data.wakeUPW.isNotBlank()) {
                holder.wakeWednesdayM?.text = data.wakeUPW
                holder.wakeWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.wakeUPW.isBlank() && data.wakeUPW.isEmpty()) {
                holder.wakeWednesdayM?.text = data.wakeUPW
                holder.wakeWednesdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.wakeWednesdayM?.text = data.wakeUPW
                holder.wakeWednesdayM?.setTextColor(Color.YELLOW)
            }
        }
        if (dayFormat.format(format.parse(data.createdDate)).equals("Thursday", true)) {
            if (data.napOneTh.isNotBlank()) {
                holder.napOneThursdayM?.text = data.napOneTh
                holder.napOneThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napOneTh.isBlank() && data.napOneTh.isEmpty()) {
                holder.napOneThursdayM?.text = data.napOneTh
                holder.napOneThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napOneThursdayM?.text = data.napOneTh
                holder.napOneThursdayM?.setTextColor(Color.YELLOW)
            }
            if (!data.napTwoTh.isNullOrEmpty() && data.napTwoTh.isNotBlank()) {
                holder.napTwoThursdayM?.text = data.napTwoTh
                holder.napTwoThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napTwoTh.isBlank() && data.napTwoTh.isEmpty()) {
                holder.napTwoThursdayM?.text = data.napTwoTh
                holder.napTwoThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napTwoThursdayM?.text = data.napTwoTh
                holder.napTwoThursdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.napThreeTh.isNullOrEmpty() && data.napThreeTh.isNotBlank()) {
                holder.napThreeThursdayM?.text = data.napThreeTh
                holder.napThreeThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napThreeTh.isBlank() && data.napThreeTh.isEmpty()) {
                holder.napThreeThursdayM?.text = data.napThreeTh
                holder.napThreeThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napThreeThursdayM?.text = data.napThreeTh
                holder.napThreeThursdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.doseOneTh.isNullOrEmpty() && data.doseOneTh.isNotBlank()) {
                holder.doseOneThursdayM?.text = data.doseOneTh
                holder.doseOneThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseOneTh.isBlank() && data.doseOneTh.isEmpty()) {
                holder.doseOneThursdayM?.text = data.doseOneTh
                holder.doseOneThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseOneThursdayM?.text = data.doseOneTh
                holder.doseOneThursdayM?.setTextColor(Color.YELLOW)
            }
            if (!data.doseTwoTh.isNullOrEmpty() && data.doseTwoTh.isNotBlank()) {
                holder.doseTwoThursdayM?.text = data.doseTwoTh
                holder.doseTwoThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseTwoW.isBlank() && data.doseTwoTh.isEmpty()) {
                holder.doseTwoThursdayM?.text = data.doseTwoTh
                holder.doseTwoThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseTwoThursdayM?.text = data.doseTwoTh
                holder.doseTwoThursdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.wakeUPTh.isNullOrEmpty() && data.wakeUPTh.isNotBlank()) {
                holder.wakeThursdayM?.text = data.wakeUPTh
                holder.wakeThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.wakeUPTh.isBlank() && data.wakeUPTh.isEmpty()) {
                holder.wakeThursdayM?.text = data.wakeUPTh
                holder.wakeThursdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.wakeThursdayM?.text = data.wakeUPTh
                holder.wakeThursdayM?.setTextColor(Color.YELLOW)
            }
        }
        if (dayFormat.format(format.parse(data.createdDate)).equals("Friday", true)) {
            if (data.napOneF.isNotBlank()) {
                holder.napOneFridayM?.text = data.napOneF
                holder.napOneFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napOneF.isBlank() && data.napOneF.isEmpty()) {
                holder.napOneFridayM?.text = data.napOneF
                holder.napOneFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napOneFridayM?.text = data.napOneF
                holder.napOneFridayM?.setTextColor(Color.YELLOW)
            }
            if (!data.napTwoF.isNullOrEmpty() && data.napTwoF.isNotBlank()) {
                holder.napTwoFridayM?.text = data.napTwoF
                holder.napTwoFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napTwoF.isBlank() && data.napTwoF.isEmpty()) {
                holder.napTwoFridayM?.text = data.napTwoF
                holder.napTwoFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napTwoFridayM?.text = data.napTwoF
                holder.napTwoFridayM?.setTextColor(Color.YELLOW)
            }

            if (!data.napThreeF.isNullOrEmpty() && data.napThreeF.isNotBlank()) {
                holder.napThreeFridayM?.text = data.napThreeF
                holder.napThreeFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napThreeF.isBlank() && data.napThreeF.isEmpty()) {
                holder.napThreeFridayM?.text = data.napThreeF
                holder.napThreeFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napThreeFridayM?.text = data.napThreeF
                holder.napThreeFridayM?.setTextColor(Color.YELLOW)
            }

            if (!data.doseOneF.isNullOrEmpty() && data.doseOneF.isNotBlank()) {
                holder.doseOneFridayM?.text = data.doseOneF
                holder.doseOneFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseOneF.isBlank() && data.doseOneF.isEmpty()) {
                holder.doseOneFridayM?.text = data.doseOneF
                holder.doseOneFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseOneFridayM?.text = data.doseOneF
                holder.doseOneFridayM?.setTextColor(Color.YELLOW)
            }
            if (!data.doseTwoF.isNullOrEmpty() && data.doseTwoF.isNotBlank()) {
                holder.doseTwoFridayM?.text = data.doseTwoF
                holder.doseTwoFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseTwoF.isBlank() && data.doseTwoF.isEmpty()) {
                holder.doseTwoFridayM?.text = data.doseTwoF
                holder.doseTwoFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseTwoFridayM?.text = data.doseTwoF
                holder.doseTwoFridayM?.setTextColor(Color.YELLOW)
            }

            if (!data.wakeUPF.isNullOrEmpty() && data.wakeUPF.isNotBlank()) {
                holder.wakeFridayM?.text = data.wakeUPF
                holder.wakeFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.wakeUPF.isBlank() && data.wakeUPF.isEmpty()) {
                holder.wakeFridayM?.text = data.wakeUPF
                holder.wakeFridayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.wakeFridayM?.text = data.wakeUPF
                holder.wakeFridayM?.setTextColor(Color.YELLOW)
            }
        }
        if (dayFormat.format(format.parse(data.createdDate)).equals("Saturday", true)) {
            if (data.napOneSa.isNotBlank()) {
                holder.napOneSaturdayM?.text = data.napOneSa
                holder.napOneSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napOneSa.isBlank() && data.napOneSa.isEmpty()) {
                holder.napOneSaturdayM?.text = data.napOneSa
                holder.napOneSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napOneSaturdayM?.text = data.napOneSa
                holder.napOneSaturdayM?.setTextColor(Color.YELLOW)
            }
            if (!data.napTwoSa.isNullOrEmpty() && data.napTwoSa.isNotBlank()) {
                holder.napTwoSaturdayM?.text = data.napTwoSa
                holder.napTwoSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napTwoSa.isBlank() && data.napTwoSa.isEmpty()) {
                holder.napTwoSaturdayM?.text = data.napTwoSa
                holder.napTwoSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napTwoSaturdayM?.text = data.napTwoSa
                holder.napTwoSaturdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.napThreeSa.isNullOrEmpty() && data.napThreeSa.isNotBlank()) {
                holder.napThreeSaturdayM?.text = data.napThreeSa
                holder.napThreeSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.napThreeSa.isBlank() && data.napThreeSa.isEmpty()) {
                holder.napThreeSaturdayM?.text = data.napThreeSa
                holder.napThreeSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.napThreeSaturdayM?.text = data.napThreeSa
                holder.napThreeSaturdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.doseOneSa.isNullOrEmpty() && data.doseOneSa.isNotBlank()) {
                holder.doseOneSaturdayM?.text = data.doseOneSa
                holder.doseOneSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseOneSa.isBlank() && data.doseOneSa.isEmpty()) {
                holder.doseOneSaturdayM?.text = data.doseOneSa
                holder.doseOneSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseOneSaturdayM?.text = data.doseOneSa
                holder.doseOneSaturdayM?.setTextColor(Color.YELLOW)
            }
            if (!data.doseTwoSa.isNullOrEmpty() && data.doseTwoSa.isNotBlank()) {
                holder.doseTwoSaturdayM?.text = data.doseTwoSa
                holder.doseTwoSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.doseTwoSa.isBlank() && data.doseTwoSa.isEmpty()) {
                holder.doseTwoSaturdayM?.text = data.doseTwoSa
                holder.doseTwoSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.doseTwoSaturdayM?.text = data.doseTwoSa
                holder.doseTwoSaturdayM?.setTextColor(Color.YELLOW)
            }

            if (!data.wakeUPSa.isNullOrEmpty() && data.wakeUPSa.isNotBlank()) {
                holder.wakeSaturdayM?.text = data.wakeUPSa
                holder.wakeSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.colorGreen))
            } else if (data.wakeUPSa.isBlank() && data.wakeUPSa.isEmpty()) {
                holder.wakeSaturdayM?.text = data.wakeUPSa
                holder.wakeSaturdayM?.setTextColor(ContextCompat.getColor(context!!, R.color.redText))
            } else {
                holder.wakeSaturdayM?.text = data.wakeUPSa
                holder.wakeSaturdayM?.setTextColor(Color.YELLOW)
            }
        }

    }
}