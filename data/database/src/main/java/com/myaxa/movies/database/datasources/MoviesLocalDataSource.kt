package com.myaxa.movies.database.datasources

import androidx.room.withTransaction
import com.myaxa.movies.database.MoviesDatabase
import com.myaxa.movies.database.MoviesDatabaseModule
import com.myaxa.movies.database.models.DbRequestFilters
import com.myaxa.movies.database.models.MovieFullDBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MoviesLocalDataSource internal constructor(
    private val database: MoviesDatabase,
) {

    suspend fun getMovieIdListByQueryAndFilters(
        query: String,
        filters: DbRequestFilters,
    ): List<Long> = withContext(Dispatchers.IO) {
        database.withTransaction {
            with(database.moviesFilteringDao) {
                getMovieIdListByQuery(query)
                    .let {
                        if (filters.yearCertain != null) {
                            filterMoviesByCertainYear(filters.yearCertain, it)
                        } else {
                            filterMoviesByYearsInterval(filters.yearFrom, filters.yearTo, it)
                        }
                    }
                    .let { filterMoviesByRating(filters.rating, it) }
                    .let { filterMoviesByContentType(filters.types, it) }
                    .let { filterMoviesByCountry(filters.countries, it) }
                    .let { filterMoviesByNetwork(filters.networks, it) }
                    .let { filterMoviesByGenre(filters.genres, it) }
                    .let { filterMoviesByAgeRating(filters.ageRatings, it) }
            }
        }
    }

    suspend fun getMovieListByIds(ids: List<Long>, page: Int, pageSize: Int) = withContext(Dispatchers.IO) {
        val offset = page * pageSize - pageSize
        database.moviesDao.getMovieListByIds(ids, offset, pageSize)
    }

    suspend fun getPagesCount(pageSize: Int): Int = withContext(Dispatchers.IO) {

        val moviesCount = database.moviesDao.getMoviesCount()
        val reminder = moviesCount % pageSize

        moviesCount / pageSize + if (reminder > 0) 1 else 0
    }

    fun getMovie(id: Long) : Flow<MovieFullDBO?> {
        return database.moviesDao.getMovieById(id)
    }

    suspend fun insertMovie(movieFull: MovieFullDBO) = withContext(Dispatchers.IO) {

        val movie = movieFull.movie

        database.withTransaction {
            val typeId = database.typeDao.upsert(movieFull.type)
            val ageRatingId = movieFull.ageRating?.let { database.ageRatingDao.upsert(it) }
            val networkId = movieFull.network?.let { database.networkDao.upsert(it) }

            val movieDBO = movie.copy(typeId = typeId, ageRatingId = ageRatingId, networkId = networkId)
            database.moviesDao.insertMovie(movieDBO)

            database.genreDao.insertGenresWithMovieId(movieFull.genres, movie.id)
            database.countryDao.insertCountriesWithMovieId(movieFull.countries, movie.id)
        }
    }

    suspend fun insertList(movies: List<MovieFullDBO>) = withContext(Dispatchers.IO) {
        movies.forEach {
            insertMovie(it)
        }
    }
}

fun MoviesLocalDataSource(databaseModule: MoviesDatabaseModule): MoviesLocalDataSource {
    return MoviesLocalDataSource(databaseModule.db)
}