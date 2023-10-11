package com.irwan.myfilm.database

import android.content.Context
import com.irwan.myfilm.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): FilmRepository {
        val database = FilmDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return FilmRepository(database, apiService)
    }
}