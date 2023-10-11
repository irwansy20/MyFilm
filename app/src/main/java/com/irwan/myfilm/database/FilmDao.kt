package com.irwan.myfilm.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.irwan.myfilm.response.ResultsItem

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFilm(film: List<ResultsItem>)

    @Query("SELECT * FROM film ORDER BY popularity DESC")
    fun getAllFilms(): PagingSource<Int, ResultsItem>

    @Query("DELETE FROM film")
    suspend fun deleteAll()
}