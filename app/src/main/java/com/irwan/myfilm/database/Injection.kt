package com.irwan.myfilm.database

import android.content.Context
import com.irwan.myfilm.AppExecutors
import com.irwan.myfilm.retrofit.ApiConfig
import com.irwan.myfilm.database.FilmRepository

object Injection {
    fun provideRepository(context: Context): FilmRepository {
        val apiService = ApiConfig.getApiService()
        val database = FilmDatabase.getInstance(context)
        val dao = database.filmDao()
        val appExecutors = AppExecutors()
        return FilmRepository.getInstance(apiService, dao, appExecutors)
    }
}