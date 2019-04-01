package myapp.net.inspire.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by deadlydragger on 10/25/18.
 */
class LoadFragment {
    fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int) {
        val transaction = manager.beginTransaction()
        transaction.replace(frameId, fragment)
        transaction.commit()

    }

    fun addFragmentToActivityNotification(manager: FragmentManager, fragment: Fragment, frameId: Int) {
        val transaction = manager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()

    }

    companion object {
        fun addFragmentToBackStack(manager: FragmentManager, fragment: Fragment, frameId: Int, tag: String) {
            val transaction = manager.beginTransaction()
            transaction.replace(frameId, fragment)
            transaction.addToBackStack(tag)
            transaction.commit()
        }



        fun popupBackStack(manager: FragmentManager, tag: String) {
            manager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }


}