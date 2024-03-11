package com.repair.lomasterapp.calendarDate

import android.text.format.DateFormat
import com.repair.lomasterapp.entity.OrderObj
import java.util.*

object CalendarDateRepositoryImpl {

    fun setNewDateToOrder(dateLong: Long, order: OrderObj): OrderObj {

        order.assigned_at_long = dateLong

        val newDate = Date(dateLong)

        order.assigned_at_date_string = DateFormat.format("dd/MM/yy", newDate) as String

        return order

    }




}