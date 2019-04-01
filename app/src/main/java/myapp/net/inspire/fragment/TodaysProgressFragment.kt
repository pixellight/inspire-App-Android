package myapp.net.inspire.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import myapp.net.inspire.R
import myapp.net.inspire.data.entity.Cataplexy
import myapp.net.inspire.data.entity.CataplexyRepository
import myapp.net.inspire.data.entity.EdsSeverity
import myapp.net.inspire.data.repository.EdsSeverityRepository
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.progress.ProgressActivity
import myapp.net.inspire.utils.DateTimeUtils
import myapp.net.inspire.utils.PlanEnum

/**
 * Created by deadlydragger on 10/27/18.
 */
class TodaysProgressFragment : Fragment() {
  private var quesEight: Int? = -1
  private var quesOne: Int? = -1
  private var quesTwo: Int? = -1
  private var quesThree: Int? = -1
  private var quesFour: Int? = -1
  private var quesFive: Int? = -1
  private var quesSix: Int? = -1
  private var quesSeven: Int? = -1
  private var quesCataplexy: Int? = -1
  private var sum: Int? = 0
  private var mEdsSeverityRepository: EdsSeverityRepository? = null
  private var mCataplexyRepository: CataplexyRepository? = null
  private var noPlanLayout: FrameLayout? = null
  private var mainLayoutProgress: LinearLayout? = null
  private var doneBttnProgress: ImageButton? = null
  private var cataplexyLayoutBottom: LinearLayout? = null
  private var quesOne_zero: ImageView? = null
  private var quesOne_one: ImageView? = null
  private var quesOne_two: ImageView? = null
  private var quesOne_three: ImageView? = null
  private var quesTwo_zero: ImageView? = null
  private var quesTwo_one: ImageView? = null
  private var quesTwo_two: ImageView? = null
  private var quesTwo_three: ImageView? = null
  private var quesThree_zero: ImageView? = null
  private var quesThree_one: ImageView? = null
  private var quesThree_two: ImageView? = null
  private var quesThree_three: ImageView? = null
  private var quesFour_zero: ImageView? = null
  private var quesFour_one: ImageView? = null
  private var quesFour_two: ImageView? = null
  private var quesFour_three: ImageView? = null
  private var quesFive_zero: ImageView? = null
  private var quesFive_one: ImageView? = null
  private var quesFive_two: ImageView? = null
  private var quesFive_three: ImageView? = null
  private var quesSix_zero: ImageView? = null
  private var quesSix_one: ImageView? = null
  private var quesSix_two: ImageView? = null
  private var quesSix_three: ImageView? = null
  private var quesSeven_zero: ImageView? = null
  private var quesSeven_one: ImageView? = null
  private var quesSeven_two: ImageView? = null
  private var quesSeven_three: ImageView? = null
  private var quesEight_zero: ImageView? = null
  private var quesEight_one: ImageView? = null
  private var quesEight_two: ImageView? = null
  private var quesEight_three: ImageView? = null
  private var totalTextView: TextView? = null
  private var quesCataplexy_zero: ImageView? = null
  private var quesCataplexy_one: ImageView? = null
  private var quesCataplexy_two: ImageView? = null
  private var quesCataplexy_three: ImageView? = null
  
  
  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    noPlanLayout = view!!.findViewById(R.id.noPlanLayout)
    mainLayoutProgress = view!!.findViewById(R.id.mainLayoutProgress)
    doneBttnProgress = view!!.findViewById(R.id.doneBttnProgress)
    cataplexyLayoutBottom = view!!.findViewById(R.id.cataplexyLayoutBottom)
    quesOne_zero = view!!.findViewById(R.id.quesOne_zero)
    quesOne_one = view!!.findViewById(R.id.quesOne_one)
    quesOne_two = view!!.findViewById(R.id.quesOne_two)
    quesOne_three = view!!.findViewById(R.id.quesOne_three)
    quesTwo_zero = view!!.findViewById(R.id.quesTwo_zero)
    quesTwo_one = view!!.findViewById(R.id.quesTwo_one)
    quesTwo_two = view!!.findViewById(R.id.quesTwo_two)
    quesTwo_three = view!!.findViewById(R.id.quesTwo_three)
    quesThree_zero = view!!.findViewById(R.id.quesThree_zero)
    quesThree_one = view!!.findViewById(R.id.quesThree_one)
    quesThree_two = view!!.findViewById(R.id.quesThree_two)
    quesThree_three = view!!.findViewById(R.id.quesThree_three)
    quesFour_zero = view!!.findViewById(R.id.quesFour_zero)
    quesFour_one = view!!.findViewById(R.id.quesFour_one)
    quesFour_two = view!!.findViewById(R.id.quesFour_two)
    quesFour_three = view!!.findViewById(R.id.quesFour_three)
    quesFive_zero = view!!.findViewById(R.id.quesFive_zero)
    quesFive_one = view!!.findViewById(R.id.quesFive_one)
    quesFive_two = view!!.findViewById(R.id.quesFive_two)
    quesFive_three = view!!.findViewById(R.id.quesFive_three)
    quesSix_zero = view!!.findViewById(R.id.quesSix_zero)
    quesSix_one = view!!.findViewById(R.id.quesSix_one)
    quesSix_two = view!!.findViewById(R.id.quesSix_two)
    quesSix_three = view!!.findViewById(R.id.quesSix_three)
    quesSeven_zero = view!!.findViewById(R.id.quesSeven_zero)
    quesSeven_one = view!!.findViewById(R.id.quesSeven_one)
    quesSeven_two = view!!.findViewById(R.id.quesSeven_two)
    quesSeven_three = view!!.findViewById(R.id.quesSeven_three)
    quesEight_zero = view!!.findViewById(R.id.quesEight_zero)
    quesEight_one = view!!.findViewById(R.id.quesEight_one)
    quesEight_two = view!!.findViewById(R.id.quesEight_two)
    quesEight_three = view!!.findViewById(R.id.quesEight_three)
    totalTextView = view!!.findViewById(R.id.totalTextView)
    quesCataplexy_zero = view!!.findViewById(R.id.quesCataplexy_zero)
    quesCataplexy_one = view!!.findViewById(R.id.quesCataplexy_one)
    quesCataplexy_two = view!!.findViewById(R.id.quesCataplexy_two)
    quesCataplexy_three = view!!.findViewById(R.id.quesCataplexy_three)
    
    if (Repository().getIsPlanSetup(activity) == PlanEnum.NOPLAN.ordinal) {
      noPlanLayout?.visibility = View.VISIBLE
      mainLayoutProgress?.visibility = View.GONE
      var toolbar = (activity as ProgressActivity).findViewById<Toolbar>(R.id.toolbar)
      toolbar.visibility = View.GONE
      doneBttnProgress?.setOnClickListener {
        (activity as ProgressActivity).onBackPressed()
      }
    } else {
      mainLayoutProgress?.visibility = View.VISIBLE
      noPlanLayout?.visibility = View.GONE
    }
    
    val edsSeverity = view!!.findViewById<TextView>(R.id.edsSeverity)
    val cataplexyFrequency = view!!.findViewById<TextView>(R.id.cataplexyFrequency)
    val edsLayout = view!!.findViewById<LinearLayout>(R.id.edsLayout)
    val edsLayoutBottom = view!!.findViewById<LinearLayout>(R.id.edsLayoutBottom)
    val cataplexyLayout = view!!.findViewById<LinearLayout>(R.id.cataplexyLayout)
    cataplexyLayoutBottom!!.visibility = View.GONE
    cataplexyLayout!!.visibility = View.GONE
    edsSeverityDataSet()
    edsSeveritySum()
    
    edsSeverity.setOnClickListener {
      edsLayout.visibility = View.VISIBLE
      edsLayoutBottom.visibility = View.VISIBLE
      cataplexyLayout.visibility = View.GONE
      cataplexyLayoutBottom!!.visibility = View.GONE
      
      edsSeverity.setBackgroundColor(resources.getColor(R.color.planBackground))
      edsSeverity.setTextColor(resources.getColor(R.color.white))
      
      cataplexyFrequency.setBackgroundColor(resources.getColor(R.color.transparent))
      cataplexyFrequency.setTextColor(resources.getColor(R.color.black))
      edsSeveritySum()
    }
    cataplexyFrequency.setOnClickListener {
      edsLayout.visibility = View.GONE
      edsLayoutBottom.visibility = View.GONE
      cataplexyLayout.visibility = View.VISIBLE
      cataplexyLayoutBottom!!.visibility = View.VISIBLE

//            cataplexyFrequency.setBackgroundColor(Color.parseColor("#ce7d05"))
      cataplexyFrequency.setBackgroundColor(resources.getColor(R.color.planBackground))
      cataplexyFrequency.setTextColor(resources.getColor(R.color.white))
      
      edsSeverity.setBackgroundColor(resources.getColor(R.color.transparent))
      edsSeverity.setTextColor(resources.getColor(R.color.black))
      sumCataplexyLayout()
      
    }
    
  }
  
  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.today_progress_fragment, container, false)
  }
  
  
  private fun edsSeveritySum() {
    Repository().setIsInEDSSeverity(activity, true)
    //question one start
    quesOne_zero?.setOnClickListener {
      quesOne_zero?.setImageResource(R.drawable.zero_on)
      quesOne_one?.setImageResource(R.drawable.one_off)
      quesOne_two?.setImageResource(R.drawable.two_off)
      quesOne_three?.setImageResource(R.drawable.three_off)
      quesOne = 0
      sumEdsServerity()
      
    }
    
    quesOne_one?.setOnClickListener {
      quesOne_one?.setImageResource(R.drawable.one_on)
      quesOne_two?.setImageResource(R.drawable.two_off)
      quesOne_three?.setImageResource(R.drawable.three_off)
      quesOne_zero?.setImageResource(R.drawable.zero_off)
      quesOne = 1
      sumEdsServerity()
    }
    
    quesOne_two?.setOnClickListener {
      quesOne_two?.setImageResource(R.drawable.two_on)
      quesOne_three?.setImageResource(R.drawable.three_off)
      quesOne_one?.setImageResource(R.drawable.one_off)
      quesOne_zero?.setImageResource(R.drawable.zero_off)
      quesOne = 2
      sumEdsServerity()
    }
    
    quesOne_three?.setOnClickListener {
      quesOne_three?.setImageResource(R.drawable.three_on)
      quesOne_two?.setImageResource(R.drawable.two_off)
      quesOne_one?.setImageResource(R.drawable.one_off)
      quesOne_zero?.setImageResource(R.drawable.zero_off)
      quesOne = 3
      sumEdsServerity()
    }
    //end of question one
    
    
    //start of question two
    
    quesTwo_zero?.setOnClickListener {
      quesTwo_zero?.setImageResource(R.drawable.zero_on)
      quesTwo_one?.setImageResource(R.drawable.one_off)
      quesTwo_two?.setImageResource(R.drawable.two_off)
      quesTwo_three?.setImageResource(R.drawable.three_off)
      quesTwo = 0
      sumEdsServerity()
    }
    
    quesTwo_one?.setOnClickListener {
      quesTwo_one?.setImageResource(R.drawable.one_on)
      quesTwo_two?.setImageResource(R.drawable.two_off)
      quesTwo_three?.setImageResource(R.drawable.three_off)
      quesTwo_zero?.setImageResource(R.drawable.zero_off)
      quesTwo = 1
      sumEdsServerity()
    }
    
    quesTwo_two?.setOnClickListener {
      quesTwo_two?.setImageResource(R.drawable.two_on)
      quesTwo_three?.setImageResource(R.drawable.three_off)
      quesTwo_one?.setImageResource(R.drawable.one_off)
      quesTwo_zero?.setImageResource(R.drawable.zero_off)
      quesTwo = 2
      sumEdsServerity()
    }
    
    quesTwo_three?.setOnClickListener {
      quesTwo_three?.setImageResource(R.drawable.three_on)
      quesTwo_two?.setImageResource(R.drawable.two_off)
      quesTwo_one?.setImageResource(R.drawable.one_off)
      quesTwo_zero?.setImageResource(R.drawable.zero_off)
      quesTwo = 3
      sumEdsServerity()
    }
    
    //end of question two
    
    //start of question three
    
    quesThree_zero?.setOnClickListener {
      quesThree_zero?.setImageResource(R.drawable.zero_on)
      quesThree_one?.setImageResource(R.drawable.one_off)
      quesThree_two?.setImageResource(R.drawable.two_off)
      quesThree_three?.setImageResource(R.drawable.three_off)
      quesThree = 0
      sumEdsServerity()
    }
    
    quesThree_one?.setOnClickListener {
      quesThree_one?.setImageResource(R.drawable.one_on)
      quesThree_two?.setImageResource(R.drawable.two_off)
      quesThree_three?.setImageResource(R.drawable.three_off)
      quesThree_zero?.setImageResource(R.drawable.zero_off)
      quesThree = 1
      sumEdsServerity()
    }
    
    quesThree_two?.setOnClickListener {
      quesThree_two?.setImageResource(R.drawable.two_on)
      quesThree_three?.setImageResource(R.drawable.three_off)
      quesThree_one?.setImageResource(R.drawable.one_off)
      quesThree_zero?.setImageResource(R.drawable.zero_off)
      quesThree = 2
      sumEdsServerity()
    }
    
    quesThree_three?.setOnClickListener {
      quesThree_three?.setImageResource(R.drawable.three_on)
      quesThree_two?.setImageResource(R.drawable.two_off)
      quesThree_one?.setImageResource(R.drawable.one_off)
      quesThree_zero?.setImageResource(R.drawable.zero_off)
      quesThree = 3
      sumEdsServerity()
    }
    
    //end of question three
    
    //start of question four
    
    quesFour_zero?.setOnClickListener {
      quesFour_zero?.setImageResource(R.drawable.zero_on)
      quesFour_one?.setImageResource(R.drawable.one_off)
      quesFour_two?.setImageResource(R.drawable.two_off)
      quesFour_three?.setImageResource(R.drawable.three_off)
      quesFour = 0
      sumEdsServerity()
    }
    
    quesFour_one?.setOnClickListener {
      quesFour_one?.setImageResource(R.drawable.one_on)
      quesFour_two?.setImageResource(R.drawable.two_off)
      quesFour_three?.setImageResource(R.drawable.three_off)
      quesFour_zero?.setImageResource(R.drawable.zero_off)
      quesFour = 1
      sumEdsServerity()
    }
    
    quesFour_two?.setOnClickListener {
      quesFour_two?.setImageResource(R.drawable.two_on)
      quesFour_three?.setImageResource(R.drawable.three_off)
      quesFour_one?.setImageResource(R.drawable.one_off)
      quesFour_zero?.setImageResource(R.drawable.zero_off)
      quesFour = 2
      sumEdsServerity()
    }
    
    quesFour_three?.setOnClickListener {
      quesFour_three?.setImageResource(R.drawable.three_on)
      quesFour_two?.setImageResource(R.drawable.two_off)
      quesFour_one?.setImageResource(R.drawable.one_off)
      quesFour_zero?.setImageResource(R.drawable.zero_off)
      quesFour = 3
      sumEdsServerity()
    }
    
    //end of question four
    
    
    //start of question five
    
    quesFive_zero?.setOnClickListener {
      quesFive_zero?.setImageResource(R.drawable.zero_on)
      quesFive_one?.setImageResource(R.drawable.one_off)
      quesFive_two?.setImageResource(R.drawable.two_off)
      quesFive_three?.setImageResource(R.drawable.three_off)
      quesFive = 0
      sumEdsServerity()
    }
    
    quesFive_one?.setOnClickListener {
      quesFive_one?.setImageResource(R.drawable.one_on)
      quesFive_two?.setImageResource(R.drawable.two_off)
      quesFive_three?.setImageResource(R.drawable.three_off)
      quesFive_zero?.setImageResource(R.drawable.zero_off)
      quesFive = 1
      sumEdsServerity()
    }
    
    quesFive_two?.setOnClickListener {
      quesFive_two?.setImageResource(R.drawable.two_on)
      quesFive_three?.setImageResource(R.drawable.three_off)
      quesFive_one?.setImageResource(R.drawable.one_off)
      quesFive_zero?.setImageResource(R.drawable.zero_off)
      quesFive = 2
      sumEdsServerity()
    }
    
    quesFive_three?.setOnClickListener {
      quesFive_three?.setImageResource(R.drawable.three_on)
      quesFive_two?.setImageResource(R.drawable.two_off)
      quesFive_one?.setImageResource(R.drawable.one_off)
      quesFive_zero?.setImageResource(R.drawable.zero_off)
      quesFive = 3
      sumEdsServerity()
    }
    
    //end of question five
    
    
    //start of question six
    
    quesSix_zero?.setOnClickListener {
      quesSix_zero?.setImageResource(R.drawable.zero_on)
      quesSix_one?.setImageResource(R.drawable.one_off)
      quesSix_two?.setImageResource(R.drawable.two_off)
      quesSix_three?.setImageResource(R.drawable.three_off)
      quesSix = 0
      sumEdsServerity()
    }
    
    quesSix_one?.setOnClickListener {
      quesSix_one?.setImageResource(R.drawable.one_on)
      quesSix_two?.setImageResource(R.drawable.two_off)
      quesSix_three?.setImageResource(R.drawable.three_off)
      quesSix_zero?.setImageResource(R.drawable.zero_off)
      quesSix = 1
      sumEdsServerity()
    }
    
    quesSix_two?.setOnClickListener {
      quesSix_two?.setImageResource(R.drawable.two_on)
      quesSix_three?.setImageResource(R.drawable.three_off)
      quesSix_one?.setImageResource(R.drawable.one_off)
      quesSix_zero?.setImageResource(R.drawable.zero_off)
      quesSix = 2
      sumEdsServerity()
    }
    
    quesSix_three?.setOnClickListener {
      quesSix_three?.setImageResource(R.drawable.three_on)
      quesSix_two?.setImageResource(R.drawable.two_off)
      quesSix_one?.setImageResource(R.drawable.one_off)
      quesSix_zero?.setImageResource(R.drawable.zero_off)
      quesSix = 3
      sumEdsServerity()
    }
    
    //end of question six
    
    
    //start of question seven
    
    quesSeven_zero?.setOnClickListener {
      quesSeven_zero?.setImageResource(R.drawable.zero_on)
      quesSeven_one?.setImageResource(R.drawable.one_off)
      quesSeven_two?.setImageResource(R.drawable.two_off)
      quesSeven_three?.setImageResource(R.drawable.three_off)
      quesSeven = 0
      sumEdsServerity()
    }
    
    quesSeven_one?.setOnClickListener {
      quesSeven_one?.setImageResource(R.drawable.one_on)
      quesSeven_zero?.setImageResource(R.drawable.zero_off)
      quesSeven_two?.setImageResource(R.drawable.two_off)
      quesSeven_three?.setImageResource(R.drawable.three_off)
      quesSeven = 1
      sumEdsServerity()
    }
    
    quesSeven_two?.setOnClickListener {
      quesSeven_two?.setImageResource(R.drawable.two_on)
      quesSeven_one?.setImageResource(R.drawable.one_off)
      quesSeven_zero?.setImageResource(R.drawable.zero_off)
      quesSeven_three?.setImageResource(R.drawable.three_off)
      quesSeven = 2
      sumEdsServerity()
    }
    
    quesSeven_three?.setOnClickListener {
      quesSeven_three?.setImageResource(R.drawable.three_on)
      quesSeven_two?.setImageResource(R.drawable.two_off)
      quesSeven_one?.setImageResource(R.drawable.one_off)
      quesSeven_zero?.setImageResource(R.drawable.zero_off)
      quesSeven = 3
      sumEdsServerity()
    }
    
    //end of question seven
    
    
    //start of question eight
    
    quesEight_zero?.setOnClickListener {
      quesEight_zero?.setImageResource(R.drawable.zero_on)
      quesEight_one?.setImageResource(R.drawable.one_off)
      quesEight_two?.setImageResource(R.drawable.two_off)
      quesEight_three?.setImageResource(R.drawable.three_off)
      quesEight = 0
      sumEdsServerity()
    }
    
    quesEight_one?.setOnClickListener {
      quesEight_one?.setImageResource(R.drawable.one_on)
      quesEight_zero?.setImageResource(R.drawable.zero_off)
      quesEight_two?.setImageResource(R.drawable.two_off)
      quesEight_three?.setImageResource(R.drawable.three_off)
      quesEight = 1
      sumEdsServerity()
    }
    
    quesEight_two?.setOnClickListener {
      quesEight_two?.setImageResource(R.drawable.two_on)
      quesEight_one?.setImageResource(R.drawable.one_off)
      quesEight_zero?.setImageResource(R.drawable.zero_off)
      quesEight_three?.setImageResource(R.drawable.three_off)
      quesEight = 2
      sumEdsServerity()
    }
    
    quesEight_three?.setOnClickListener {
      quesEight_three?.setImageResource(R.drawable.three_on)
      quesEight_two?.setImageResource(R.drawable.two_off)
      quesEight_one?.setImageResource(R.drawable.one_off)
      quesEight_zero?.setImageResource(R.drawable.zero_off)
      quesEight = 3
      sumEdsServerity()
    }
    
    //end of question eight
    
  }
  
  private fun sumEdsServerity() {
    
    if (quesOne!! >= 0 && quesTwo!! >= 0 && quesThree!! >= 0 && quesFour!! >= 0 && quesFive!! >= 0
        && quesSix!! >= 0 && quesSeven!! >= 0 && quesEight!! >= 0) {
      mEdsSeverityRepository = EdsSeverityRepository(activity)
      sum = quesOne!! + quesTwo!! + quesThree!! + quesFour!! + quesFive!! + quesSix!! + quesSeven!! + quesEight!!
      totalTextView?.setText("YOUR SCORE " + sum)
      totalTextView?.setPadding(4, 4, 4, 4)
      totalTextView?.setTextColor(Color.WHITE)
      totalTextView?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreen))
      
      try {
        println("EDS " + Repository().getIsProgress(activity))
        if (Repository().getIsProgress(activity!!) != null && Repository().getIsProgress(activity)!!.equals(DateTimeUtils.getCurrentDateTimeforEdsSeverity().toString())) {
          var edsId = mEdsSeverityRepository!!.getTodayEdsSeverity(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
          
          var edsSeverity = EdsSeverity(id = edsId, questionOne = quesOne!!, questionTwo = quesTwo!!,
              questionThree = quesThree!!, questionFour = quesFour!!, questionFive = quesFive!!, questionSix = quesSix!!,
              questionSeven = quesSeven!!, questionEight = quesEight!!, sum = sum!!, cataplexy = quesCataplexy!!, createdDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity())
          mEdsSeverityRepository!!.updateEdsSeverity(edsSeverity)
        } else {
          Repository().setIsProgress(activity, DateTimeUtils.getCurrentDateTimeforEdsSeverity()!!.toString())
          var edsSeverity = EdsSeverity(id = null, questionOne = quesOne!!, questionTwo = quesTwo!!,
              questionThree = quesThree!!, questionFour = quesFour!!, questionFive = quesFive!!, questionSix = quesSix!!,
              questionSeven = quesSeven!!, questionEight = quesEight!!, sum = sum!!, cataplexy = quesCataplexy!!, createdDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity())
          mEdsSeverityRepository!!.insertEdsSeverity(edsSeverity)
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
      
    } else {
    
    
    }
  }
  
  private fun sumCataplexyLayout() {
    Repository().setIsInEDSSeverity(activity, false)
    quesCataplexy_zero?.setOnClickListener {
      quesCataplexy_zero?.setImageResource(R.drawable.zero_on)
      quesCataplexy_one?.setImageResource(R.drawable.one_off)
      quesCataplexy_two?.setImageResource(R.drawable.two_off)
      quesCataplexy_three?.setImageResource(R.drawable.three_off)
      quesCataplexy = 0
      cataplexySum()
    }
    
    quesCataplexy_one?.setOnClickListener {
      quesCataplexy_zero?.setImageResource(R.drawable.zero_off)
      quesCataplexy_one?.setImageResource(R.drawable.one_on)
      quesCataplexy_two?.setImageResource(R.drawable.two_off)
      quesCataplexy_three?.setImageResource(R.drawable.three_off)
      quesCataplexy = 1
      cataplexySum()
    }
    
    quesCataplexy_two?.setOnClickListener {
      quesCataplexy_zero?.setImageResource(R.drawable.zero_off)
      quesCataplexy_one?.setImageResource(R.drawable.one_off)
      quesCataplexy_two?.setImageResource(R.drawable.two_on)
      quesCataplexy_three?.setImageResource(R.drawable.three_off)
      quesCataplexy = 2
      cataplexySum()
    }
    
    quesCataplexy_three?.setOnClickListener {
      quesCataplexy_zero?.setImageResource(R.drawable.zero_off)
      quesCataplexy_one?.setImageResource(R.drawable.one_off)
      quesCataplexy_two?.setImageResource(R.drawable.two_off)
      quesCataplexy_three?.setImageResource(R.drawable.three_on)
      quesCataplexy = 3
      cataplexySum()
    }
    
  }
  
  private fun cataplexySum() {
    if (quesCataplexy!! >= 0) {
      mCataplexyRepository = CataplexyRepository(activity)
      
      try {
//                if (Repository().getIsProgressCataplexy(activity!!) != null && Repository().getIsProgressCataplexy(activity)!!.equals(DateTimeUtils.getCurrentDateTimeforEdsSeverity())) {
//                    var id = mCataplexyRepository!!.getTodayCataplexy(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
//                    var cataplexy = Cataplexy(id = id, questionOne = quesCataplexy!!, createdDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity())
//                    mCataplexyRepository!!.updateCataplexy(cataplexy)
//                } else {
//                    var cataplexy = Cataplexy(id = null, questionOne = quesCataplexy!!, createdDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity())
//                    mCataplexyRepository!!.insertCataplexy(cataplexy)
//                    Repository().setIsProgressCataplexy(activity, DateTimeUtils.getCurrentDateTimeforEdsSeverity().toString())
//
//                }
        
        if (Repository().getIsProgress(activity!!) != null && Repository().getIsProgress(activity)!!.equals(DateTimeUtils.getCurrentDateTimeforEdsSeverity().toString())) {
          var edsId = mEdsSeverityRepository!!.getTodayEdsSeverity(DateTimeUtils.getCurrentDateTimeforEdsSeverity()).id
          
          var edsSeverity = EdsSeverity(id = edsId, questionOne = quesOne!!, questionTwo = quesTwo!!,
              questionThree = quesThree!!, questionFour = quesFour!!, questionFive = quesFive!!, questionSix = quesSix!!,
              questionSeven = quesSeven!!, questionEight = quesEight!!, sum = sum!!, cataplexy = quesCataplexy!!, createdDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity())
          mEdsSeverityRepository!!.updateEdsSeverity(edsSeverity)
        } else {
          Repository().setIsProgress(activity, DateTimeUtils.getCurrentDateTimeforEdsSeverity()!!.toString())
          var edsSeverity = EdsSeverity(id = null, questionOne = quesOne!!, questionTwo = quesTwo!!,
              questionThree = quesThree!!, questionFour = quesFour!!, questionFive = quesFive!!, questionSix = quesSix!!,
              questionSeven = quesSeven!!, questionEight = quesEight!!, sum = sum!!, cataplexy = quesCataplexy!!, createdDate = DateTimeUtils.getCurrentDateTimeforEdsSeverity())
          mEdsSeverityRepository!!.insertEdsSeverity(edsSeverity)
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
      
      
    }
    
  }
  
  
  private fun edsSeverityDataSet() {
    try {
      mEdsSeverityRepository = EdsSeverityRepository(activity)
      mCataplexyRepository = CataplexyRepository(activity)
      var edsSeverity: EdsSeverity? = null
      var cataplexy: Cataplexy? = null
      cataplexy = mCataplexyRepository!!.getTodayCataplexy(DateTimeUtils.getCurrentDateTimeforEdsSeverity())
      edsSeverity = mEdsSeverityRepository!!.getTodayEdsSeverity(DateTimeUtils.getCurrentDateTimeforEdsSeverity())
      if (edsSeverity != null) {
        //for question one
        quesOne = edsSeverity.questionOne
        if (quesOne == 0) {
          quesOne_zero?.setImageResource(R.drawable.zero_on)
        } else if (quesOne == 1) {
          quesOne_one?.setImageResource(R.drawable.one_on)
        } else if (quesOne == 2) {
          quesOne_two?.setImageResource(R.drawable.two_on)
        } else {
          quesOne_three?.setImageResource(R.drawable.three_on)
        }
        
        //end question
        
        //for question two
        quesTwo = edsSeverity.questionTwo
        if (quesTwo == 0) {
          quesTwo_zero?.setImageResource(R.drawable.zero_on)
        } else if (quesTwo == 1) {
          quesTwo_one?.setImageResource(R.drawable.one_on)
        } else if (quesTwo == 2) {
          quesTwo_two?.setImageResource(R.drawable.two_on)
        } else {
          quesTwo_three?.setImageResource(R.drawable.three_on)
        }
        
        //end question
        
        
        //for question three
        quesThree = edsSeverity.questionThree
        if (quesThree == 0) {
          quesThree_zero?.setImageResource(R.drawable.zero_on)
        } else if (quesThree == 1) {
          quesThree_one?.setImageResource(R.drawable.one_on)
        } else if (quesThree == 2) {
          quesThree_two?.setImageResource(R.drawable.two_on)
        } else {
          quesThree_three?.setImageResource(R.drawable.three_on)
        }
        
        //end question
        
        
        //for question four
        quesFour = edsSeverity.questionFour
        if (quesFour == 0) {
          quesFour_zero?.setImageResource(R.drawable.zero_on)
        } else if (quesFour == 1) {
          quesFour_one?.setImageResource(R.drawable.one_on)
        } else if (quesFour == 2) {
          quesFour_two?.setImageResource(R.drawable.two_on)
        } else {
          quesFour_three?.setImageResource(R.drawable.three_on)
        }
        
        //end question
        
        
        //for question five
        quesFive = edsSeverity.questionFive
        if (quesFive == 0) {
          quesFive_zero?.setImageResource(R.drawable.zero_on)
        } else if (quesFive == 1) {
          quesFive_one?.setImageResource(R.drawable.one_on)
        } else if (quesFive == 2) {
          quesFive_two?.setImageResource(R.drawable.two_on)
        } else {
          quesFive_three?.setImageResource(R.drawable.three_on)
        }
        
        //end question
        
        //for question six
        quesSix = edsSeverity.questionSix
        if (quesSix == 0) {
          quesSix_zero?.setImageResource(R.drawable.zero_on)
        } else if (quesSix == 1) {
          quesSix_one?.setImageResource(R.drawable.one_on)
        } else if (quesSix == 2) {
          quesSix_two?.setImageResource(R.drawable.two_on)
        } else {
          quesSix_three?.setImageResource(R.drawable.three_on)
        }
        
        //end question
        
        
        //for question seven
        quesSeven = edsSeverity.questionSeven
        if (quesSeven == 0) {
          quesSeven_zero?.setImageResource(R.drawable.zero_on)
        } else if (quesSeven == 1) {
          quesSeven_one?.setImageResource(R.drawable.one_on)
        } else if (quesSeven == 2) {
          quesSeven_two?.setImageResource(R.drawable.two_on)
        } else {
          quesSeven_three?.setImageResource(R.drawable.three_on)
        }
        
        //end question
        
        
        //for question eight
        quesEight = edsSeverity.questionEight
        if (quesEight == 0) {
          quesEight_zero?.setImageResource(R.drawable.zero_on)
        } else if (quesEight == 1) {
          quesEight_one?.setImageResource(R.drawable.one_on)
        } else if (quesEight == 2) {
          quesEight_two?.setImageResource(R.drawable.two_on)
        } else {
          quesEight_three?.setImageResource(R.drawable.three_on)
        }
        
        //end question
        
        
        //cataplexy question
        
        quesCataplexy = edsSeverity.cataplexy
        if (quesCataplexy == 0) {
          quesCataplexy_zero?.setImageResource(R.drawable.zero_on)
        } else if (quesCataplexy == 1) {
          quesCataplexy_one?.setImageResource(R.drawable.one_on)
        } else if (quesCataplexy == 2) {
          quesCataplexy_two?.setImageResource(R.drawable.two_on)
        } else {
          quesCataplexy_three?.setImageResource(R.drawable.three_on)
        }
        
        totalTextView?.setText("YOUR SCORE " + edsSeverity.sum)
        totalTextView?.setTextColor(Color.WHITE)
        totalTextView?.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorGreen))
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
    
    
  }
  
  
}