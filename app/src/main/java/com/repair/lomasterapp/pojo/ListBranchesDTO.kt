package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class ListBranchesDTO(

    @SerializedName("count")
    val count: Int? = null,

    @SerializedName("data")
    val data: List<BranchDTO>? = null,

    @SerializedName("success")
    val success: Boolean? = null
)
