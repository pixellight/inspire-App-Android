package myapp.net.inspire.refill

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.DatePicker
import kotlinx.android.synthetic.main.fragment_refill_reminder.*
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.Constants
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.NotificationUtils
import java.util.*

/**
 * Created by Alucard on 2/23/2019.
 */
class RefillFragment : Fragment() {

    private var isStart: Boolean? = false
    private var isReminder: Boolean? = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_refill_reminder, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var refillFlag = Repository().getIsRefillReminder(activity!!)
        switchRefill?.isChecked = refillFlag!!
        startedLayout?.setOnClickListener {
            if (isStart!!) {
                isStart = false
                startedDatePicker?.visibility = View.GONE

            } else {
                isStart = true
                startedDatePicker?.visibility = View.VISIBLE

            }

        }


        var startDate = Repository().getStartDateRefill(activity)
        var emptyDate = Repository().getEndDateRefill(activity)
        if (startDate != null) {
            startedTextview?.text = DateTimeUtils.convertDateFormat(startDate)

        }

        if (emptyDate != null) {
            emptyTextview?.text = DateTimeUtils.convertDateFormat(emptyDate)
        }

        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        startedDatePicker?.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), object : DatePicker.OnDateChangedListener {
            override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                startedTextview?.text = DateTimeUtils.getCurrentDayFull(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) + " " + DateTimeUtils.getMonth(monthOfYear) + " " + dayOfMonth
                startedTextview?.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                var date = dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                emptyTextview?.text = DateTimeUtils.add30DaysForRefillEmpty(year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString())
                emptyTextview?.setTextColor(ContextCompat.getColor(activity!!, R.color.blackDim))
                var dateEmpty = emptyTextview?.text.toString()
                Repository().setStartDateRefill(activity!!, date)
                Repository().setEndDateRefill(activity!!, DateTimeUtils.convert30DaysEmpty(dateEmpty))
            }

        })



        switchRefill?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked!!) {
                isReminder = true
                Repository().setIsRefillReminder(activity!!, isChecked!!)
            } else {
                isReminder = false
                Repository().setIsRefillReminder(activity!!, false)


            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }

        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()

        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return if (event!!.getAction() === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    try {
                        if (isReminder!!) {
                            NotificationUtils.setOnTimeNotificationForRefill(Repository().getEndDateRefill(activity)!!, activity!!, Constants.REFILL_NOTIFICATION_ID)
                            (activity as RefillActivity).onBackPressed()
                        } else {
                            (activity as RefillActivity).onBackPressed()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    true
                } else false
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                try {
                    if (isReminder!!) {
                        NotificationUtils.setOnTimeNotificationForRefill(Repository().getEndDateRefill(activity)!!, activity!!, Constants.REFILL_NOTIFICATION_ID)
                        (activity as RefillActivity).onBackPressed()
                    } else {
                        (activity as RefillActivity).onBackPressed()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        return false
    }


}