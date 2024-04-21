package com.myaxa.movie_catalog_api

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.myaxa.movie_catalog_impl.filters.Filters
import com.myaxa.movie_catalog_api.models.MovieInCatalog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class MovieCatalogViewModel : ViewModel() {
    abstract val catalogFlow: Flow<PagingData<MovieInCatalog>>

    abstract val filtersFlow: StateFlow<Filters?>

    abstract fun updateSearchQuery(query: CharSequence)

    abstract fun updateFilters(filters: Filters?)
}