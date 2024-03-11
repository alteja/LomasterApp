package com.repair.lomasterapp.orders

import com.repair.lomasterapp.entity.OrderObj

interface ListOrderInterface {

    suspend fun getOrdersListFromServer(userPhone: String): List<OrderObj>

}