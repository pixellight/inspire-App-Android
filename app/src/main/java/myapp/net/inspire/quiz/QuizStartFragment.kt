package myapp.net.inspire.quiz

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_quiz_start.*
import myapp.net.inspire.R
import myapp.net.inspire.utils.GeneralUtils
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by Alucard on 3/4/2019.
 */
class QuizStartFragment : Fragment() {

    companion object {
        val TAG = "QuizStartFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.activity_quiz_start, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startQuiz?.setOnClickListener {
            LoadFragment().addFragmentToActivity(activity!!.supportFragmentManager, QuizFragment(), R.id.containerQuzi)
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
                    activity!!.finish()
                    true
                } else false
            }

        })

    }

}