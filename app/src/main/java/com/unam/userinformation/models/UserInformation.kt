package com.unam.userinformation.models

import android.icu.util.ChineseCalendar
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInformation(
    val fullName: String? = null,
    val age: String? = null,
    val rfc: String? = null,
    val zodiac: String? = null,
    val chineseZodiac: String? = null,
    val favoriteSport: String? = null,
    val zipCode: String? = null
) : Parcelable