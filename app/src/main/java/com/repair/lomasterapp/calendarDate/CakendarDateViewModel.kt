package com.repair.lomasterapp.calendarDate

import android.util.Log
import androidx.lifecycle.ViewModel
import com.repair.lomasterapp.entity.OrderObj
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarDateViewModel @Inject constructor(): ViewModel() {

    val repository = CalendarDateRepositoryImpl

    fun setNewDateToOrder(order: OrderObj, year: Int, month: Int, dayOfMonth: Int){

        val calender: Calendar = Calendar.getInstance()

        calender.set(year, month, dayOfMonth)
        Log.d("SelectedDate", "$dayOfMonth/${month + 1}/$year")

        order.let {
            CalendarDateRepositoryImpl.setNewDateToOrder(
                calender.timeInMillis,
                it as OrderObj
            )
        }

    }

    fun getDateOrder(order: OrderObj): Long?{

        if (order.assigned_at_long?.toInt() == 0){

            val calender: Calendar = Calendar.getInstance()

            with(LocalDateTime.now()){
                calender.set(year, monthValue, dayOfMonth)
            }

           return calender.timeInMillis

        }
        else{

            return order.assigned_at_long
        }
    }
}