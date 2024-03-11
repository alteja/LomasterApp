package com.repair.lomasterapp.orders

interface NewOrderInterface {

    suspend fun getListTypeDevices(): List<String>

    suspend fun getListBrandsDevices(): List<String>

    suspend fun getListModelsDevices(): List<String>

    suspend fun getListDefectsDevices(): List<String>

    suspend fun getListEquipmentsDevices(): List<String>
}