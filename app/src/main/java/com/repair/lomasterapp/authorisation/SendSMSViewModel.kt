package com.repair.lomasterapp.authorisation

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendSMSViewModel @Inject constructor(): ViewModel() {

    private val _userKod = MutableLiveData<Int>()
    val userKod: LiveData<Int>
        get() = _userKod

    fun checkingSmsKod(kodArgs: Int, kodUser: Int): Boolean{

        return kodArgs == kodUser

    }

    fun afterTextChangedKod(res: Editable) {
        _userKod.value = res.get(0).toString().toInt()
    }


}