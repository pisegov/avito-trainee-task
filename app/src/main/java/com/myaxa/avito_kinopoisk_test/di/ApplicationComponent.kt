package com.myaxa.avito_kinopoisk_test.di

import android.content.Context
import com.myaxa.filters_bottomsheet_api.FiltersBottomSheetApi
import com.myaxa.filters_bottomsheet_impl.di.FiltersBottomSheetApiModule
import com.myaxa.movie.details.api.MovieDetailsApi
import com.myaxa.movie.details.api.MovieDetailsDependencies
import com.myaxa.movie.details.di.MovieDetailsApiModule
import com.myaxa.movie_catalog_api.MovieCatalogApi
import com.myaxa.movie_catalog_api.MovieCatalogDependencies
import com.myaxa.movie_catalog_impl.di.MovieCatalogApiModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        ApplicationModule::class,
        ViewModelModule::class,
        MovieCatalogApiModule::class,
        MovieDetailsApiModule::class,
        FiltersBottomSheetApiModule::class
    ]
)
@ApplicationScope
internal interface ApplicationComponent :
    MovieCatalogDependencies,
    MovieDetailsDependencies {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
        ): ApplicationComponent
    }

    val movieCatalogApi: MovieCatalogApi

    val movieDetailsApi: MovieDetailsApi

    val filtersBottomSheetApi: FiltersBottomSheetApi
}