package com.myaxa.data.movie_details

import com.myaxa.data.mappers.toMovieDBO
import com.myaxa.domain.movie_details.MovieDetails
import com.myaxa.domain.movie_details.MovieDetailsRepository
import com.myaxa.movies.database.datasources.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
): MovieDetailsRepository{

    override fun getMovieDetailsFlow(id: Long): Flow<MovieDetails?> = localDataSource.getMovie(id).map {
        it?.toMovieDetails()
    }

    override suspend fun loadMovieDetails(id: Long) {
        val result = remoteDataSource.getMovieById(id)
        when {
            result.isSuccess -> {
                val movie = result.getOrThrow()
                localDataSource.insertMovie(movie.toMovieDBO())
            }
        }
    }
}