package com.dicoding.kenalikan.di

import android.content.Context
import com.dicoding.kenalikan.data.UserRepository
import com.dicoding.kenalikan.data.preference.UserPreference
import com.dicoding.kenalikan.data.preference.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}