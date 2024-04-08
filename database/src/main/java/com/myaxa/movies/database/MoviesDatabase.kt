package com.myaxa.movies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myaxa.movies.database.dao.MoviesDao
import com.myaxa.movies.database.models.MovieDBO

@Database(entities = [MovieDBO::class], version = 1)
internal abstract class MoviesDatabase : RoomDatabase() {
    abstract val dao: MoviesDao
}

