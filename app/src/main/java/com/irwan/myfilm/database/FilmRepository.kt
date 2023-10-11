package com.irwan.myfilm.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.irwan.myfilm.AppExecutors
import com.irwan.myfilm.response.AllFilmResponse
import com.irwan.myfilm.response.ResultsItem
import com.irwan.myfilm.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FilmRepository private constructor(
    private val apiService:ApiService,
    private val filmDao: FilmDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<FilmEntity>>>()

    fun getPopFilm(): LiveData<Result<List<FilmEntity>>>{
        result.value = Result.Loading
        val client = apiService.getFilm()
        client.enqueue(object : Callback<AllFilmResponse>{
           override fun onResponse(
               call: Call<AllFilmResponse>,
               response: Response<AllFilmResponse>
           ) {
               if (response.isSuccessful){
                   val listFilm = response.body()?.results
                   val newList = ArrayList<FilmEntity>()
                   appExecutors.diskIO.execute {
                       listFilm?.forEach { result ->
                           val films = FilmEntity(
                               result.title,
                               result.releaseDate,
                               result.popularity
                           )
                           newList.add(films)
                       }
                       filmDao.insertNews(newList)
                   }
               }
           }

            override fun onFailure(call: Call<AllFilmResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = filmDao.getAllFilms()
        result.addSource(localData) { newData: List<FilmEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: FilmRepository? = null
        fun getInstance(
            apiService:ApiService,
            filmDao: FilmDao,
            appExecutors: AppExecutors
        ): FilmRepository =
            instance ?: synchronized(this) {
                instance ?: FilmRepository(apiService, filmDao, appExecutors)
            }.also { instance = it }
    }
}