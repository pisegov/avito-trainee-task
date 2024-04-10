package com.myaxa.avito_kinopoisk_test.di

import android.content.Context
import com.myaxa.avito_kinopoisk_test.BuildConfig
import com.myaxa.movies.common.Navigator
import com.myaxa.avito_kinopoisk_test.R
import com.myaxa.data.movie_details.MovieDetailsRepositoryImpl
import com.myaxa.domain.movie_details.MovieDetailsRepository
import com.myaxa.movies.database.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import dagger.Binds
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

        @Provides
        @ApplicationScope
        fun provideNavigator(): Navigator {
            return Navigator(R.id.main)
        }
    }

    @Binds
    fun provideMovieDetailsRepository(impl: MovieDetailsRepositoryImpl): MovieDetailsRepository
}

@Scope
annotation class ApplicationScope