package com.repair.lomasterapp.clients

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import com.repair.lomasterapp.databinding.ClientFragmentBinding
import com.repair.lomasterapp.databinding.NewClientFragment2Binding
import com.repair.lomasterapp.entity.ClientObj
import com.repair.lomasterapp.entity.UserPhone

object ClientRepositoryImpl: ClientRepository {


    //FUNCTIONS FOR WORKING WITH FRAGMENT NEW CLIENTS
    /////////////////////////////////////////////////

    override fun checkClientInformation(clientObj: ClientObj): Boolean {

        return !(clientObj.name.isNullOrEmpty()
                || clientObj.firstName.isNullOrEmpty()
                || clientObj.secondName.isNullOrEmpty()
               )

    }

    override fun checkClientContacts(clientObj: ClientObj):Boolean {

        return !(clientObj.address.isNullOrEmpty()
                || clientObj.phone.isNullOrEmpty()
                || clientObj.email.isNullOrEmpty()
                )

    }


    fun getClientObjFragment1(
        clientInformation: ClientObj?,
        userPhone: UserPhone
    ): ClientObj {

        var newClient = ClientObj()

        if (clientInformation == null) {
            newClient.phone = arrayOf(userPhone.numberPhone, "", "")
        } else {
            newClient = ClientObj(
                clientInformation.firstName,
                clientInformation.name,
                clientInformation.secondName,
                arrayOf(userPhone.numberPhone, "", ""),
                "",
                "",
                null
            )
        }

        return newClient

    }

    fun getClientPhoneFragment2(binding: NewClientFragment2Binding): UserPhone {

        return UserPhone(binding.etPhone1.toString(), "", "")

    }


    override fun setVisiblePhonesOldClient(binding: ClientFragmentBinding){

        with(binding){

            if(etPhone2Client.isVisible){
                etPhone3Client.isVisible = true
                btnClientAddPhone.isVisible = false
            }else{
                etPhone2Client.isVisible = true
            }

            if (etPhone3Client.isVisible){
                binding.btnClientAddPhone.visibility = View.GONE
            }

        }

    }

    override suspend fun saveClientPhoneToLocalDataStore(userPhone: String, context: Context) {
        //LocalDataStore(context = context).updateClientPhoneData(userPhone)
    }


    //FUNCTIONS FOR WORKING WITH FRAGMENT EXISTING CLIENTS
    /////////////////////////////////////////////////

    fun getExistingClient(){

    }


}