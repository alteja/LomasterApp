package com.repair.lomasterapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class OrderStatus (

    var id: Double? = null,

    var name: String? = null,

    var color: String? = null

): Parcelable