package com.myaxa.movie.details.api

import androidx.lifecycle.ViewModelProvider

interface MovieDetailsDependencies {
    val viewModelFactory : ViewModelProvider.Factory
}

interface MovieDetailsDependenciesProvider {
    fun provideMovieDetailsDependencies() : MovieDetailsDependencies
}