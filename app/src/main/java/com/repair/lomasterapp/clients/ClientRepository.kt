package com.repair.lomasterapp.clients

import android.content.Context
import com.repair.lomasterapp.databinding.ClientFragmentBinding
import com.repair.lomasterapp.entity.ClientObj

interface ClientRepository {

    fun checkClientInformation(client: ClientObj): Boolean

    fun checkClientContacts(client: ClientObj):Boolean

    fun setVisiblePhonesOldClient(binding: ClientFragmentBinding)

    suspend  fun saveClientPhoneToLocalDataStore(userPhone: String, context: Context)

}