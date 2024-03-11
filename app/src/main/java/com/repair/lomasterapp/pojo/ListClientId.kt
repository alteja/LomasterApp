package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class ListClientId(

    @SerializedName("data")
    val data: GetClientId? = null,

    @SerializedName("success")
    var success: Boolean? = false
)
