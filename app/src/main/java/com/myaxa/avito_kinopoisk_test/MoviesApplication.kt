package com.myaxa.avito_kinopoisk_test

import android.app.Application
import com.myaxa.avito_kinopoisk_test.di.ApplicationComponent
import com.myaxa.avito_kinopoisk_test.di.DaggerApplicationComponent
import com.myaxa.filters_bottomsheet_api.FiltersBottomSheetApi
import com.myaxa.filters_bottomsheet_api.FiltersBottomSheetApiProvider
import com.myaxa.movie.details.api.MovieDetailsApi
import com.myaxa.movie.details.api.MovieDetailsApiProvider
import com.myaxa.movie.details.api.MovieDetailsDependencies
import com.myaxa.movie.details.api.MovieDetailsDependenciesProvider
import com.myaxa.movie_catalog_api.MovieCatalogApi
import com.myaxa.movie_catalog_api.MovieCatalogApiProvider
import com.myaxa.movie_catalog_api.MovieCatalogDependencies
import com.myaxa.movie_catalog_api.MovieCatalogDependenciesProvider

internal class MoviesApplication :
    Application(),
    MovieCatalogDependenciesProvider,
    MovieDetailsDependenciesProvider,
    MovieCatalogApiProvider,
    MovieDetailsApiProvider,
    FiltersBottomSheetApiProvider {

    private val applicationComponent: ApplicationComponent by lazy { DaggerApplicationComponent.factory().create(this) }

    override fun provideMovieCatalogApi(): MovieCatalogApi {
        return applicationComponent.movieCatalogApi
    }
    override fun provideMovieCatalogDependencies(): MovieCatalogDependencies {
        return applicationComponent
    }

    override fun provideMovieDetailsApi(): MovieDetailsApi {
        return applicationComponent.movieDetailsApi
    }

    override fun provideMovieDetailsDependencies(): MovieDetailsDependencies {
        return applicationComponent
    }

    override fun provideFiltersBottomSheetApi(): FiltersBottomSheetApi {
        return applicationComponent.filtersBottomSheetApi
    }
}