package com.repair.lomasterapp.calendarDate

import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import androidx.annotation.RestrictTo
import androidx.databinding.*
import java.util.*


@RestrictTo(RestrictTo.Scope.LIBRARY)
@InverseBindingMethods(InverseBindingMethod(type = CalendarView::class, attribute = "android:date"))
object CalendarViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:setDate")
    fun setDate(view: CalendarView, date: Long) {
        if (view.date != date) {
            view.date = date
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["android:onSelectedDayChange", "android:dateAttrChanged"],
        requireAll = true
    )
    fun setListeners(
        view: CalendarView,
        onDayChange: OnDateChangeListener?,
        attrChange: InverseBindingListener?
    ) {
        if (attrChange == null) {
            view.setOnDateChangeListener(onDayChange)
        } else {
            view.setOnDateChangeListener { view, year, month, dayOfMonth ->
                onDayChange?.onSelectedDayChange(view, year, month, dayOfMonth)
                val instance = Calendar.getInstance()
                instance[year, month] = dayOfMonth
                view.date = instance.timeInMillis
                attrChange.onChange()
            }
        }
    }

    @InverseBindingAdapter(attribute = "android:getDate", event = "android:dateAttrChanged")
    fun getDateLong(view: CalendarView): Long {
        return view.date
    }


}