package com.repair.lomasterapp.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import com.repair.lomasterapp.R
import com.repair.lomasterapp.calendarDate.TransformDatesRepository
import com.repair.lomasterapp.entity.OrderObj
import com.repair.lomasterapp.entity.UserPhone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrdersListAdapter: ListAdapter<OrderObj, OrderItemViewHolder>(OrderItemDiffCallback()) {

    var items = emptyList<OrderObj>()

    @Inject
    lateinit var userPhone: UserPhone


    var repositoryDates = TransformDatesRepository

    var repositoryStatus = StatusColorsRepository

    fun setOrdersList(ordersList :  List<OrderObj>?) {
        this.items = ordersList?: emptyList()
    }

    lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {

        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_orders_fragment, parent, false)
        return OrderItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: OrderItemViewHolder, position: Int) {

        var orderItem = getItem(position)

        viewHolder.tvDateCreation.text = repositoryDates.dateToString(orderItem.dateSetting)
        viewHolder.tvStatus.text = orderItem.status
        viewHolder.tvStatus.setBackgroundColor(repositoryStatus.getStatusColor(orderItem.status?:"", context))
        viewHolder.tvDefect.text = orderItem.malfunction
        viewHolder.tvTypeDevice.text = orderItem.kindof_good

        viewHolder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(
                com.repair.lomasterapp.presentation.requests.ListOrdersFragmentDirections.actionListOrdersFragmentToOldOrderFragment()
            )
        }

    }

    override fun getItemCount() = items.size

    companion object{
        const val MAX_POOL_SIZE = 15
    }
}