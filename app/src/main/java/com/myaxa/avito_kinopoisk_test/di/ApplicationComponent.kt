package com.myaxa.avito_kinopoisk_test.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [ApplicationModule::class, ViewModelModule::class]
)
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
        ): ApplicationComponent
    }
}