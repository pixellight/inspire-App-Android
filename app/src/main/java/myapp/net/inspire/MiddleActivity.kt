package myapp.net.inspire

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import myapp.net.inspire.fragment.LearnFragment
import myapp.net.inspire.more.MoreFragment
import myapp.net.inspire.fragment.TrackFragment
import myapp.net.inspire.utils.LoadFragment

/**
 * Created by deadlydragger on 10/26/18.
 */
class MiddleActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_middle)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        var title = findViewById<TextView>(R.id.title)
        if ( intent.extras["type"]=="More")
        { title.text="More"
            LoadFragment().addFragmentToActivity(supportFragmentManager, MoreFragment(),R.id.fragmennt)
        }
        if ( intent.extras["type"]=="Learn")
        { title.text="Learn"
            LoadFragment().addFragmentToActivity(supportFragmentManager, LearnFragment(),R.id.fragmennt)
        }
        if ( intent.extras["type"]=="Track")
        { title.text="Track"
            LoadFragment().addFragmentToActivity(supportFragmentManager, TrackFragment(),R.id.fragmennt)
        }


    }
}