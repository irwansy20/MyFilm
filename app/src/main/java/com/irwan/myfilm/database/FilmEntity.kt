package com.irwan.myfilm.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

@Entity (tableName = "films")
class FilmEntity(
    @field:ColumnInfo("title")
    @field:PrimaryKey
    val title: String,

    @field:ColumnInfo("release_date")
    val releaseDate: String,

    @field:ColumnInfo("popularity")
    val popularity: Double,
)
