package com.myaxa.data.movie_details

import com.myaxa.domain.movie_details.MovieDetails
import com.myaxa.domain.movie_details.MovieDetailsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class MovieDetailsRepositoryImpl @Inject constructor(): MovieDetailsRepository{
    override val movieDetailsFlow: Flow<MovieDetails>
        get() = TODO("Not yet implemented")

    override suspend fun loadMovieDetails() {
        TODO("Not yet implemented")
    }
}