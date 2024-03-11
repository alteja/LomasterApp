package com.repair.lomasterapp.clients

import android.widget.Toast
import com.repair.lomasterapp.LomasterApplication.Companion.apiServiceLomaster
import com.repair.lomasterapp.LomasterApplication.Companion.appContext
import com.repair.lomasterapp.R
import com.repair.lomasterapp.api.GetTokenSingleton
import com.repair.lomasterapp.entity.ClientObj
import com.repair.lomasterapp.pojo.ClientDTO
import com.repair.lomasterapp.pojo.ListClientId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

object ClientRepositoryDB {

    private suspend fun getToken(): String {

        var tokenJson = apiServiceLomaster.getToken()
        var token = ""

        if (tokenJson.success) {
            token = tokenJson.token
            println("TOKEN $token")
        }

        return token
    }

    suspend fun createNewUserOnServer(clientDTO: ClientDTO): Int {

        var clientIdDB: ListClientId
        var clientId = 0

        try {

            val checkingToken = GetTokenSingleton.getInstance()
            val tokenJson = checkingToken.tokenValue.toString()

            if (tokenJson.isNotEmpty()) {

                clientIdDB = createClientOnServerWithToken(clientDTO, tokenJson)

                if (clientIdDB.success==true){
                    clientId = clientIdDB.data!!.id
                }

            }

        } catch (e: java.lang.IllegalStateException) {

            //   val checkingToken = GetTokenSingleton.getInstance()

            //     if (checkingToken?.get("TOKEN")!!.isNotEmpty()) {

            //        id = createClientOnServerWithToken(clientDTO, tokenJson)

            //   }
            println(e.message)

        }

        return clientId
    }

    suspend fun createClientOnServerWithToken(clientDTO: ClientDTO, tokenJson: String) =
        apiServiceLomaster.createNewUser(
            token_value = tokenJson,
            user_phone = clientDTO.phone[0].toString(),
            client = clientDTO
        )


    suspend fun saveUserOnServer(clientDTO: ClientDTO, tokenJson:String): Boolean{

        var succsessfull = false
        val clientIdDB: ListClientId

         succsessfull =  CoroutineScope(Dispatchers.IO).async {

            println("get token")



            try {
                val clientIdDB: ListClientId = apiServiceLomaster.saveUser(
                    token_value = tokenJson,
                    id = clientDTO.id?: 0,
                    data = clientDTO
                )

                succsessfull = clientIdDB.success == true

            }catch (e: IllegalStateException){
                println("error old client id: $clientDTO.id")
                println("error saving client: ${e.message}")
            }

            return@async succsessfull


        }.await()

        println("id client: $succsessfull")

        return succsessfull
    }

    suspend fun saveClientOnServer(clientObj: ClientObj?):Boolean{

        val clientDB = convertClientEntityToClientDB(clientObj)
        var succsessfull = false

        if (clientDB != null) {

            succsessfull =  createNewUserOnServer(clientDB) > 0


        }

         if (!succsessfull){

             Toast.makeText(
                 appContext,
                 appContext.getString(R.string.error_saving_client),
                 Toast.LENGTH_LONG
             ).show()

         }

        return succsessfull

    }

    suspend fun saveOldClientOnServer(clientObj: ClientObj?):Boolean{

        val clientDB = convertClientEntityToClientDB(clientObj)
        var succsessfull = true

        val tokenJson = getToken()
        if (clientDB != null) {

            succsessfull = CoroutineScope(Dispatchers.IO).async {

                return@async saveUserOnServer(clientDB, tokenJson)

            }.await()
        }

        if (!succsessfull){

            Toast.makeText(
                appContext,
                appContext.getString(R.string.error_saving_client),
                Toast.LENGTH_LONG
            ).show()

        }

        return succsessfull

    }

    private fun convertClientEntityToClientDB(clientEntity: ClientObj?): ClientDTO? {

        val nameClient = StringBuilder().also {
            it.append(clientEntity?.firstName)
            it.append(" ")
            it.append(clientEntity?.name)
            it.append(" ")
            it.append(clientEntity?.secondName)
        }

        return clientEntity?.let {
            it.phone?.let { it1 ->
                ClientDTO(
                    it.id,
                    nameClient.toString(),
                    it1,
                    it.email ?: "",
                    "",
                    it.address ?: "",
                    false,
                    false,
                    false,
                    ""

                )
            }
        }

    }


    suspend fun getClientInformationFromServer(userPhone: String): ClientObj? {

        var clientList = apiServiceLomaster.getClient(
            token_value = getToken(),
            user_phone = userPhone
        )

        var client: ClientObj? = null

        if (clientList.success == true
            && clientList.data?.size != 0
        ) {

            client = getFirstClient(clientList.data)
        }

        return client

    }

    private fun getFirstClient(listClientsDB: List<ClientDTO>?): ClientObj? {

        var clientInformation: ClientObj? = null

        if (listClientsDB != null) {

            for (clientDTO in listClientsDB) {

                clientInformation = ClientObj(

                    clientDTO.name,
                    clientDTO.name,
                    clientDTO.name,
                    clientDTO.phone,
                    clientDTO.email,
                    clientDTO.address,
                    clientDTO.id
                )
                break
            }

        }

        return clientInformation

    }



    private fun increasePhoneArray(clientInformation: ClientObj): ClientObj {

        var newPhoneArray = arrayOfNulls<String>(3)

        for( (index, newPhone) in newPhoneArray.withIndex()){

            try {
                newPhoneArray[index] = clientInformation.phone[index].toString()
            } catch (e: ArrayIndexOutOfBoundsException) {
                newPhoneArray[index] = ""
            }

        }

        clientInformation.phone = newPhoneArray

        return clientInformation

    }


}