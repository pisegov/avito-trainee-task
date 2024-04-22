package com.myaxa.movie_catalog_impl.di

import androidx.fragment.app.Fragment
import com.myaxa.movie_catalog_api.MovieCatalogApi
import com.myaxa.movie_catalog_impl.ui.MovieCatalogFragment
import javax.inject.Inject

internal class MovieCatalogApiImpl @Inject constructor() : MovieCatalogApi {
    override fun provideMovieCatalog(): Fragment {
        return MovieCatalogFragment()
    }
}