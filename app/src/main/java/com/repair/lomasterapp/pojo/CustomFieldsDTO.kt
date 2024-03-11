package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class CustomFieldsDTO(

    @SerializedName("id")
    var id: String? = "",

    @SerializedName("name")
    var name: String? = "",

    )
