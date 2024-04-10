package com.myaxa.movies_catalog.di

import androidx.lifecycle.ViewModelProvider
import com.myaxa.movies.common.Navigator

interface MoviesCatalogDependencies {
    val viewModelFactory : ViewModelProvider.Factory
    val navigator: Navigator
}

interface MoviesCatalogDependenciesProvider {
    fun provideMovieCatalogDependencies(): MoviesCatalogDependencies
}
