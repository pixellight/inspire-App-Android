package myapp.net.inspire.plan

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_onboarding_initial.*
import myapp.net.inspire.MainActivity
import myapp.net.inspire.R
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.onboarding.OnboardingViewPagerAdapter

/**
 * Created by QPay on 3/5/2019.
 */
class PlanTutorialActivity:AppCompatActivity() {

    private var myViewPagerAdapter: OnboardingViewPagerAdapter? = null
    private var layouts: IntArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_initial)
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }

        layouts = intArrayOf(R.layout.onboarding_plan_one, R.layout.onboarding_plan_two,
                R.layout.onboarding_plan_four, R.layout.onboarding_plan_five,
                R.layout.onboarding_plan_siz, R.layout.onboarding_plan_seven)
        addBottomDots(0)
        changeStatusBarColor()

        myViewPagerAdapter = OnboardingViewPagerAdapter(this, layouts!!)
        view_pager?.adapter = myViewPagerAdapter
        view_pager?.addOnPageChangeListener(viewPagerPageChangeListener)
        btn_next?.setOnClickListener {
            var current = getItem(+1)
            if (current < layouts!!.size) {
                view_pager?.currentItem = current
            } else {
                launchHomeScreen()
            }
        }
    }





    private fun launchHomeScreen() {
        Repository().setIsFirstTimePlan(this, false)
        var intent=Intent(this, MainActivity::class.java)
        intent.putExtra("fragment","plan")
        startActivity(intent)
        finishAffinity()
    }

    private fun getItem(i: Int): Int {
        return view_pager?.getCurrentItem()!! + i
    }

    internal var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            if (position == layouts!!.size-1) {
                btn_next?.setImageResource(R.drawable.ic_action_done)
                btn_next?.maxWidth = 150
            } else if (position == layouts!!.size) {
            } else {
                btn_next?.setImageResource(R.drawable.ic_action_next_white)
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    private fun addBottomDots(currentPage: Int) {
        var dots = arrayOfNulls<TextView>(layouts!!.size!!)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        layoutDots.removeAllViews()
        for (i in dots!!.indices) {
            dots!![i] = TextView(this)
            dots!![i]!!.text = fromHtml("&#8226;")
            dots!![i]!!.textSize = 35f
            dots!![i]!!.setTextColor(colorsInactive[currentPage])
            layoutDots.addView(dots!![i])
        }

        if (dots!!.size > 0)
            dots!![currentPage]!!.setTextColor(colorsActive[currentPage])

    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = getWindow()
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
        }
    }

    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finishAffinity()
    }
}