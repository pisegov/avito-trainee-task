package com.myaxa.data.movie_details

import com.myaxa.data.movie_details_remote.MovieDetailsRemoteDataSource
import com.myaxa.data.movie_details_remote.models.MovieDetailsDTO
import com.myaxa.domain.movie_details.MovieDetails
import com.myaxa.domain.movie_details.MovieDetailsRepository
import com.myaxa.movie_details_local.MovieDetailsLocalDataSource
import com.myaxa.movies.database.models.MovieDBO
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieDetailsRemoteDataSource,
    private val localDataSource: MovieDetailsLocalDataSource,
): MovieDetailsRepository{

    override fun getMovieDetailsFlow(id: Long): Flow<MovieDetails?> = localDataSource.getMovie(id).map {
        it?.toMovieDetails()
    }

    override suspend fun loadMovieDetails(id: Long) {
        val result = remoteDataSource.getMovieDetails(id)
        when {
            result.isSuccess -> {
                val movie = result.getOrThrow()
                localDataSource.insertMovie(movie.toMovieDBO())
            }
        }
    }
}

private fun MovieDetailsDTO.toMovieDBO(): MovieDBO {
    return MovieDBO(
        id = id,
        name = name,
        type = type,
        typeNumber = typeNumber,
        year = year,
        isSeries = isSeries,
    )
}

private fun MovieDBO.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        name = name,
    )
}
