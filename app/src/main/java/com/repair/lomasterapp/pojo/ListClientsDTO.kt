package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class ListClientsDTO(

    @SerializedName("data")
    val data: List<ClientDTO>? = null,

    @SerializedName("success")
    val success: Boolean? = null

)
