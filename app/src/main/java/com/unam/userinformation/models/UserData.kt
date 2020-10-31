package com.unam.userinformation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(
    val name: String? = null,
    val lastName: String? = null,
    val motherLastName: String? = null,
    val sportSelected: String? = null,
    val dateOfBirth: String? = null,
    val zipCode: String? = null
) : Parcelable
