package com.myaxa.avito_kinopoisk_test.di

import android.content.Context
import com.myaxa.movies_catalog.di.MoviesCatalogDependencies
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [ApplicationModule::class, ViewModelModule::class]
)
@ApplicationScope
internal interface ApplicationComponent : MoviesCatalogDependencies {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
        ): ApplicationComponent
    }
}