package com.unam.userinformation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInformation(
    val fullName: String? = null,
    val age: String? = null,
    val rfc: String? = null,
    val zodiac: String? = null,
    val chineseZodiac: List<String?>,
    val favoriteSport: String? = null,
    val zipCode: String? = null
) : Parcelable