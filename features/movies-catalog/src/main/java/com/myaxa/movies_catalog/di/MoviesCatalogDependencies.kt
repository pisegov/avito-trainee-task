package com.myaxa.movies_catalog.di

import androidx.lifecycle.ViewModelProvider
import com.myaxa.movies_data.MoviesRepository

interface MoviesCatalogDependencies {
    val viewModelFactory : ViewModelProvider.Factory
}

interface MoviesCatalogDependenciesProvider {
    fun provideDependencies(): MoviesCatalogDependencies
}
