package com.irwan.myfilm.retrofit

import android.util.Size
import com.irwan.myfilm.response.AllFilmResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie?api_key=f7b67d9afdb3c971d4419fa4cb667fbf")
    suspend fun getFilm(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): AllFilmResponse
}