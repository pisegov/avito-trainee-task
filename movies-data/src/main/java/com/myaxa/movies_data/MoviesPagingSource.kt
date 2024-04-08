package com.myaxa.movies_data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myaxa.movies.database.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.IOException

class MoviesPagingSource @AssistedInject constructor(
    @Assisted(QUERY_TAG) private val query: String,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val page = params.key ?: INITIAL_PAGE_NUMBER
        return try {
            val responseResult = remoteDataSource.getMovies(query, page, params.loadSize)

            val list = responseResult.getOrNull()?.let { response ->
                response.movies.map { it.toMovieDBO().toMovie() }
            } ?: emptyList()

            LoadResult.Page(
                list,
                prevKey = (page - 1).takeIf { page > 1 },
                nextKey = (page + 1).takeIf { list.isNotEmpty() },
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(QUERY_TAG) query: String,
        ): MoviesPagingSource
    }

    private companion object {
        const val QUERY_TAG = "query"

        const val INITIAL_PAGE_NUMBER = 1
    }
}