package com.myaxa.movies_catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.myaxa.movies_catalog.util.toMovieUI
import com.myaxa.movies_data.Filters
import com.myaxa.movies_data.Movie
import com.myaxa.movies_data.MoviesRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesCatalogViewModel @Inject constructor(
    private val repository: MoviesRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            updateFilters(repository.getFilters())
        }
    }

    private val searchQuery = MutableStateFlow("")

    private val _filtersFlow = MutableStateFlow<Filters?>(null)
    val filtersFlow get() = _filtersFlow.asStateFlow()

    @OptIn(FlowPreview::class)
    val catalogFlow = searchQuery.debounce(1000)
        .combine(filtersFlow, ::newPager)
        .flatMapLatest { pager -> pager.flow.map { pagingData -> pagingData.map { it.toMovieUI() } } }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val _navigateToDetails = MutableStateFlow<Long?>(null)
    val navigateToDetails = _navigateToDetails.asStateFlow()

    fun updateSearchQuery(query: CharSequence) {
        searchQuery.update { query.toString() }
    }

    fun updateFilters(filters: Filters?) {
        filters?.let {
            _filtersFlow.tryEmit(filters)
        }
    }

    fun onMovieClicked(id: Long) {
        _navigateToDetails.tryEmit(id)
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