package com.myaxa.movie.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myaxa.domain.movie_details.MovieDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel() {

    private val _movieFlow = MutableStateFlow(0L)
    val movieFlow = _movieFlow.flatMapLatest {
        repository.getMovieDetailsFlow(it)
    }

    fun loadMovie(id: Long) {
        viewModelScope.launch {
            repository.loadMovieDetails(id)
        }
        _movieFlow.tryEmit(id)
    }
}