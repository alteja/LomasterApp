package com.repair.lomasterapp.orders

import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter


class BindingAdapterOrder {

    companion object {

        @JvmStatic
        @BindingAdapter("clickTypeDevice")
        fun clickTypeDevice(view: AutoCompleteTextView, viewModel: NewOrderViewModel) {

            view.doAfterTextChanged {
                viewModel.setTypeDeviceToLiveData(view)
            }
        }

        @JvmStatic
        @BindingAdapter("clickSerialNumber")
        fun clickSerialNumber(view: EditText, viewModel: NewOrderViewModel) {

            view.doAfterTextChanged {
                viewModel.setSerialNumberLiveData(view)
            }

        }

        @JvmStatic
        @BindingAdapter("clickBrandDevice")
        fun clickBrandDevice(view: AutoCompleteTextView, viewModel: NewOrderViewModel) {

            view.doAfterTextChanged {
                viewModel.setBrandDeviceToLiveData(view)
            }

        }

        @JvmStatic
        @BindingAdapter("clickModelDevice")
        fun clickModelDevice(view: EditText, viewModel: NewOrderViewModel) {

            view.doAfterTextChanged {
                viewModel.setModelDeviceToLiveData(view)
            }

        }

        @JvmStatic
        @BindingAdapter("clickPassDevice")
        fun clickPassDevice(view: EditText, viewModel: NewOrderViewModel) {

            view.doAfterTextChanged {
                viewModel.setPassLiveData(view)
            }

        }

        @JvmStatic
        @BindingAdapter("clickDefectDevice")
        fun clickDefectDevice(view: AutoCompleteTextView, viewModel: NewOrderViewModel) {

            view.doAfterTextChanged {
                viewModel.setDefectDeviceToLiveData(view)
            }

        }

        @JvmStatic
        @BindingAdapter("clickEquipmentDevice")
        fun clickEquipmentDevice(view: AutoCompleteTextView, viewModel: NewOrderViewModel) {

            view.doAfterTextChanged {
                viewModel.setEquipmentDeviceToLiveData(view)
            }

        }

        @JvmStatic
        @BindingAdapter("clickUrgent")
        fun clickUrgent(checkBoxUrgent: CheckBox, viewModel: NewOrderViewModel) {

              checkBoxUrgent.setOnCheckedChangeListener { buttonView,
                                                        isChecked ->
                viewModel.setUrgentToLiveData(checkBoxUrgent)

            }

        }

        ///OLD ORDER
        @JvmStatic
        @BindingAdapter("visibilityEMail")
        fun setVisibility(view: TextView, viewModel: OldOrderViewModel) {

            val visibility = when (view.text.isNullOrEmpty()) {
                true -> View.GONE
                false -> View.VISIBLE
            }
            view.setVisibility(visibility)
        }


        ///NEW ORDER
        @JvmStatic
        @BindingAdapter("visibilityEMail")
        fun setVisibility(view: TextView, vieModel: NewOrderViewModel) {

            val visibility = when (view.text.isNullOrEmpty()) {
                true -> View.GONE
                false -> View.VISIBLE
            }
            view.setVisibility(visibility)
        }

    }

}




