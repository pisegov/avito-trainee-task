package com.myaxa.avito_kinopoisk_test.di

import android.content.Context
import com.myaxa.avito_kinopoisk_test.BuildConfig
import com.myaxa.movies.common.Navigator
import com.myaxa.avito_kinopoisk_test.R
import com.myaxa.data.actors_remote.MovieDetailsInfoDataSource
import com.myaxa.data.movie_details.MovieDetailsRepositoryImpl
import com.myaxa.domain.movie_details.MovieDetailsRepository
import com.myaxa.movies.database.MoviesDatabaseModule
import com.myaxa.movies.database.datasources.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import com.myaxa.movie_catalog_impl.MoviesRepository
import com.myaxa.movies.database.datasources.MovieDetailsInfoLocalDataSource
import com.myaxa.movies_data.MoviesRepositoryImpl
import com.myaxa.network.RetrofitModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
internal interface ApplicationModule {
    companion object {

        @Provides
        @ApplicationScope
        fun provideRetrofitModule(): RetrofitModule {
            return RetrofitModule(
                BuildConfig.BASE_URL,
                BuildConfig.API_KEY,
            )
        }

        @Provides
        @ApplicationScope
        fun provideDatabaseModule(applicationContext: Context): MoviesDatabaseModule {
            return MoviesDatabaseModule(applicationContext)
        }

        @Provides
        fun provideMoviesRemoteDataSource(retrofitModule: RetrofitModule): MoviesRemoteDataSource {
            return MoviesRemoteDataSource(retrofitModule)
        }

        @Provides
        fun provideMoviesLocalDataSource(databaseModule: MoviesDatabaseModule): MoviesLocalDataSource {
            return MoviesLocalDataSource(databaseModule)
        }

        @Provides
        fun provideMovieDetailsInfoRemoteDataSource(retrofitModule: RetrofitModule): MovieDetailsInfoDataSource {
            return MovieDetailsInfoDataSource(retrofitModule)
        }

        @Provides
        fun provideMovieDetailsInfoLocalDataSource(databaseModule: MoviesDatabaseModule): MovieDetailsInfoLocalDataSource {
            return MovieDetailsInfoLocalDataSource(databaseModule)
        }

        @Provides
        fun provideNavigator(): Navigator {
            return Navigator(R.id.main)
        }
    }

    @Binds
    fun provideMovieDetailsRepository(impl: MovieDetailsRepositoryImpl): MovieDetailsRepository

    @Binds
    fun provideMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository
}

@Scope
annotation class ApplicationScope