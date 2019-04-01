package myapp.net.inspire.application

import android.app.Application
import android.content.Context

/**
 * Created by Alucard on 2/9/2019.
 */
class Application : android.app.Application() {
    init {
        instance = this
    }

    companion object {
        var instance: Application? = null

        val context: Context
            get() = instance!!
    }
}