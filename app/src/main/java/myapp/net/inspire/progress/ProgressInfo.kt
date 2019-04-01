package myapp.net.inspire.progress

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.progress_info_fragment.*
import myapp.net.inspire.R
import myapp.net.inspire.WebActivity
import myapp.net.inspire.data.repository.Repository

/**
 * Created by deadlydragger on 10/28/18.
 */
class ProgressInfo : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.progress_info_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if(Repository().getIsInEDSSeverity(activity)!!) {
              infoWebView?.loadUrl("file:///android_asset/progressInfoHtmlFiles/progress_eds_01.html")
            }else{
              infoWebView?.loadUrl("file:///android_asset/progressInfoHtmlFiles/progress_cataplexy_01.html")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}