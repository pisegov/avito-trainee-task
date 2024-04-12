package com.myaxa.data.movie_details

import androidx.paging.PagingSource
import com.myaxa.data.mappers.toMovieDBO
import com.myaxa.domain.movie_details.Actor
import com.myaxa.domain.movie_details.MovieDetails
import com.myaxa.domain.movie_details.MovieDetailsRepository
import com.myaxa.movies.database.datasources.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
    private val actorsPagingSourceFactory: ActorsPagingSource.Factory,
): MovieDetailsRepository{

    override fun getMovieDetailsFlow(id: Long): Flow<MovieDetails?> = localDataSource.getMovie(id).map {
        it?.toMovieDetails()
    }

    override fun getActors(movieId: Long): PagingSource<Int, Actor> =
        actorsPagingSourceFactory.create(movieId)

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