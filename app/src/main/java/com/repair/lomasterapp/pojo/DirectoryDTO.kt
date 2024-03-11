package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class DirectoryDTO(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String
)
