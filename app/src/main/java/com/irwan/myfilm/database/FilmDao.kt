package com.irwan.myfilm.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.irwan.myfilm.response.AllFilmResponse
import com.irwan.myfilm.response.ResultsItem

@Dao
interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(films: List<FilmEntity>)

    @Query("SELECT * FROM films ORDER BY popularity DESC")
    fun getAllFilms(): LiveData<List<FilmEntity>>


}