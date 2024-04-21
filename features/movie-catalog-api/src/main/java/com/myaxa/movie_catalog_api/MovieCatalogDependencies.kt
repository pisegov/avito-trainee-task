package com.myaxa.movie_catalog_api

import androidx.lifecycle.ViewModelProvider
import com.myaxa.movies.common.Navigator

interface MovieCatalogDependencies {
    val viewModelFactory: ViewModelProvider.Factory
    val navigator: Navigator
}

interface MovieCatalogDependenciesProvider {
    fun provideMovieCatalogDependencies(): MovieCatalogDependencies
}
