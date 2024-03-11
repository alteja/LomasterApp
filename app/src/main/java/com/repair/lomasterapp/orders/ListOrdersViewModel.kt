package com.repair.lomasterapp.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.repair.lomasterapp.entity.OrderObj
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListOrdersViewModel @Inject constructor() : ViewModel()  {

    private val repository = OrdersListRepositoryImpl

    private val _ordersData = MutableLiveData<List<OrderObj>>()
    val ordersData: LiveData<List<OrderObj>>
        get() = _ordersData

    suspend fun setOrderInformation(userPhone: String) {
        _ordersData.value = repository.getOrdersListFromServer(userPhone)
    }

}