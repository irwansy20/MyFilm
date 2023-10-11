package com.irwan.myfilm

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
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

    val film: LiveData<PagingData<ResultsItem>> =
        filmRepository.getFilm().cachedIn(viewModelScope)
    }

    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(Injection.provideRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }