package com.irwan.myfilm.database

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.irwan.myfilm.response.ResultsItem
import com.irwan.myfilm.retrofit.ApiService

class FilmRepository(
    private val filmDatabase: FilmDatabase,
    private val apiService:ApiService
){
    fun getFilm(): LiveData<PagingData<ResultsItem>>{
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 1
            ),
            remoteMediator = FilmRemoteMediator(filmDatabase, apiService),
            pagingSourceFactory = {
//                FilmPagingSource(apiService)
                filmDatabase.filmDao().getAllFilms()
            }
        ).liveData
    }
}