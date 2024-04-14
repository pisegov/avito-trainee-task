package com.myaxa.data.movie_details

import androidx.paging.PagingSource
import com.myaxa.data.actors_remote.MovieDetailsInfoDataSource
import com.myaxa.data.mappers.toMovieRemoteDBO
import com.myaxa.domain.movie_details.DetailsInfoModel
import com.myaxa.domain.movie_details.MovieDetails
import com.myaxa.domain.movie_details.MovieDetailsRepository
import com.myaxa.movies.database.datasources.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieDetailsRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val detailsRemoteDataSource: MovieDetailsInfoDataSource,
    private val localDataSource: MoviesLocalDataSource,
) : MovieDetailsRepository {

    override fun getMovieDetailsFlow(id: Long): Flow<MovieDetails?> {
        return localDataSource.getMovie(id).map {
            it?.toMovieDetails()
        }
    }

    override fun <T : DetailsInfoModel> getInfo(movieId: Long, type: Class<T>): PagingSource<Int, T> {
        return MovieDetailsInfoPagingSource(movieId, type, detailsRemoteDataSource)
    }

    override suspend fun loadMovieDetails(id: Long) {
        val result = moviesRemoteDataSource.getMovieById(id)
        when {
            result.isSuccess -> {
                val movie = result.getOrThrow()
                localDataSource.insertMovie(movie.toMovieRemoteDBO())
            }
        }
    }
}