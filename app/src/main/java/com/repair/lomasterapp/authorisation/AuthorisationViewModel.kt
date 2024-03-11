package com.repair.lomasterapp.authorisation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.repair.lomasterapp.LomasterApplication.Companion.appContext
import com.repair.lomasterapp.authorisation.AuthorisationRepositoryImpl.checkUserInDB
import com.repair.lomasterapp.authorisation.AuthorisationRepositoryImpl.getUserPhone
import com.repair.lomasterapp.authorisation.AuthorisationRepositoryImpl.verifyFillingPhoneNumber
import com.repair.lomasterapp.databinding.AutorisationFragmentBinding
import com.repair.lomasterapp.entity.UserPhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class AuthorisationViewModel @Inject constructor(): ViewModel() {

    private val repository_checking: CheckInternetConnectionUseCase = CheckInternetConnectionUseCase

    private val _userPhone = MutableLiveData<UserPhone>()
    val userPhone: LiveData<UserPhone>
        get() = _userPhone


    private val _userInDB = MutableLiveData<Boolean>()
    val userInDB: LiveData<Boolean>
        get() = _userInDB

    private val MY_PERMISSIONS_REQUEST_SEND_SMS = 1
    private val MY_PERMISSIONS_REQUEST_RECIEVE_SMS = 11

    suspend fun checkUsersPhone(phone1: String, phone2: String): answerCheckUser {

        val userPhone = getUserPhone(phone1, phone2)

        var correctNumberPhone = verifyFillingPhoneNumber(userPhone)

        if (!correctNumberPhone) {
            Toast.makeText(
                appContext,
                appContext.getString(com.repair.lomasterapp.R.string.error_filling_phone),
                Toast.LENGTH_LONG
            ).show()
            return answerCheckUser.INCORRECT_FILLING
        }

        var success = CoroutineScope(Dispatchers.IO).async {
            return@async checkUserInDB(userPhone)
            //println("RESULT $result")
            //_userInDB.postValue(result)
        }.await()

        if (success) {
           // saveClientPhoneToLocalDataStore(userPhone.numberPhone, context)
            return answerCheckUser.ATTEND_IN_DB
        } else {
            return answerCheckUser.ABSENT_IN_DB
        }

    }

    private fun createSMSKod(): Int{

        var randomNumber = 0

        for (i in 1..4){

            randomNumber = randomNumber*10 + rand(1, 9)

        }

        return randomNumber
    }

     fun sendSMS(): HashMap<Boolean, Int> {

         var result = HashMap<Boolean, Int>()

         var succsess = true

         val kodSMS = createSMSKod()
         val sms = appContext.getString(com.repair.lomasterapp.R.string.send_sms_body) + kodSMS

         val smsManager =
             SmsManager.getSmsManagerForSubscriptionId(SmsManager.getDefaultSmsSubscriptionId())

         try {
             smsManager.sendTextMessage(userPhone.value?.numberPhone, null, sms, null, null);
             Toast.makeText(
                 appContext,
                 appContext.getString(com.repair.lomasterapp.R.string.succsessfull_sms), Toast.LENGTH_LONG
             ).show()
         } catch (e: Exception) {
             succsess = false
             Toast.makeText(
                 appContext,
                 appContext.getString(com.repair.lomasterapp.R.string.not_succsessfull_sms), Toast.LENGTH_LONG
             ).show()
         }

         result.put(succsess, kodSMS)

         return result
    }



    private fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
    }


    fun checkForSmsPermission(activity: Activity) {

        if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.SEND_SMS
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {

            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.SEND_SMS),
                MY_PERMISSIONS_REQUEST_SEND_SMS
            )
        } else {
            // Permission already granted. Enable the SMS button.

        }

        if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.RECEIVE_SMS
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {

            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.RECEIVE_SMS),
                MY_PERMISSIONS_REQUEST_RECIEVE_SMS
            )
        } else {
            // Permission already granted. Enable the SMS button.

        }
    }

    fun checkInternetConnection(): Boolean{

        return repository_checking.checkInternetConnection(appContext)

    }


    fun setUserPhoneLiveData(binding: AutorisationFragmentBinding){
        _userPhone.value = getUserPhone(binding.PhoneAuthor1.text.toString(),
                                        binding.PhoneAuthor2.text.toString())
    }

    private suspend fun saveClientPhoneToLocalDataStore(numberPhone: String,context: Context){
       saveClientPhoneToLocalDataStore(numberPhone, context)
   }

    fun onTextChangedPhone1(s:CharSequence, start:Int, before:Int, count: Int){
        _userPhone.value?.phonePart1 = s.toString()
    }

    fun onTextChangedPhone2(s: CharSequence, start: Int, before: Int, count: Int) {
        _userPhone.value?.phonePart2 = s.toString()
    }


}