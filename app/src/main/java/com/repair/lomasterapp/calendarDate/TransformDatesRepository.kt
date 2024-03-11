package com.repair.lomasterapp.calendarDate

import android.text.format.DateFormat
import java.util.*

object TransformDatesRepository {

    fun dateToString(date:Long?): String{

        val newDate = date?.let { Date(it) }

        return DateFormat.format("dd/MM/yy", newDate) as String

    }
}