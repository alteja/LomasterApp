package com.repair.lomasterapp.pojo

import com.google.gson.annotations.SerializedName

data class OldOrderDTO(

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("created_at")
    var created_at: Long? = 0,

    @SerializedName("brand")
    var brand: String? = "",

    @SerializedName("model")
    var model: String? = "",

    @SerializedName("serial")
    var serial: String? = "",

    @SerializedName("urgent")
    var urgent: Boolean? = false,

    //ориентировачная стоимость
    @SerializedName("estimated_cost")
    val estimated_cost: Float? = null,

    //внешний вид
    @SerializedName("appearance")
    var appearance: String? = "",

    //комплектация
    @SerializedName("packagelist")
    var packagelist: String? = "",

    //неисправность
    @SerializedName("malfunction")
    var malfunction: String? = "",

    //Время вызова мастера
    @SerializedName("assigned_at")
    var assigned_at: Long? = 0,

    //ориентировачная стоимость
    @SerializedName("estimated_done_at")
    var estimated_done_at: Long? = null,

    //заметки приемщика
    @SerializedName("manager_notes")
    val manager_notes: String? = "",

    //тип устройства
    @SerializedName("kindof_good")
    var kindof_good: String? = "",

    @SerializedName("client_id")
    var client_id: Int? = null,

    @SerializedName("branch_id")
    var branch_id: Int? = null,

    @SerializedName("order_type")
    var order_type: OrderTypeDTO? = null,

    @SerializedName("status")
    var status: OrderStatusDTO? = null,

    @SerializedName("operations")
    var operations: Array<SettingsServicesDTO> = emptyArray(),

    @SerializedName("parts")
    var parts: Array<SettingsServicesDTO> = emptyArray(),

    @SerializedName("custom_fields")
    var custom_fields: Any? = null

)
