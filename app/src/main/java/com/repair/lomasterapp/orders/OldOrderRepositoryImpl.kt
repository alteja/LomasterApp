package com.repair.lomasterapp.orders

import com.google.gson.internal.LinkedTreeMap
import com.repair.lomasterapp.LomasterApplication.Companion.apiServiceLomaster
import com.repair.lomasterapp.entity.OldOrderObj
import com.repair.lomasterapp.entity.OrderStatus
import com.repair.lomasterapp.entity.SettingsServices
import com.repair.lomasterapp.pojo.ClientDTO
import com.repair.lomasterapp.pojo.ListOldOrderDTO
import com.repair.lomasterapp.pojo.OldOrderDTO
import com.repair.lomasterapp.pojo.OrderStatusDTO
import com.repair.lomasterapp.pojo.SettingsServicesDTO

object OldOrderRepositoryImpl {

    const val BRAND_CUSTOM = "f156191"
    const val TYPE_DEVICE_CUSTOM = "f156186"
    const val SERIAL_CUSTOM = "f156196"
    const val APPIARENCE_CUSTOM = "f156188"
    const val PACKAGELIST_CUSTOM = "f156187"
    const val MODEL_CUSTOM = "f156190"
    const val PASSWORD_CUSTOM = "f1786791"

    suspend fun getOldOrderDataFromServer(idOrder: Int, userPhone: String): OldOrderObj {

        val clientDTO = getClientInformationDTO(userPhone)

        return getOldOrder(idOrder, clientDTO)

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

    private suspend fun getOldOrder(idOrder: Int, clientDTO: ClientDTO?): OldOrderObj {

        var token = getToken()

        var ordersListDTO = emptyList<OldOrderDTO>()
        var orderList = ListOldOrderDTO()
        var oldOrderDB = OldOrderDTO()

        if (token.isNotEmpty()) {

            try {

                orderList = apiServiceLomaster.getListOldOrders(
                    token_value = token,
                    client_phone = clientDTO!!.phone[0].toString()
                )

                if (orderList.success == true) {
                    ordersListDTO = orderList.data!!

                    oldOrderDB = ordersListDTO.filter{it.id == idOrder}.get(0)
                }

            }catch (e: Exception){
               // println("ERROR REQUEST: " + e.response()?.errorBody()?.string())
                println("ERROR REQUEST: " + e.message)
            }

        }

        println("ПОЛУЧИЛИ СТАРЫЙ ЗАКАЗ")

        return convertOldOrderDBToOldOrderObj(oldOrderDB, clientDTO)
    }


    fun convertOldOrderDBToOldOrderObj(
        oldOrder: OldOrderDTO,
        clientDTO: ClientDTO?
    ): OldOrderObj {

        println("КОНВЕРТИРУЕМ СТАРЫЙ ЗАКАЗ")

        val mapCustomFields = oldOrder.custom_fields as LinkedTreeMap<*,*>

        val order = with(oldOrder) {
            OldOrderObj(
                id,
                clientDTO?.name,
                clientDTO!!.phone[0]!!.toString(),
                clientDTO.email,
                created_at, //data setting
                mapCustomFields.get(TYPE_DEVICE_CUSTOM) as String?,
                mapCustomFields.get(SERIAL_CUSTOM) as String?,
                mapCustomFields.get(BRAND_CUSTOM) as String?,
                mapCustomFields.get(MODEL_CUSTOM) as String?,
                mapCustomFields.get(PASSWORD_CUSTOM) as String?,
                mapCustomFields.get(APPIARENCE_CUSTOM) as String?,
                mapCustomFields.get(PACKAGELIST_CUSTOM) as String?,
                urgent,
                assigned_at,
                "", //!!
                clientDTO.id,
                malfunction,
                estimated_cost,
                manager_notes,
                condertStatusDTOToObj(status),
                convertArraySettingsDTOToObj(parts),
                convertArraySettingsDTOToObj(operations)
            )
        }

        println("ORDER:${order}")
        return order

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

    private fun convertArraySettingsDTOToObj(arrayDTO: Array<SettingsServicesDTO>): ArrayList<SettingsServices>{

        val newList = ArrayList<SettingsServices>()

        var summTax = 0

        for (element in arrayDTO){

            if (element.title?.equals("Обязательная налоговая нагрузка") == true){
                summTax+= element.price!!.toInt() * element.amount!!.toInt()
                continue
            }

            val newSetting = with(element){
                SettingsServices(
                    id,
                    title,
                    price,
                    amount
                )
            }
            newList.add(newSetting)
        }

        if (newList.size > 0){
            newList[0].price = newList[0].price?.plus(summTax)
        }

        return newList
    }

    private fun condertStatusDTOToObj(statusDTO: OrderStatusDTO?): OrderStatus {

        return with(statusDTO) {
            OrderStatus(
                this?.id,
                this?.name,
                this?.color
            )
        }

    }

}
