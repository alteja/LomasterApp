package com.repair.lomasterapp.orders

import android.annotation.SuppressLint
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repair.lomasterapp.LomasterApplication.Companion.appContext
import com.repair.lomasterapp.R
import com.repair.lomasterapp.entity.OrderObj
import com.repair.lomasterapp.entity.UserPhone
import com.repair.lomasterapp.orders.NewOrderRepositoryImpl.getClientInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class NewOrderViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var userPhone: UserPhone

    private val _orderData = MutableLiveData<OrderObj>()
    val orderData: LiveData<OrderObj>
        get() = _orderData


    private val _typeDevice = MutableLiveData<String>()
    val typeDevice: LiveData<String>
        get() = _typeDevice

    private val _serialNumberDevice = MutableLiveData<String>()
    val serialNumberDevice: LiveData<String>
        get() = _serialNumberDevice

    private val _brandDevice = MutableLiveData<String>()
    val brandDevice: LiveData<String>
        get() = _brandDevice

    private val _modelDevice = MutableLiveData<String>()
    val modelDevice: LiveData<String>
        get() = _modelDevice


    private val _passwordDevice = MutableLiveData<String>()
    val passwordDevice: LiveData<String>
        get() = _passwordDevice

    private val _defectDevice = MutableLiveData<String>()
    val defectDevice: LiveData<String>
        get() = _defectDevice


    private val _equipmentDevice = MutableLiveData<String>()
    val equipmentDevice: LiveData<String>
        get() = _equipmentDevice

    private val _isQuiqly = MutableLiveData<Boolean>()
    val isQuiqly: LiveData<Boolean>
        get() = _isQuiqly

    private val _masterCallDate = MutableLiveData<String>()
    val masterCallDate: LiveData<String>
        get() = _masterCallDate

    private val _isNewDevice = MutableLiveData<String>()
    val isNewDevice: LiveData<String>
        get() = _isNewDevice

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val job = SupervisorJob()
    private val coroutineContextIO: CoroutineContext
        get() = Dispatchers.IO + coroutineExceptionHandler + job


    suspend fun setClientInformation() {

        val clientInformation = CoroutineScope(
            coroutineContextIO
        ).async {

            return@async getClientInformation(userPhone.numberPhone)

        }.await()

        val userPhone = clientInformation?.phone

        val newOrder = OrderObj(
            null, clientInformation?.name, userPhone?.get(0),
            clientInformation?.email
        )

        _orderData.postValue(newOrder)

    }

    suspend fun loadTypeDevice() =

        viewModelScope.async {

            return@async NewOrderRepositoryImpl.getListTypeDevices()

        }.await()


    suspend fun loadBrandDevice(): List<String> = viewModelScope.async {

        return@async NewOrderRepositoryImpl.getListBrandsDevices()

    }.await()

    suspend fun loadDefectDevice() =
        viewModelScope.async {

            return@async NewOrderRepositoryImpl.getListDefectsDevices()

        }.await()

    suspend fun loaEdequipmentDevice() =
        viewModelScope.async {

            return@async NewOrderRepositoryImpl.getListEquipmentsDevices()

        }.await()

    fun setTypeDeviceToLiveData(view: EditText) {

        _typeDevice.value = view.text.toString()

    }

    fun setSerialNumberLiveData(view: EditText) {

        _serialNumberDevice.value = view.text.toString()

    }

    fun setBrandDeviceToLiveData(view: EditText) {

        _brandDevice.value = view.text.toString()

    }

    fun setModelDeviceToLiveData(view: EditText) {

        _modelDevice.value = view.text.toString()

    }

    fun setPassLiveData(view: EditText) {

        _passwordDevice.value = view.text.toString()

    }

    fun setDefectDeviceToLiveData(view: AutoCompleteTextView) {

        _defectDevice.value = view.text.toString()

    }

    fun setIsNewDeviceToLiveData(position: Int) {

          _isNewDevice.value = IsNewDeviceArray()[position]

    }

    fun setEquipmentDeviceToLiveData(view: AutoCompleteTextView) {

        _equipmentDevice.value = view.text.toString()

    }

    fun setUrgentToLiveData(view: CheckBox) {

        _orderData.value?.urgent = view.isChecked

    }

    @SuppressLint("StringFormatInvalid")
    fun IsNewDeviceArray(): ArrayList<String> {

        return arrayListOf(appContext.getString(R.string.enum_new_device), appContext.getString(R.string.enum_old_device))

    }


     fun CheckNewOrder(): Boolean{

        var successful = true

        val message = _orderData.value?.let { NewOrderRepositoryImpl.checkOrderBeforeSave(it) }

        if (message.isNullOrEmpty()){
            //everything ok. Saving!
        }else{
            successful = false
            Toast.makeText(
                appContext,
                message,
                Toast.LENGTH_LONG
            ).show()
        }

        return successful
    }

    suspend fun saveNewOrder(): Boolean {

        val result = CoroutineScope(Dispatchers.IO).async {
            return@async saveNewOrder()
        }.await()

        println("create order result view model $result")
        return result

    }

    //использовала для получения типов заказов
    suspend fun getOrderTypes(){
        var orderTypes = getOrderTypes()
        val i = 1
    }

    //использовала для получения branches заказов
//    suspend fun getBranchesList(){
//        var listOrders = getListBranches()
//        //1 branch_id = 11029
//    }

}


