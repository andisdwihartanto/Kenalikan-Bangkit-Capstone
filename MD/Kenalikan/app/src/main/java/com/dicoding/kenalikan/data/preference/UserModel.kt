package com.dicoding.kenalikan.data.preference

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
