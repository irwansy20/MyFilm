package com.irwan.myfilm.retrofit

import com.irwan.myfilm.response.AllFilmResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("movie?api_key=f7b67d9afdb3c971d4419fa4cb667fbf")
    fun getFilm(): Call<AllFilmResponse>
}