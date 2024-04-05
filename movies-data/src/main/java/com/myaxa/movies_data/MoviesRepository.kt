package com.myaxa.movies_data

import com.myaxa.movies.database.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class MoviesRepository @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource,
) {

    private val networkErrorFlow: MutableSharedFlow<LoadingState> = MutableStateFlow(LoadingState.Started)

    private val localMoviesCatalog = localDataSource.getAll().map { list ->
        return@map when {
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

    suspend fun loadMoviesCatalog() {
        val fromRemote = remoteDataSource.getAll()
        when {
            fromRemote.isFailure -> networkErrorFlow.emit(LoadingState.NetworkError)
            fromRemote.isSuccess -> {
                val list = fromRemote.getOrNull()?.docs ?: emptyList()
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