package com.repair.lomasterapp.orders

import com.repair.lomasterapp.LomasterApplication.Companion.apiServiceLomaster
import com.repair.lomasterapp.LomasterApplication.Companion.appContext
import com.repair.lomasterapp.entity.ClientObj
import com.repair.lomasterapp.entity.OrderObj
import com.repair.lomasterapp.pojo.ClientDTO
import com.repair.lomasterapp.pojo.DirectoryDTO
import com.repair.lomasterapp.pojo.GetListOrderId
import com.repair.lomasterapp.pojo.ListDirectories
import com.repair.lomasterapp.pojo.ListSettingDTO
import com.repair.lomasterapp.pojo.OrderDTO
import com.repair.lomasterapp.pojo.SettingDTO
import retrofit2.HttpException
import java.util.Calendar

object NewOrderRepositoryImpl : NewOrderInterface {

    private suspend fun getToken(): String {

        var tokenJson = apiServiceLomaster.getToken()
        var token = ""

        if (tokenJson.success) {
            token = tokenJson.token
            println("TOKEN $token")
        }

        return token
    }

    suspend fun getClientInformation(userPhone: String): ClientObj? {

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

    override suspend fun getListTypeDevices(): List<String> {

        var token = getToken()

        var typeDeviceListDTO = emptyList<DirectoryDTO>()

        var typeDevList: ListDirectories

        if (token.isNotEmpty()) {

            try {

                typeDevList = apiServiceLomaster.getListTypesDevices(
                    token_value = token
                )

                if (typeDevList.count != 0) {
                    typeDeviceListDTO = typeDevList.data!!
                }

            } catch (e: Exception) {
                println("ERROR BRANDS $e")
            }

        }

        return convertDirectoryFromDTOToList(typeDeviceListDTO)
    }


    override suspend fun getListBrandsDevices(): List<String> {

        var token = getToken()

        var brandDeviceListDTO = emptyList<DirectoryDTO>()

        var brandList: ListDirectories

        if (token.isNotEmpty()) {

            try {

                brandList = apiServiceLomaster.getListBrandsDevices(
                    token_value = token
                )

                if (brandList.count != 0) {
                    brandDeviceListDTO = brandList.data!!
                }

            } catch (e: Exception) {
                println("ERROR BRANDS $e")
            }

        }

        return convertDirectoryFromDTOToList(brandDeviceListDTO)

    }

    override suspend fun getListModelsDevices(): List<String> {

        var token = getToken()

        var modelDeviceListDTO = emptyList<DirectoryDTO>()

        var modelList: ListDirectories

        if (token.isNotEmpty()) {

            try {

                modelList = apiServiceLomaster.getListModelsDevices(
                    token_value = token
                )

                if (modelList.count != 0) {
                    modelDeviceListDTO = modelList.data!!
                }

            } catch (e: Exception) {
                println("ERROR MODELS $e")
            }

        }

        return convertDirectoryFromDTOToList(modelDeviceListDTO)

    }

    private fun convertDirectoryFromDTOToList(listDTO: List<DirectoryDTO>): List<String> {

        return listDTO.map { it.title }.toList()

    }

    override suspend fun getListDefectsDevices(): List<String> {

        var token = getToken()

        var defectDeviceListDTO = emptyList<DirectoryDTO>()

        var defectList: ListDirectories

        if (token.isNotEmpty()) {

            try {

                defectList = apiServiceLomaster.getListDefectsDevices(
                    token_value = token
                )

                if (defectList.count != 0) {
                    defectDeviceListDTO = defectList.data!!
                }

            } catch (e: Exception) {
                println("ERROR DEFECTS $e")
            }

        }

        return convertDirectoryFromDTOToList(defectDeviceListDTO)


    }


    override suspend fun getListEquipmentsDevices(): List<String> {

        var token = getToken()

        var equipmentDeviceListDTO = emptyList<DirectoryDTO>()

        var equipmentList: ListDirectories

        if (token.isNotEmpty()) {

            try {

                equipmentList = apiServiceLomaster.getListEquipmentsDevices(
                    token_value = token
                )

                if (equipmentList.count != 0) {
                    equipmentDeviceListDTO = equipmentList.data!!
                }

            } catch (e: Exception) {
                println("ERROR EQUIPMENTS $e")
            }

        }

        return convertDirectoryFromDTOToList(equipmentDeviceListDTO)

    }


//    suspend fun setTypeDeviceNewOrder(
//        binding: NewRequestFragmentBinding,
//        context: Context
//    ) {
//
//        val listTypeDevices = CoroutineScope(Dispatchers.IO).async {
//
//            return@async getListTypeDevices()
//
//        }.await()
//
//        binding.typeDeviceReq.setAdapter(
//
//            ArrayAdapter(
//                context,
//                R.layout.simple_dropdown_item_1line,
//                listTypeDevices
//            )
//        )
//
//    }

//    suspend fun setModelDeviceNewReq(
//        binding: NewRequestFragmentBinding,
//        context: Context
//    ) {
//
//        val listModel = CoroutineScope(Dispatchers.IO).async {
//            return@async getListModelsDevices()
//        }.await()
//
//        //    binding.modelDeviceReq.setAdapter(
//        //      ArrayAdapter(
//        //        context,
//        //        R.layout.simple_dropdown_item_1line,
//        //        listModel
//        //    )
//        // )
//
//    }
//
//    suspend fun setBrandDeviceNewOrder(
//        binding: NewRequestFragmentBinding,
//        context: Context
//    ) {
//
//        val listBrands = CoroutineScope(Dispatchers.IO).async {
//
//            return@async getListBrandsDevices()
//
//        }.await()
//
//        binding.brandDeviceReq.setAdapter(
//            ArrayAdapter(
//                context,
//                R.layout.simple_dropdown_item_1line,
//                listBrands
//            )
//        )
//
//    }

//    suspend fun setDefectsDeviceNewOrder(
//        binding: NewRequestFragmentBinding,
//        context: Context
//    ) {
//
//        val listDefectsDevices = CoroutineScope(Dispatchers.IO).async {
//            return@async getListDefectsDevices()
//        }.await()
//
//
//        binding.defectDeviceReq.setAdapter(
//            ArrayAdapter(
//                context,
//                R.layout.simple_dropdown_item_1line,
//                listDefectsDevices
//            )
//        )
//    }
//
//    suspend fun setEquipmentDeviceNewOrder(
//        binding: NewRequestFragmentBinding,
//        context: Context
//    ) {
//
//        val listEquipmentDevices = CoroutineScope(Dispatchers.IO).async {
//            return@async getListEquipmentsDevices()
//        }.await()
//
//
//        binding.equipmentDeviceReq.setAdapter(
//            ArrayAdapter(
//                context,
//                R.layout.simple_dropdown_item_1line,
//                listEquipmentDevices
//            )
//        )
//    }

    fun checkOrderBeforeSave(order: OrderObj): String {

        var message = ""

        with(appContext) {

            if (order.fioClient.isNullOrEmpty()) {
                message = getString(com.repair.lomasterapp.R.string.empty_fio)
                return message
            }

            if (order.phoneClient.isNullOrEmpty()) {
                message = getString(com.repair.lomasterapp.R.string.empty_phone)
                return message
            }

            if (order.emailClient.isNullOrEmpty()) {
                message = getString(com.repair.lomasterapp.R.string.empty_email)
                return message
            }

            if (order.kindof_good.isNullOrEmpty()) {
                message = getString(com.repair.lomasterapp.R.string.empty_type_device)
                return message
            }

            if (order.serial.isNullOrEmpty()) {
                message = getString(com.repair.lomasterapp.R.string.empty_serial_number)
                return message
            }

            if (order.brand.isNullOrEmpty()) {
                message = getString(com.repair.lomasterapp.R.string.empty_brand_device)
                return message
            }

            if (order.model.isNullOrEmpty()) {
                message = getString(com.repair.lomasterapp.R.string.empty_model_device)
                return message
            }

            if (order.packagelist.isNullOrEmpty()) {
                message = getString(com.repair.lomasterapp.R.string.empty_packagelist)
                return message
            }

            if (order.malfunction.isNullOrEmpty()) {
                message = getString(com.repair.lomasterapp.R.string.empty_malfunction)
                return message
            }
        }

        return message
    }

    suspend fun convertOrderObjToOrderDB(order: OrderObj, token: String): OrderDTO {

        val listClients = apiServiceLomaster.getClient(
            token_value = token,
            user_phone = order.phoneClient.toString()
        )

        val orderDTO = with(order) {

            OrderDTO(
                id,
                dateSetting,
                brand,
                model,
                serial,
                urgent,
                estimated_cost,
                appearance,
                malfunction,
                assigned_at_long,
                null,
                "",
                kindof_good,
                null
            )
        }

        orderDTO.client_id = getFirstClient(listClients.data)?.id
        orderDTO.estimated_done_at = orderDTO.assigned_at
        orderDTO.branch_id = 11029
        orderDTO.order_type = 19521
        orderDTO.created_at = Calendar.getInstance().getTime().time

        //пользовательские поля заказа.
        // при передаче на сервер должны быть в формате:
        // {"id поля" : "значение", "id поля":"значение"}

        val model = order.model.toString()
        val brand = order.brand.toString()
        val serial = order.serial.toString()
        val packagelist = order.packagelist.toString()
        val pass = order.passwordDevice.toString()
        val kindof_good = order.kindof_good.toString()
        val appearance = order.appearance.toString()

        val custom_fields_string = buildString {
            append("{\"156190\": \"$model\",")
            append("\"156191\": \"$brand\",")
            append("\"156196\": \"$serial\",")
            append("\"156187\": \"$packagelist\",")
            append("\"1786791\": \"$pass\",")
            append("\"156186\": \"$kindof_good\",")
            append("\"156188\": \"$appearance\"}")
        }

        orderDTO.custom_fields = custom_fields_string

        return orderDTO
    }


    suspend fun saveNewOrder(order: OrderObj): Boolean {

        var succsessfull = false

        val token = getToken()

        val orderDTO = convertOrderObjToOrderDB(order, token)

        try {
            var idOrderDB = GetListOrderId()

            try {
                idOrderDB = apiServiceLomaster.createNewOrder(
                    token_value = token,
                    //  branch_id = 11029,
                    //  order_type = 19521,
                    order = orderDTO
                )
            } catch (e: HttpException) {
                println(e.response()?.errorBody()?.string())
            }

            if (idOrderDB.success == true
                && idOrderDB.data!!.id > 0
            ) {
                succsessfull = true
            }
        } catch (e: IllegalStateException) {
            println("error order")
            println("error saving order: ${e.message}")
        }

        return succsessfull

    }


    suspend fun getOrderTypes(): List<SettingDTO> {

        var token = getToken()

        var orderTypesListDTO = emptyList<SettingDTO>()
        var orderList = ListSettingDTO()

        if (token.isNotEmpty()) {

            try {

                orderList = apiServiceLomaster.getOrderTypes(
                    token_value = token
                )

                if (orderList.count != 0) {
                    orderTypesListDTO = orderList.data!!
                }

            } catch (e: Exception) {
                println("ERROR DEFECTS $e")
            }

        }

        return orderTypesListDTO

    }

    suspend fun getListBranches(): List<SettingDTO> {

        var token = getToken()

        var branchesListDTO = emptyList<SettingDTO>()
        var branchesList = ListSettingDTO()

        if (token.isNotEmpty()) {

            try {

                branchesList = apiServiceLomaster.getBranches(
                    token_value = token
                )

                if (branchesList.count != 0) {
                    branchesListDTO = branchesList.data!!
                }

            } catch (e: Exception) {
                println("ERROR DEFECTS $e")
            }

        }

        return branchesListDTO

    }


}








