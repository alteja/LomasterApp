package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class SettingsServicesDTO(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("title")
    val title:String? = "",

    @SerializedName("price")
    val price: Float? = null,

    @SerializedName("amount")
    val amount: Int? = 0

)
