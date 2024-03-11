package com.repair.lomasterapp.pojo
import com.google.gson.annotations.SerializedName

data class ClientDTO(

    @SerializedName("id")
    var id: Int? = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("phone")
    val phone: Array<String?>,

    @SerializedName("email")
    val email: String? = "",

    @SerializedName("notes")
    val notes: String? = "",

    @SerializedName("address")
    val address: String? = "",

    @SerializedName("supplier")
    val supplier: Boolean? = false,

    @SerializedName("juridical")
    val juridical: Boolean? = false,

    @SerializedName("conflicted")
    val conflicted: Boolean? = false,

    @SerializedName("discount_code")
    val discount_code: String? = "",


    )