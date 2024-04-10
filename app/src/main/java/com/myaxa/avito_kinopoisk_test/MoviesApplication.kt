package com.myaxa.avito_kinopoisk_test

import android.app.Application
import android.content.Context
import com.myaxa.avito_kinopoisk_test.di.ApplicationComponent
import com.myaxa.avito_kinopoisk_test.di.DaggerApplicationComponent
import com.myaxa.movie.details.api.MovieDetailsApi
import com.myaxa.movie.details.api.MovieDetailsApiProvider
import com.myaxa.movie.details.api.MovieDetailsDependencies
import com.myaxa.movie.details.api.MovieDetailsDependenciesProvider
import com.myaxa.movies_catalog.di.MoviesCatalogDependencies
import com.myaxa.movies_catalog.di.MoviesCatalogDependenciesProvider

internal class MoviesApplication :
    Application(),
    MoviesCatalogDependenciesProvider,
    MovieDetailsDependenciesProvider,
    MovieDetailsApiProvider {

    private val applicationComponent: ApplicationComponent by lazy { DaggerApplicationComponent.factory().create(this) }

    override fun provideMovieCatalogDependencies(): MoviesCatalogDependencies {
        return applicationComponent
    }

    override fun provideMovieDetailsApi(): MovieDetailsApi {
        return applicationComponent.movieDetailsApi
    }

    override fun provideMovieDetailsDependencies(): MovieDetailsDependencies {
        return applicationComponent
    }
}