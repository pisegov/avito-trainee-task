package com.myaxa.movie_catalog_api

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.myaxa.movie_catalog_api.models.MovieInCatalog
import com.myaxa.movie_catalog_impl.filters.Filters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FiltersStateHolder {

    val filtersFlow: StateFlow<Filters?>
    fun updateFilters(filters: Filters?)
}

abstract class MovieCatalogViewModel : FiltersStateHolder, ViewModel() {
    abstract val catalogFlow: Flow<PagingData<MovieInCatalog>>

    abstract fun updateSearchQuery(query: CharSequence)
}