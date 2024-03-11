package com.repair.lomasterapp.clients

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter

class BindingAdapterClient {

    companion object {
        @JvmStatic
        @BindingAdapter("phone2Visible")
        fun setVisiblePhone2(view: EditText, vieModel: NewClientViewModel) {

           // if (vieModel.clientData.value?.phone?.get(1)?.length == 0) {
                view.visibility = View.GONE
          //  } else {
          //      view.visibility = View.VISIBLE
          //  }
        }


        @JvmStatic
        @BindingAdapter("phone3Visible")
        fun setVisiblePhone3(view: EditText, vieModel: NewClientViewModel) {

           // if (vieModel.clientData.value?.phone?.get(2)?.length == 0) {
                view.visibility = View.GONE
           // } else {
           //     view.visibility = View.VISIBLE
           // }
        }

        @JvmStatic
        @BindingAdapter("phone2VisibleOldClient")
        fun setVisibleOldClientPhone2(view: EditText, vieModel: ClientViewModel) {

            if (vieModel.clientData.value?.phone?.get(1) == null
                ||vieModel.clientData.value?.phone?.get(1)?.length == 0) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
            }
        }


        @JvmStatic
        @BindingAdapter("phone3VisibleOldClient")
        fun setVisibleOldClientPhone3(view: EditText, vieModel: ClientViewModel) {

            if (vieModel.clientData.value?.phone?.get(2) == null
                || vieModel.clientData.value?.phone?.get(2)?.length == 0) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
            }
        }



    }
}
