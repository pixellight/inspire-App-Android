package myapp.net.inspire.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import kotlinx.android.synthetic.main.activity_disclaimer.*
import myapp.net.inspire.R
import myapp.net.inspire.utils.EulaWebView


/**
 * Created by Alucard on 3/2/2019.
 */
class DisclaimerActivity : AppCompatActivity(), EulaWebView.OnBottomReachedListener {
    override fun onBottomReached(v: View) {
        println("Bottom reached")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disclaimer)
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }
        headingDisclarimer.text = "DISCLAIMER & PRIVACY"
//        webViewDisclaimer.loadUrl("file:///android_asset/disclaimer/disclaimer_02.html")
//        webViewDisclaimer.visibility = View.VISIBLE
//        webViewDisclaimer?.setOnBottomReachedListener(this, 1)
        val disclaimerContent = SpannableString(getString(R.string.disclaimer))
        disclaimerContent.setSpan(UnderlineSpan(), 0, disclaimerContent.length, 0)
        disclaimerTitle.text = disclaimerContent

        val privacyContent = SpannableString(getString(R.string.privacy_first))
        privacyContent.setSpan(UnderlineSpan(), 0, privacyContent.length, 0)
        privacyTitle.text = privacyContent

        val securityContent = SpannableString(getString(R.string.security))
        securityContent.setSpan(UnderlineSpan(), 0, securityContent.length, 0)
        securityTitle.text = securityContent

        val notificationContent = SpannableString(getString(R.string.notification))
        notificationContent.setSpan(UnderlineSpan(), 0, notificationContent.length, 0)
        notificationTitle.text = notificationContent




        iAgreeBttn?.setOnClickListener {

            startActivity(Intent(this, OnboardingActivity::class.java))

        }
    }
}