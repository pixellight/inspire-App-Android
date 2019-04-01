package myapp.net.inspire.more

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.more_fragment.view.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.TutorialsActivity
import myapp.net.inspire.WebActivity
import myapp.net.inspire.data.repository.Repository

/**
 * Created by deadlydragger on 10/24/18.
 */

class MoreFragment : Fragment() {
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view!!)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.more_fragment, container, false)


        return view
    }


    fun setupView(view: View) {
        val tutorial = view.tutorial
        val versionLayout = view.versionLayout

        tutorial.setOnClickListener {
            startActivity(Intent(context, TutorialsActivity::class.java))

        }

        versionLayout.setOnClickListener {
            startActivity(Intent(context, VersionActivity::class.java))
        }

        view.support?.setOnClickListener {
            startActivity(Intent(context, SupportActivity::class.java))
        }
        view.fullPrescribingInformation.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("title", "More")
            intent.putExtra("heading", "Full Prescribing Information")
            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
            startActivity(intent)
        }


        view.privacyMenu.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("title", "More")
            intent.putExtra("heading", "Disclaimer & Privacy")
            intent.putExtra("url", "file:///android_asset/disclaimer/disclaimer_02.html")
            startActivity(intent)
        }

        view.importanceSafteyMenu.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("title", "More")
            intent.putExtra("heading", "Important Safety Information")
            intent.putExtra("url", "file:///android_asset/ISI/isi_learn.htm")
            startActivity(intent)
        }
    }


    override fun onResume() {
        if (Repository().getIsNotificationTriggred(activity)!!) {
            startActivity(Intent(activity!!, MainActivity::class.java))
            activity!!.finishAffinity()
        }
        super.onResume()
    }
}
