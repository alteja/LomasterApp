package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class ListCustomFieldsDTO(
    @SerializedName("data")
    var data: List<CustomFieldsDTO>? = null,

    //тип устройства
    @SerializedName("success")
    var success: Boolean? = false,

    @SerializedName("count")
    val count: Int? = null
)
