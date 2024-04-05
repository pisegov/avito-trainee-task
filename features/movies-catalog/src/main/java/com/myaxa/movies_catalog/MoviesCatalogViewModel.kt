package com.myaxa.movies_catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.movies_catalog.models.MovieInCatalog
import com.myaxa.movies_catalog.util.toScreenState
import com.myaxa.movies_data.MoviesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MoviesCatalogViewModel(
    private val repository: MoviesRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.loadMoviesCatalog()
        }
    }

    val state = repository.moviesCatalog.map { loadingState ->
        loadingState.toScreenState()
    }.stateIn(
        viewModelScope, SharingStarted.Lazily, ScreenState.None
    )
}

sealed interface ScreenState {
    data class Success(val movies: List<MovieInCatalog>) : ScreenState
    data object Loading : ScreenState
    data object NoDataError : ScreenState
    data object NetworkError : ScreenState
    data object None : ScreenState
}

