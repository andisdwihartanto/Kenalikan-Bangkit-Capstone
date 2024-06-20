package com.dicoding.kenalikan.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RentResponse(
    val name: String,
    val description: String,
    val photo: Int
) : Parcelable
