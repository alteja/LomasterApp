package com.repair.lomasterapp.mainScreens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.repair.lomasterapp.LomasterApplication.Companion.apiServiceLomaster
import com.repair.lomasterapp.pojo.ClientDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(): ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private suspend fun getToken(): String {

        var tokenJson = apiServiceLomaster.getToken()
        var token = ""

        if (tokenJson.success) {
            token = tokenJson.token
            println("TOKEN $token")
        }

        return token
    }


    suspend fun getClientInformationDTO(userPhone: String): String? {

        var clientList = apiServiceLomaster.getClient(
            token_value = getToken(),
            user_phone = userPhone
        )

        var client: ClientDTO? = null

        if (clientList.success == true
            && clientList.data?.size != 0
        ) {

            client = clientList.data?.get(0)
        }

        return parseNameClient(client?.name)
    }

    fun parseNameClient(nameClient: String?): String {

        return nameClient?.substringAfter(' ')?.substringBefore(' ').toString()

    }

}