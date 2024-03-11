package com.repair.lomasterapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class OldOrderObj(

    val id: Int? = null,

    val  fioClient: String? = "",
    val  phoneClient: String? = "",
    val  emailClient: String? = "",

    var dateSetting: Long? = 0,

    //тип устройства
    var kindof_good: String? = "",

    var serial: String? = "",
    var brand: String? = "",
    var model: String? = "",
    var passwordDevice: String? = "",

    //внешний вид
    var appearance: String? = null,

    //комплектация
    var packagelist: String? = "",

    var urgent: Boolean? = false,

    //Время вызова мастера
    var assigned_at_long: Long? = 0,
    var assigned_at_date_string: String? = "",

    val  client_id: Int? = null,

    //неисправность
    var malfunction: String? = "",

    //ориентировачная стоимость
    val estimated_cost: Float? = null,

    //заметки приемщика
    val manager_notes: String? = "",

    val status: OrderStatus? = null,

    //материалы, работы по заказу
    val parts: ArrayList<SettingsServices>? = null,

    val operations: ArrayList<SettingsServices>? = null

): Parcelable