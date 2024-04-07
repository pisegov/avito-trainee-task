package com.myaxa.movies_data

import com.myaxa.movies.database.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class MoviesRepository @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource,
) {
    private val networkMovieIds = MutableStateFlow<List<Long>>(emptyList())
    private val networkErrorFlow: MutableSharedFlow<LoadingState> = MutableStateFlow(LoadingState.Started)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val localMoviesCatalog = networkMovieIds
        .flatMapLatest {
            if (it.isEmpty()) {
                localDataSource.getAll()
            } else {
                localDataSource.getMoviesWithId(it)
            }
        }
        .map { list ->
            when {
                list.isEmpty() -> LoadingState.NoData
                else -> LoadingState.Success(list.map { it.toMovie() })
        }
    }

    val moviesCatalog: Flow<LoadingState> = localMoviesCatalog.combine(networkErrorFlow) { local, network ->
        when {
            local is LoadingState.NoData && network is LoadingState.Started -> network
            local is LoadingState.NoData && network is LoadingState.NetworkError -> local
            local is LoadingState.Success && network is LoadingState.NetworkError -> network
            local is LoadingState.Success && network is LoadingState.Started -> local
            else -> LoadingState.Started
        }
    }

    suspend fun loadMoviesCatalog(query: String = "") {
        val fromRemote = remoteDataSource.getMovies(query)
        when {
            fromRemote.isFailure -> {
                networkErrorFlow.emit(LoadingState.NetworkError)
            }
            fromRemote.isSuccess -> {
                val list = fromRemote.getOrNull()?.docs ?: emptyList()
                networkMovieIds.emit(list.map { it.id })
                localDataSource.insertList(list.map { it.toMovieDBO() })
            }
        }
    }
}

sealed interface LoadingState {
    data class Success(val data: List<Movie>) : LoadingState
    data object NoData : LoadingState
    data object Started : LoadingState
    data object NetworkError : LoadingState
}