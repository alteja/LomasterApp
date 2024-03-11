package com.repair.lomasterapp.entity

import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
 data class OrderObj @Inject constructor(

    val id: Int? = null,

    val fioClient: String? = "",
    val phoneClient: String? = "",
    val emailClient: String? = "",
    var dateSetting: Long? = 0,

    var kindof_good: String? = "",

    var serial: String? = "",
    var brand: String? = "",
    var model: String? = "",
    var passwordDevice: String? = "",

    var appearance: String? = null,

    var packagelist: String? = "",

    var urgent: Boolean? = false,

    var assigned_at_long: Long? = 0,
    var assigned_at_date_string: String? = "",

    val client_id: Int? = null,

    var malfunction: String? = "",

    val estimated_cost: Float? = null,

    val manager_notes: String? = "",

    val status: String? = "",

    var operations: ArrayList<SettingsServices>? = null,


    var parts: ArrayList<SettingsServices>? = null,

    ){

    fun isEmptyOrder(): Boolean{

        return fioClient.isNullOrEmpty() || phoneClient.isNullOrEmpty()

    }
}
