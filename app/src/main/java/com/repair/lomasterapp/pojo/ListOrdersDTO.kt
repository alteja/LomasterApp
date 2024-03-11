package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class ListOrdersDTO(

    @SerializedName("data")
    val data: List<OdrerDTOList>? = null,

    @SerializedName("success")
    val success: Boolean? = null
)
