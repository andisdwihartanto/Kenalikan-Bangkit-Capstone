package com.dicoding.kenalikan.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rent (
    val name: String,
    val description: String,
    val photo: Int
) : Parcelable