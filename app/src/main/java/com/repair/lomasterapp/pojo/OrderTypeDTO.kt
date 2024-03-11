package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class OrderTypeDTO(

    @SerializedName("id")
    var id: Double? = null,

    @SerializedName("name")
    var name: String? = ""

    )
