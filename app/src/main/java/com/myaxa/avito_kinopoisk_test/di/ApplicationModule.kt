package com.myaxa.avito_kinopoisk_test.di

import android.content.Context
import com.myaxa.avito_kinopoisk_test.BuildConfig
import com.myaxa.movies.database.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
internal interface ApplicationModule {
    companion object {
        @Provides
        @ApplicationScope
        fun provideMoviesLocalDataSource(applicationContext: Context): MoviesLocalDataSource {
            return MoviesLocalDataSource(applicationContext)
        }

        @Provides
        @ApplicationScope
        fun provideMoviesRemoteDataSource(): MoviesRemoteDataSource {
            return MoviesRemoteDataSource(
                BuildConfig.BASE_URL,
                BuildConfig.API_KEY
            )
        }
    }
}

@Scope
annotation class ApplicationScope