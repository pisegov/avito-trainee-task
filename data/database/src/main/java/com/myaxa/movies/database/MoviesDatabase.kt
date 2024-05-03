package com.myaxa.movies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myaxa.movies.database.dao.ActorDao
import com.myaxa.movies.database.dao.AgeRatingDao
import com.myaxa.movies.database.dao.CountryDao
import com.myaxa.movies.database.dao.EpisodeDao
import com.myaxa.movies.database.dao.GenreDao
import com.myaxa.movies.database.dao.ImageDao
import com.myaxa.movies.database.dao.MoviesDao
import com.myaxa.movies.database.dao.MoviesFilteringDao
import com.myaxa.movies.database.dao.NetworkDao
import com.myaxa.movies.database.dao.ReviewDao
import com.myaxa.movies.database.dao.TypeDao
import com.myaxa.movies.database.models.ActorDBO
import com.myaxa.movies.database.models.AgeRatingDBO
import com.myaxa.movies.database.models.CountryDBO
import com.myaxa.movies.database.models.EpisodeDBO
import com.myaxa.movies.database.models.GenreDBO
import com.myaxa.movies.database.models.ImageDBO
import com.myaxa.movies.database.models.MovieActorCrossRef
import com.myaxa.movies.database.models.MovieCountryCrossRef
import com.myaxa.movies.database.models.MovieDBO
import com.myaxa.movies.database.models.MovieGenreCrossRef
import com.myaxa.movies.database.models.NetworkDBO
import com.myaxa.movies.database.models.ReviewDBO
import com.myaxa.movies.database.models.TypeDBO

@Database(
    entities = [
        MovieDBO::class,
        ActorDBO::class,
        AgeRatingDBO::class,
        CountryDBO::class,
        EpisodeDBO::class,
        GenreDBO::class,
        ImageDBO::class,
        NetworkDBO::class,
        ReviewDBO::class,
        TypeDBO::class,
        MovieActorCrossRef::class,
        MovieCountryCrossRef::class,
        MovieGenreCrossRef::class,
    ], version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
    abstract val moviesFilteringDao: MoviesFilteringDao
    abstract val ageRatingDao: AgeRatingDao
    abstract val networkDao: NetworkDao
    abstract val typeDao: TypeDao
    abstract val genreDao: GenreDao
    abstract val countryDao: CountryDao
    abstract val actorDao: ActorDao
    abstract val reviewDao: ReviewDao
    abstract val imageDao: ImageDao
    abstract val episodeDao: EpisodeDao
}

class MoviesDatabaseModule(applicationContext: Context) {
    val db = Room.databaseBuilder(
        applicationContext.applicationContext,
        MoviesDatabase::class.java,
        "movies"
    ).build()
}