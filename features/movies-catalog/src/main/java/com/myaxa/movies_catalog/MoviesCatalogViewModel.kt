package com.myaxa.movies_catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.movies_catalog.models.MovieInCatalog
import com.myaxa.movies_catalog.util.toScreenState
import com.myaxa.movies_data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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
            repository.loadMoviesCatalog()
        }
    }

    val state = repository.moviesCatalog.map { loadingState ->
        loadingState.toScreenState()
    }.stateIn(viewModelScope, SharingStarted.Lazily, ScreenState.None)

    private val _navigateToDetails = MutableStateFlow<Long?>(null)
    val navigateToDetails = _navigateToDetails.asStateFlow()

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

