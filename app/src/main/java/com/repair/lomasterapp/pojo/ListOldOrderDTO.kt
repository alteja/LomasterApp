package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

class ListOldOrderDTO(

    @SerializedName("data")
    val data: List<OldOrderDTO>? = null,

    @SerializedName("success")
    val success: Boolean? = null

)