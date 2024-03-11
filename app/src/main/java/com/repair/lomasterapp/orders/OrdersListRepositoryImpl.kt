package com.repair.lomasterapp.orders

import com.repair.lomasterapp.LomasterApplication.Companion.apiServiceLomaster
import com.repair.lomasterapp.entity.ClientObj
import com.repair.lomasterapp.entity.OrderObj
import com.repair.lomasterapp.pojo.ClientDTO
import com.repair.lomasterapp.pojo.ListOrdersDTO
import com.repair.lomasterapp.pojo.OdrerDTOList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

object OrdersListRepositoryImpl : ListOrderInterface {

    override suspend fun getOrdersListFromServer(userPhone: String): List<OrderObj> {

        return CoroutineScope(Dispatchers.IO).async {

            var clientDTO = getClientInformationDTO(userPhone)

            return@async getListOrders(clientDTO)

        }.await()

    }

    private suspend fun getToken(): String {

        var tokenJson = apiServiceLomaster.getToken()
        var token = ""

        if (tokenJson.success) {
            token = tokenJson.token
            println("TOKEN $token")
        }

        return token
    }

    private suspend fun getListOrders(clientDTO: ClientDTO?): List<OrderObj> {

        var token = getToken()

        println("toket: $token")

        var ordersListDTO = emptyList<OdrerDTOList>()
        var orderList = ListOrdersDTO()


        if (token.isNotEmpty()) {

            try {

                orderList = apiServiceLomaster.getListOrders(
                    token_value = token,
                    client_phone = clientDTO!!.phone[0].toString()
                )

                if (orderList.success == true) {
                    ordersListDTO = orderList.data!!
                }

            } catch (e: Exception) {
                println("ERROR DEFECTS $e")
            }

        }

        return convertlistOrdersDBToOrdersObj(ordersListDTO, clientDTO)
    }

    private fun convertlistOrdersDBToOrdersObj(
        listOrderDTO: List<OdrerDTOList>,
        clientDTO: ClientDTO?
    ): List<OrderObj> {

        var newList = emptyList<OrderObj>().toMutableList()
        var newOder: OrderObj

        listOrderDTO.forEach {

            newOder = with(it) {
                OrderObj(
                    id,
                    clientDTO?.name,
                    clientDTO?.phone.toString(),
                    clientDTO?.email,
                    created_at, //data setting
                    kindof_good,
                    serial,
                    brand,
                    model,
                    "",
                    appearance,
                    packagelist,
                    urgent,
                    assigned_at,
                    "", //!!
                    clientDTO?.id,
                    malfunction,
                    estimated_cost,
                    manager_notes,
                    (it.status as Map<String,String>).get("name")
                )
            }

            newList.add(newOder)

        }

        newList.sortByDescending { it.dateSetting }

        return newList
    }


    private suspend fun getClientInformation(userPhone: String): ClientObj? {

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

    private suspend fun getClientInformationDTO(userPhone: String): ClientDTO? {

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


}