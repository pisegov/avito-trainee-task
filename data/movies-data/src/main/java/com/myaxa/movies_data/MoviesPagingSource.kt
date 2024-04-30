package com.myaxa.movies_data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myaxa.data.mappers.toMovie
import com.myaxa.data.mappers.toMovieFullDBO
import com.myaxa.movie_catalog_impl.Movie
import com.myaxa.movie_catalog_impl.filters.Filters
import com.myaxa.movies.database.datasources.MoviesLocalDataSource
import com.myaxa.movies.database.models.DbRequestFilters
import com.myaxa.movies.database.models.MovieFullDBO
import com.myaxa.movies_api.MoviesRemoteDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.IOException

internal class MoviesPagingSource @AssistedInject constructor(
    @Assisted(QUERY_TAG) private val query: String,
    @Assisted(FILTERS_TAG) private val filters: Filters?,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
) : PagingSource<Int, Movie>() {

    private var cachedIds: List<Long>? = null
    private var pages: Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val currentPage = params.key ?: INITIAL_PAGE_NUMBER

        return try {
            val responseResult = remoteDataSource.getMovies(
                query,
                filters.toNetworkRequestFilters(),
                currentPage,
                params.loadSize,
            )

            val remoteList = responseResult.getOrNull()?.movies

            remoteList?.let { list ->
                localDataSource.insertList(list.map { it.toMovieFullDBO() })
            }

            val list =
                remoteList?.map { it.toMovie() }
                    ?: getCachedMovies(
                        query = query,
                        dbRequestFilters = filters.toDbRequestFilters(),
                        page = currentPage,
                        pageSize = params.loadSize,
                    ).map { it.toMovie() }

            val pages = responseResult.getOrNull()?.pages ?: pages ?: INITIAL_PAGE_NUMBER

            LoadResult.Page(
                list,
                prevKey = (currentPage - 1).takeIf { currentPage > INITIAL_PAGE_NUMBER },
                nextKey = (currentPage + 1).takeIf { currentPage < pages },
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    private suspend fun getCachedMovies(
        query: String,
        dbRequestFilters: DbRequestFilters,
        page: Int,
        pageSize: Int,
    ): List<MovieFullDBO> {
        if (cachedIds == null && page != 1) return emptyList()

        if (cachedIds == null) {
            cachedIds = localDataSource.getMovieIdListByQueryAndFilters(query, dbRequestFilters)
            pages = localDataSource.getPagesCount(pageSize)
        }

        val ids = cachedIds ?: return emptyList()
        return localDataSource.getMovieListByIds(ids, page, pageSize)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(QUERY_TAG) query: String,
            @Assisted(FILTERS_TAG) filters: Filters?,
        ): MoviesPagingSource
    }

    private companion object {
        const val QUERY_TAG = "query"
        const val FILTERS_TAG = "filters"

        const val INITIAL_PAGE_NUMBER = 1
    }
}