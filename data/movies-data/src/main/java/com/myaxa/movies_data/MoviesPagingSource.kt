package com.myaxa.movies_data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myaxa.data.mappers.toMovieRemoteDBO
import com.myaxa.movies.database.datasources.MoviesLocalDataSource
import com.myaxa.movies_api.MoviesRemoteDataSource
import com.myaxa.movies_catalog.Movie
import com.myaxa.movies_catalog.filters.Filters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.IOException

class MoviesPagingSource @AssistedInject constructor(
    @Assisted(QUERY_TAG) private val query: String,
    @Assisted(FILTERS_TAG) private val filters: Filters?,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val page = params.key ?: INITIAL_PAGE_NUMBER

        return try {
            if (query.isEmpty()) {
                loadWithEmptyQuery(params, page)
            } else {
                loadWithQuery(params, page)
            }
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

    private suspend fun loadWithEmptyQuery(params: LoadParams<Int>, page: Int): LoadResult.Page<Int, Movie> {
        val responseResult = remoteDataSource.getMovies(
            page,
            params.loadSize,
            year = if (filters?.year?.isSelected == true) filters.year.toString() else null,
            rating = if (filters?.rating?.isSelected == true) filters.rating.toString() else null,
            countries = if (filters?.countries?.isSelected == true) filters.countries.selectedOptions() else null,
            types = if (filters?.types?.isSelected == true) filters.types.selectedOptions() else null,
            networks = if (filters?.networks?.isSelected == true) filters.networks.selectedOptions() else null,
            genres = if (filters?.genres?.isSelected == true) filters.genres.selectedOptions() else null,
            ageRatings = if (filters?.ageRatings?.isSelected == true) filters.ageRatings.selectedOptions() else null,
        )

        val remoteList = responseResult.getOrNull()?.movies
        val list = responseResult.getOrNull()?.movies?.map { it.toMovie() } ?: emptyList()
        val pages = responseResult.getOrNull()?.pages ?: page

        localDataSource.insertList(
            remoteList?.map { it.toMovieRemoteDBO() } ?: emptyList()
        )

        return LoadResult.Page(
            list,
            prevKey = (page - 1).takeIf { page > INITIAL_PAGE_NUMBER },
            nextKey = (page + 1).takeIf { page < pages },
        )
    }

    private suspend fun loadWithQuery(params: LoadParams<Int>, page: Int): LoadResult.Page<Int, Movie> {
        val responseResult = remoteDataSource.getMovies(query, page, params.loadSize)

        val list = responseResult.getOrNull()?.movies ?: emptyList()
        val pages = responseResult.getOrNull()?.pages ?: page

        val remoteList = remoteDataSource.filterMoviesList(
            ids = list.map { it.id },
            year = if (filters?.year?.isSelected == true) filters.year.toString() else null,
            rating = if (filters?.rating?.isSelected == true) filters.rating.toString() else null,
            countries = if (filters?.countries?.isSelected == true) filters.countries.selectedOptions() else null,
            types = if (filters?.types?.isSelected == true) filters.types.selectedOptions() else null,
            networks = if (filters?.networks?.isSelected == true) filters.networks.selectedOptions() else null,
            genres = if (filters?.genres?.isSelected == true) filters.genres.selectedOptions() else null,
            ageRatings = if (filters?.ageRatings?.isSelected == true) filters.ageRatings.selectedOptions() else null,
        ).getOrNull()?.movies

        val filteredMovies = remoteList?.let { list ->
            list.map { it.toMovie() }
        } ?: emptyList()

        localDataSource.insertList(
            remoteList?.map { it.toMovieRemoteDBO() } ?: emptyList()
        )

        return LoadResult.Page(
            filteredMovies,
            prevKey = (page - 1).takeIf { page > INITIAL_PAGE_NUMBER },
            nextKey = (page + 1).takeIf { page < pages },
        )
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