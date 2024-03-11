package com.repair.lomasterapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SettingsServices(

    val id: Int? = null,

    val title:String? = "",

    var price: Float? = null,

    var amount: Int? = 0

): Parcelable
