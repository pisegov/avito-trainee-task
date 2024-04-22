package com.myaxa.movie_catalog_api

import androidx.fragment.app.Fragment

interface MovieCatalogApiProvider {
    fun provideMovieCatalogApi(): MovieCatalogApi
}

interface MovieCatalogApi {
    fun provideMovieCatalog(): Fragment
}