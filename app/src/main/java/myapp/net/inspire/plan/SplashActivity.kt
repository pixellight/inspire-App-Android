package myapp.net.inspire.plan

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import myapp.net.inspire.MainActivity
import myapp.net.inspire.data.repository.Repository
import myapp.net.inspire.onboarding.DisclaimerActivity


/**
 * Created by QPay on 2/12/2019.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        Fabric.with(this, Crashlytics())
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if(Repository().getIsFirstTimeLaunch(this)!!){
            val mainIntent = Intent(this, DisclaimerActivity::class.java)
            startActivity(mainIntent)
            finish()
        }else{
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }



    }
}