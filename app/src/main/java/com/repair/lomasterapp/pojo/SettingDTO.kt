package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class SettingDTO(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String
)
