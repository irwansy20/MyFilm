package com.irwan.myfilm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irwan.myfilm.response.AllFilmResponse
import com.irwan.myfilm.response.ResultsItem
import com.irwan.myfilm.retrofit.ApiConfig
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _listFilm = MutableLiveData<List<ResultsItem>>()
    val listFilm: LiveData<List<ResultsItem>> = _listFilm

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findFilm(){
       _isLoading.value = true
       val client = ApiConfig.getApiService().getFilm()
       client.enqueue(object : retrofit2.Callback<AllFilmResponse>{
           override fun onResponse(
               call: Call<AllFilmResponse>,
               response: Response<AllFilmResponse>
           ) {
               _isLoading.value = false
               if (response.isSuccessful){
                   val listFilm = response.body()
                   val film = listFilm?.results
                   _listFilm.value = film
               } else {
                   Log.e(TAG, "onFailure List: ${response.message()}")
               }
           }

           override fun onFailure(call: Call<AllFilmResponse>, t: Throwable) {
               _isLoading.value = false
               Log.e(TAG, "onFailure: ${t.message}")
           }
       })
    }

    companion object{
        private const val TAG = "MainActiivity"
    }
}