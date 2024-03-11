package com.repair.lomasterapp.orders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.repair.lomasterapp.R

class OrderItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    val tvStatus = view.findViewById<TextView>(R.id.tvStatus)
    val tvDateCreation: TextView = view.findViewById(R.id.tvDateCreation)
    val tvTypeDevice: TextView = view.findViewById(R.id.tvTypeDevice)
    val tvDefect: TextView = view.findViewById(R.id.tvDefect)

}