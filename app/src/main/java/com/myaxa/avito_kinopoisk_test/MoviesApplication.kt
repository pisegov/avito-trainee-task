package com.myaxa.avito_kinopoisk_test

import android.app.Application
import android.content.Context
import com.myaxa.avito_kinopoisk_test.di.ApplicationComponent
import com.myaxa.avito_kinopoisk_test.di.DaggerApplicationComponent

class MoviesApplication : Application() {
    private val applicationComponent: ApplicationComponent by lazy { DaggerApplicationComponent.factory().create(this) }

    companion object {
        fun applicationComponent(context: Context): ApplicationComponent {
            return (context.applicationContext as MoviesApplication).applicationComponent
        }
    }
}