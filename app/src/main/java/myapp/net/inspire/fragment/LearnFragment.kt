package myapp.net.inspire.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.learn_fragment.view.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.TheInspireProgram
import myapp.net.inspire.WebActivity
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.quiz.QuizActivity

/**
 * Created by deadlydragger on 10/24/18.
 */

class LearnFragment : Fragment() {
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.learn_fragment, container, false)

        view.xyremDescribe.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("heading", "What is Xyrem?")
            intent.putExtra("url", "file:///android_asset/disclaimer/whatisxyrem.html")
            startActivity(intent)
        }

        view.stepbystepDosing.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("heading", "Step-by-Step Dosing")
            intent.putExtra("url", "file:///android_asset/HowToPrepareDoses/index.html")
            startActivity(intent)
        }


        view.tipsForTakingXYREM.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("heading", "Tips for Taking XYREM")
            intent.putExtra("url", "file:///android_asset/xyrem-tips-html/tipsforxyrem.html")
            startActivity(intent)
        }


        view.treatmentWithXYREM.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("heading", "Treatment with XYREM")
            intent.putExtra("url", "file:///android_asset/treatment-xyrem-html/treatment.html")
            startActivity(intent)
        }


        view.sleepHabitquioz.setOnClickListener {
            val intent = Intent(context, QuizActivity::class.java)
            startActivity(intent)
        }

        view.whatIsInspire.setOnClickListener {
            val intent = Intent(context, TheInspireProgram::class.java)
            startActivity(intent)
        }


        return view
    }

    override fun onResume() {
        if (Repository().getIsNotificationTriggred(activity)!!) {
            startActivity(Intent(activity!!,MainActivity::class.java))
            activity!!.finishAffinity()
        }
        super.onResume()
    }

}
