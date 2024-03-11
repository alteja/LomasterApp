package com.repair.lomasterapp.entity
data class ClientObj (

    var firstName: String? = "",

    var name: String? = "",

    var secondName: String? = "",

    var phone: Array<String?> = arrayOfNulls<String>(3),

    var email: String? = "",

    var address: String? = "",

    val id: Int? = null

)

