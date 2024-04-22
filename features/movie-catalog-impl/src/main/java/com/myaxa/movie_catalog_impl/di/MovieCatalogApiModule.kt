package com.myaxa.movie_catalog_impl.di

import com.myaxa.movie_catalog_api.MovieCatalogApi
import dagger.Binds
import dagger.Module

@Module
abstract class MovieCatalogApiModule {
    @Binds
    internal abstract fun bindMovieCatalogApi(impl: MovieCatalogApiImpl): MovieCatalogApi
}