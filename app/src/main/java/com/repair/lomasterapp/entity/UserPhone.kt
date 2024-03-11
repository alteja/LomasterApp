package com.repair.lomasterapp.entity

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class UserPhone @Inject constructor(

    val numberPhone: String,
    var phonePart1: String? = null,
    var phonePart2: String? = null


) {
    override fun toString(): String {
        return phonePart1.toString() +
               phonePart2.toString()
    }
}
