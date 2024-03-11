package com.repair.lomasterapp.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.repair.lomasterapp.databinding.ClientFragmentBinding
import com.repair.lomasterapp.entity.ClientObj
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(): ViewModel() {

    private val repository: ClientRepositoryImpl = ClientRepositoryImpl

    private val repositoryDB: ClientRepositoryDB = ClientRepositoryDB

    private val _clientData = MutableLiveData<ClientObj>()
    val clientData: LiveData<ClientObj>
        get() = _clientData

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun seClientDataViewModel(clientObj: ClientObj?){
        _clientData.value = clientObj!!
    }

    suspend fun saveClientOnServer(): Boolean {

        val result = repositoryDB.saveOldClientOnServer(_clientData.value)
        println("saving result: $result")
        return result

    }

    fun setVisiblePhones(binding: ClientFragmentBinding) {
        repository.setVisiblePhonesOldClient(binding)
    }

    fun parseNameClient(nameClient: String?): String {

        return nameClient?.substringAfter(' ')?.substringBefore(' ').toString()

    }

    fun parseFirstNameClient(nameClient: String?):String {

        return nameClient?.substringBefore(' ').toString()

    }

    fun parseSecondNameClient(nameClient: String?):String {

        val masNames = nameClient?.split("\\s".toRegex())?.toTypedArray()
        var secondName = ""

        if (masNames!!.size > 2){
            secondName = nameClient.substringAfterLast(' ').toString()
        }
        return secondName

    }

    fun onTextChangedName(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.name = s.toString()
    }

    fun onTextChangedFirstName(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.firstName = s.toString()
    }

    fun onTextChangedSecondName(s:CharSequence, start:Int, before:Int, count: Int){
        _clientData.value?.secondName = s.toString()
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

    fun equalsClient(clientServer: ClientObj):Boolean{

        return with(clientData.value!!) {
            firstName.equals(clientServer.firstName)
            name.equals(clientServer.name)
            secondName.equals(clientServer.secondName)
            email.equals(clientServer.email)
            address.equals(clientServer.address)
            phone.equals(clientServer.phone)
        }

    }

}