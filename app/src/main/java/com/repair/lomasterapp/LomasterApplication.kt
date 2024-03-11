package com.repair.lomasterapp

import android.app.Application
import android.content.Context
import com.repair.lomasterapp.api.ApiFactory
import com.repair.lomasterapp.api.ApiService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LomasterApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        apiServiceLomaster = ApiFactory.apiService

    }

    companion object{

        lateinit  var appContext: Context
        lateinit var apiServiceLomaster: ApiService

    }

}