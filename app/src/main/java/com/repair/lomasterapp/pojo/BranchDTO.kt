package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class BranchDTO(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("branch_id")
    val branch_id: Int?

)
