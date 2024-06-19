package com.dicoding.kenalikan.retrofit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RentResponse(
    val name: String,
    val description: String,
    val photo: Int
) : Parcelable
