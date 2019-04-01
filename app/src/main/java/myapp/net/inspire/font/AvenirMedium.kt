package myapp.net.inspire.font

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

/**
 * Created by deadlydragger on 10/24/18.
 */
class AvenirMedium(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    init {
        val typeface = Typeface.createFromAsset(getContext().assets, "font/Avenir-Medium.otf")
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB ||
                android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            setTypeface(typeface)
        }
    }
}