package com.repair.lomasterapp.clients

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.repair.lomasterapp.LomasterApplication.Companion.appContext
import com.repair.lomasterapp.R
import com.repair.lomasterapp.databinding.NewClientFragment2Binding
import com.repair.lomasterapp.entity.ClientObj
import com.repair.lomasterapp.entity.UserPhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class NewClientViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var userPhone: UserPhone

    private val repository: ClientRepositoryImpl = ClientRepositoryImpl

    private val repositoryDB: ClientRepositoryDB = ClientRepositoryDB

    private val _clientData = MutableLiveData<ClientObj>()
    val clientData: LiveData<ClientObj>
        get() = _clientData

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }


    fun setClientInformationToLiveData() {

        _clientData.value = repository.getClientObjFragment1(_clientData.value, userPhone)

    }


    fun checkClientInformation(): Boolean {

        if (!repository.checkClientInformation(_clientData.value!!)) {
            Toast.makeText(
                appContext,
                appContext.getString(R.string.error_filling_data_client),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else {
            return true
        }
    }

    fun checkClientContacts(): Boolean {

        val clientObj = _clientData.value

        if (!clientObj?.let { repository.checkClientContacts(it) }!!) {
            Toast.makeText(
                appContext,
                appContext.getString(R.string.error_filling_data_client),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else {
            return true
        }
    }

    suspend fun saveClientOnServer(): Boolean {

        return repositoryDB.saveClientOnServer(_clientData.value)

    }


    fun getClientPhoneFragment2(binding: NewClientFragment2Binding): UserPhone {
        return repository.getClientPhoneFragment2(binding)
    }

    private suspend fun saveClientPhoneToLocalDataStore(numberPhone: String,context: Context){
        repository.saveClientPhoneToLocalDataStore(numberPhone, context)
    }

    fun onTextChangedNameClient(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.name = s.toString()
    }

    fun onTextChangedFirstNameClient(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.firstName = s.toString()
    }

    fun onTextChangedSecondNameClient(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.secondName = s.toString()
    }

    fun onTextChangedPhone1(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.phone?.set(0, s.toString())
    }

    fun onTextChangedPhone2(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.phone?.set(1, s.toString())
    }

    fun onTextChangedPhone3(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.phone?.set(2, s.toString())
    }

    fun onTextChangedEmail(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.email = s.toString()
    }

    fun onTextChangedAdress(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.address = s.toString()
    }

}