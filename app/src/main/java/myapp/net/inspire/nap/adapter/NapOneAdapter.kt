package myapp.net.inspire.nap.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import myapp.net.inspire.R

/**
 * Created by QPay on 2/10/2019.
 */
class NapOneAdapter(val minutes: ArrayList<Int>) : RecyclerView.Adapter<NapOneAdapter.NapOneViewHolder>() {
    override fun getItemCount(): Int {
        return minutes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NapOneViewHolder {
        return NapOneViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_minute, parent, false))
    }

    override fun onBindViewHolder(holder: NapOneViewHolder?, position: Int) {
        try {
            val min = minutes.get(position)
            holder!!.minTextview?.text = min.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    class NapOneViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val minTextview = itemView?.findViewById<TextView>(R.id.minutesTextview)
    }
}