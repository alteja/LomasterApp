package com.repair.lomasterapp.orders

import androidx.recyclerview.widget.DiffUtil
import com.repair.lomasterapp.entity.OrderObj

class OrderItemDiffCallback: DiffUtil.ItemCallback<OrderObj>()  {

    override fun areItemsTheSame(oldItem: OrderObj, newItem: OrderObj): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderObj, newItem: OrderObj): Boolean {
        return oldItem == newItem
    }
}