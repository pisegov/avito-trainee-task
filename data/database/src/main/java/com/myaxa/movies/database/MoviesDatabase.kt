package com.myaxa.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myaxa.movies.database.dao.MoviesDao
import com.myaxa.movies.database.models.MovieDBO

@Database(entities = [MovieDBO::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val dao: MoviesDao
}

class MoviesDatabaseModule(applicationContext: Context) {
    val db = Room.databaseBuilder(
        applicationContext.applicationContext,
        MoviesDatabase::class.java,
        "movies"
    ).build()
}