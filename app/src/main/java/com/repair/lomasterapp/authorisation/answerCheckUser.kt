package com.repair.lomasterapp.authorisation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class answerCheckUser: Parcelable {

    INCORRECT_FILLING, ABSENT_IN_DB, ATTEND_IN_DB, ERROR_LOGIN

}