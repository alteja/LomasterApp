package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class GetListOrderId(

    @SerializedName("data")
    val data: GetOrderId? = null,

    @SerializedName("success")
    val success: Boolean? = false

)
