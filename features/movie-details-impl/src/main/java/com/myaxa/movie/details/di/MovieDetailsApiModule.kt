package com.myaxa.movie.details.di

import com.myaxa.movie.details.api.MovieDetailsApi
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDetailsApiModule {
    @Binds
    internal abstract fun bindMovieDetailsApi(impl: MovieDetailsApiImpl): MovieDetailsApi
}