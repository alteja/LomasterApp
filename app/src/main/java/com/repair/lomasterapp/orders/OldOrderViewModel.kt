package com.repair.lomasterapp.orders

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.repair.lomasterapp.entity.OldOrderObj
import com.repair.lomasterapp.entity.SettingsServices
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


class OldOrderViewModel(application: Application):ViewModel() {

    private val repository = OldOrderRepositoryImpl

    private val context = application


    private val _orderData = MutableLiveData<OldOrderObj>()
    val orderData: LiveData<OldOrderObj>
        get() = _orderData

    private val _settingsServicesData = MutableLiveData<ArrayList<SettingsServices>>()
    val settingsServicesData: LiveData<ArrayList<SettingsServices>>
        get() = _settingsServicesData

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }


    suspend fun getOrderDataFromServer(idOrder: Int, userPhone: String): OldOrderObj {

        println(" println(\"GETTING OLD ORDER\") OLD ORDER")

        val oldOrder = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).async {
            return@async repository.getOldOrderDataFromServer(idOrder, userPhone)
        }.await()

        println("SETTING OLD ORDER TO LIVE DATA")

        return oldOrder

    }

    fun setOrderDBToLiveData(order: OldOrderObj){
        _orderData.value = order
        _settingsServicesData.value = getListServicesSettings()
    }

    fun getListServicesSettings(): ArrayList<SettingsServices>{

       return concatenateSettingsServices()

    }

    private fun concatenateSettingsServices(): ArrayList<SettingsServices> {

        val fooSet: MutableSet<SettingsServices> = LinkedHashSet(_orderData.value?.operations)
        _orderData.value?.parts?.let { fooSet.addAll(it) }

        return  ArrayList(fooSet)
    }

    fun getGeneralPrice(): Int{

        var summGeneral = 0

        with(_orderData.value){

            val partsArray = this?.parts as ArrayList<SettingsServices>

            summGeneral = partsArray.sumOf { it.price!!.toInt() * it.amount!!.toInt()}

            val operationsArray = this.operations as ArrayList<SettingsServices>

            summGeneral += operationsArray.sumOf { it.price!!.toInt() * it.amount!!.toInt()}
        }

        return summGeneral
    }

}

