package com.myaxa.avito_kinopoisk_test.di

import android.content.Context
import com.myaxa.movie.details.api.MovieDetailsApi
import com.myaxa.movie.details.di.MovieDetailsApiModule
import com.myaxa.movies_catalog.di.MoviesCatalogDependencies
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        ApplicationModule::class,
        ViewModelModule::class,
        MovieDetailsApiModule::class
    ]
)
@ApplicationScope
internal interface ApplicationComponent : MoviesCatalogDependencies {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
        ): ApplicationComponent
    }

    val movieDetailsApi: MovieDetailsApi
}