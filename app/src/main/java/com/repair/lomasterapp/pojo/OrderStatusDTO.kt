package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class OrderStatusDTO(

    @SerializedName("id")
    var id: Double? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("color")
    var color: String? = null,

    )
