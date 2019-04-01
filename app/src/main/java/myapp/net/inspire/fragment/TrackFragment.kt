package myapp.net.inspire.fragment

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.note.NotesActivity
import myapp.net.inspire.progress.ProgressActivity
import myapp.net.inspire.refill.RefillActivity
import myapp.net.inspire.report.ReportActivity

/**
 * Created by deadlydragger on 10/24/18.
 */
class TrackFragment : Fragment() {
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view!!.findViewById<ConstraintLayout>(R.id.progress_menu).setOnClickListener {
            startActivity(Intent(activity, ProgressActivity::class.java))
        }
        view!!.findViewById<ConstraintLayout>(R.id.reports_menu).setOnClickListener {
            startActivity(Intent(activity, ReportActivity::class.java))
        }
        view!!.findViewById<ConstraintLayout>(R.id.notes).setOnClickListener {
            startActivity(Intent(activity, NotesActivity::class.java))
        }
        view!!.findViewById<ConstraintLayout>(R.id.reflils).setOnClickListener {
            startActivity(Intent(activity, RefillActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.track_fragment, container, false)
    }

    override fun onResume() {
        if (Repository().getIsNotificationTriggred(activity)!!) {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }
        super.onResume()
        if (view == null) {
            return
        }
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()

        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return if (event!!.getAction() === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    activity!!.finishAffinity()
                    true
                } else false
            }

        })

    }
}