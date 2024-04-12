package com.myaxa.movie.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.myaxa.domain.movie_details.Actor
import com.myaxa.domain.movie_details.MovieDetailsRepository
import com.myaxa.movie.details.actors.ActorUI
import com.myaxa.movie.details.actors.toActorUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel() {

    private val _movieFlow = MutableStateFlow(0L)
    internal val movieFlow = _movieFlow.flatMapLatest {
        repository.getMovieDetailsFlow(it)
    }.map {
        it?.toMovieDetailsUI()
    }

    val actorsFlow: Flow<PagingData<ActorUI>?> = _movieFlow
        .map { id ->
            newPager(id)
        }
        .flatMapLatest { pager ->
            pager.flow.map { pagingData -> pagingData.map { it.toActorUI() } }
        }
        .cachedIn(viewModelScope)

    internal fun loadMovie(id: Long) {
        viewModelScope.launch {
            repository.loadMovieDetails(id)
        }
        _movieFlow.tryEmit(id)
    }

    private fun newPager(id: Long): Pager<Int, Actor> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 1,
                initialLoadSize = 10
            )
        ) {
            repository.getInfo(id, Actor::class.java)
        }
    }
}