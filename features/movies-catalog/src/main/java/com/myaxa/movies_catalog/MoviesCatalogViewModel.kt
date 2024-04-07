package com.myaxa.movies_catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.movies_catalog.models.MovieInCatalog
import com.myaxa.movies_catalog.util.toScreenState
import com.myaxa.movies_data.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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

    private val searchQuery = MutableStateFlow<CharSequence>("")

    init {
        viewModelScope.launch {
            repository.loadMoviesCatalog()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val state = searchQuery
        .debounce(1000)
        .flatMapLatest {
            repository.loadMoviesCatalog(it.toString())
            repository.moviesCatalog.map { loadingState ->
                loadingState.toScreenState()
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, ScreenState.None)

    private val _navigateToDetails = MutableStateFlow<Long?>(null)
    val navigateToDetails = _navigateToDetails.asStateFlow()

    fun updateSearchQuery(query: CharSequence) {
        searchQuery.update { query }
    }

    fun onMovieClicked(id: Long) {
        _navigateToDetails.update { id }
    }
}

sealed interface ScreenState {
    data class Success(val movies: List<MovieInCatalog>) : ScreenState
    data object Loading : ScreenState
    data object NoDataError : ScreenState
    data object NetworkError : ScreenState
    data object None : ScreenState
}

