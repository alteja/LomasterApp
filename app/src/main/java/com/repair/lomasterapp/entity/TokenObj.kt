package com.repair.lomasterapp.entity

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenObj @Inject constructor(){

    var tokenValue: String? = ""
    var tokenError: String? = ""

}