package myapp.net.inspire

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.content_web.*
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.utils.LoadFragment

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_web)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        var title = findViewById<TextView>(R.id.title)

        title.text = intent.extras.getString("title", "Learn")


        toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        try {
            if (!intent.extras.getString("title", null).isNullOrEmpty() &&
                    intent.extras.getString("title", null).equals("Information")) {
                bottomViewWeb?.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if(intent.extras.getString("heading").equals("Important Safety Information",true) ||
                intent.extras.getString("heading").equals("Full Prescribing Information",true)){
            toolbar.visibility = View.GONE

        }else{
            toolbar.visibility = View.VISIBLE

        }

        if(intent.extras.getString("heading") != "Important Safety Information") {
            heading.text = intent.extras.getString("heading")
        }else{
            heading.visibility = View.GONE
        }
        if (!intent.extras.getString("url").isNullOrEmpty()) {
            webView.loadUrl(intent.extras.getString("url"))
            pdfViewer.visibility = View.GONE
            webView.visibility = View.VISIBLE
        }

        if (!intent.extras.getString("pdf").isNullOrEmpty()) {
            pdfViewer.fromAsset(intent.extras.getString("pdf"))
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    .invalidPageColor(Color.WHITE) // color of page that is invalid and cannot be loaded
                    .load()
            pdfViewer.visibility = View.VISIBLE
            webView.visibility = View.GONE
        }

        fullPrescribeInformationWeb?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Full Prescribing Information")
            intent.putExtra("pdf", "xyrem.en.USPI.pdf")
            startActivity(intent)
        }

        importantSafetyInformationWeb?.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("title", "Information")
            intent.putExtra("heading", "Important Safety Information")
            intent.putExtra("url", "file:///android_asset/ISI/ISI.htm")
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        LoadFragment.popupBackStack(supportFragmentManager, MainActivity.TAG)
        super.onBackPressed()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        if (Repository().getIsNotificationTriggred(this@WebActivity)!!) {
            startActivity(Intent(this@WebActivity!!,MainActivity::class.java))
            finishAffinity()
        }
        super.onResume()
    }

}
