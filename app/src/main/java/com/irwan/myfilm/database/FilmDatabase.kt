package com.irwan.myfilm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.irwan.myfilm.database.local.RemoteKeys
import com.irwan.myfilm.database.local.RemoteKeysDao
import com.irwan.myfilm.response.ResultsItem

@Database (
    entities = [ResultsItem::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class FilmDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao
    abstract fun remoteKeysDao(): RemoteKeysDao


    companion object{
        @Volatile
        private var INSTANCE: FilmDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FilmDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FilmDatabase::class.java, "Films.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}