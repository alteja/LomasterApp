package com.repair.lomasterapp.authorisation

import android.content.Context
import com.repair.lomasterapp.LomasterApplication.Companion.apiServiceLomaster
import com.repair.lomasterapp.entity.UserPhone
import com.repair.lomasterapp.pojo.GetToken

object AuthorisationRepositoryImpl : AuthorisationRepository {

     override fun verifyFillingPhoneNumber(

        userPhone: UserPhone
    ): Boolean {

        var isCorrect = true;

        if (userPhone.phonePart1.toString().length == 0) {
            isCorrect = false
        }

        if (userPhone.phonePart2.toString().length == 0) {
            isCorrect = false
        }

        if (userPhone.numberPhone.length < 13) {
            isCorrect = false
        }

        return isCorrect

    }


    suspend fun checkUserInDB(userPhone: UserPhone): Boolean {

        var result = true

        var tokenJson: GetToken

        var token = ""

        try {
            tokenJson = apiServiceLomaster.getToken()
            if (tokenJson.success) {
                token = tokenJson.token
                println("TOKEN $token")
            }
        } catch (e: Exception) {
            println("ERROR: $e")
            println("TOKEN:$token")
            result = false
        }


        if (result) {

            var clientList = apiServiceLomaster.getClient(
                token_value = token,
                user_phone = userPhone.numberPhone
            )
            result = ((clientList.success == true
                    && clientList.data?.size != 0))
        }


        return result

    }


    override fun getUserPhone(phone1: String, phone2: String): UserPhone {

        val number_phone = StringBuilder().also {
            it.append(phone1)
            it.append(phone2)
        }

        var userPhone = UserPhone(
            number_phone.toString(),
            phone1,
            phone2
        )

        return userPhone
    }


    override suspend fun saveClientPhoneToLocalDataStore(userPhone: String, context: Context) {

        // LocalDataStore(context = context).updateClientPhoneData(userPhone)

    }

}





