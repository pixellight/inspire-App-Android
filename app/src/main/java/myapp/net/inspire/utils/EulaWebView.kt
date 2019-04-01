package myapp.net.inspire.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView

/**
 * Created by QPay on 3/10/2019.
 */
class EulaWebView: WebView {
    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs,0) {
    }

   constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs,defStyle){
    }



    var mOnBottomReachedListener: OnBottomReachedListener? = null
    private var mMinDistance = 0

    /**
     * Set the listener which will be called when the WebView is scrolled to within some
     * margin of the bottom.
     * @param bottomReachedListener
     * @param allowedDifference
     */
    fun setOnBottomReachedListener(bottomReachedListener: OnBottomReachedListener, allowedDifference: Int) {
        mOnBottomReachedListener = bottomReachedListener
        mMinDistance = allowedDifference
    }

    /**
     * Implement this interface if you want to be notified when the WebView has scrolled to the bottom.
     */
    interface OnBottomReachedListener {
        fun onBottomReached(v: View)
    }

    override fun onScrollChanged(newLeft: Int, newTop: Int, oldLeft: Int, oldTop: Int) {
        if (mOnBottomReachedListener != null) {

            if ((newTop + height)-(contentHeight+oldTop) <= mMinDistance)
                mOnBottomReachedListener!!.onBottomReached(this)
        }
        super.onScrollChanged(left, top, oldLeft, oldTop)
    }

}