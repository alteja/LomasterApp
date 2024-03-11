package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class ListDirectories(

    @SerializedName("data")
    val data: List<DirectoryDTO>? = null,

    @SerializedName("count")
    val count: Int? = null
)
