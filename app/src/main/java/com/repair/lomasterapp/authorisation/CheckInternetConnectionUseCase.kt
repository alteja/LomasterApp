package com.repair.lomasterapp.authorisation


object CheckInternetConnectionUseCase {

    fun checkInternetConnection(): Boolean{

//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val netInfo = cm.activeNetworkInfo
//        return if (netInfo != null && netInfo.isConnectedOrConnecting) {
//            true
//        } else false

        return true
    }
}