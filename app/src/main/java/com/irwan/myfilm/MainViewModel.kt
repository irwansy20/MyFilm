package com.irwan.myfilm

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.irwan.myfilm.database.FilmRepository
import com.irwan.myfilm.database.Injection
import com.irwan.myfilm.response.AllFilmResponse
import com.irwan.myfilm.response.ResultsItem
import com.irwan.myfilm.retrofit.ApiConfig
import kotlinx.coroutines.launch
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class MainViewModel(private val filmRepository: FilmRepository): ViewModel() {
    fun getPop() = filmRepository.getPopFilm()
}