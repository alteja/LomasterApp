package com.repair.lomasterapp.authorisation

import android.content.Context
import com.repair.lomasterapp.entity.UserPhone


interface AuthorisationRepository {

   fun verifyFillingPhoneNumber(
      userPhone: UserPhone
   ): Boolean

   fun getUserPhone(phone1: String, phone2: String): UserPhone

   suspend  fun saveClientPhoneToLocalDataStore(userPhone: String, context: Context)
}