package com.myaxa.movie_catalog_impl

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.myaxa.movie_catalog_impl.filters.Filters
import com.myaxa.movie_catalog_api.MovieCatalogViewModel
import com.myaxa.movie_catalog_api.models.toMovieUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieCatalogViewModelImpl @Inject constructor(
    private val repository: MoviesRepository,
) : MovieCatalogViewModel() {

    init {
        viewModelScope.launch {
            updateFilters(repository.getFilters())
        }
    }

    private val searchQuery = MutableStateFlow("")

    private val _filtersFlow = MutableStateFlow<Filters?>(null)
    override val filtersFlow get() = _filtersFlow.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override val catalogFlow = searchQuery.debounce(1000)
        .combine(filtersFlow, ::newPager)
        .flatMapLatest { pager -> pager.flow.map { pagingData -> pagingData.map { it.toMovieUI() } } }
        .cachedIn(viewModelScope)

    override fun updateSearchQuery(query: CharSequence) {
        searchQuery.update { query.toString() }
    }

    override fun updateFilters(filters: Filters?) {
        filters?.let {
            _filtersFlow.tryEmit(filters)
        }
    }

    private fun newPager(query: String, filters: Filters? = null): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 1,
                initialLoadSize = 10
            ),
        ) {
            repository.queryMovies(query, filters)
        }
    }
}