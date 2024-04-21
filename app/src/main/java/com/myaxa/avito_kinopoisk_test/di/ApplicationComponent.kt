package com.myaxa.avito_kinopoisk_test.di

import android.content.Context
import com.myaxa.filters_bottomsheet_api.FiltersBottomSheetApi
import com.myaxa.filters_bottomsheet_impl.di.FiltersBottomSheetApiModule
import com.myaxa.movie.details.api.MovieDetailsApi
import com.myaxa.movie.details.api.MovieDetailsDependencies
import com.myaxa.movie.details.di.MovieDetailsApiModule
import com.myaxa.movie_catalog_api.MovieCatalogDependencies
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        ApplicationModule::class,
        ViewModelModule::class,
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

    val movieDetailsApi: MovieDetailsApi

    val filtersBottomSheetApi: FiltersBottomSheetApi
}