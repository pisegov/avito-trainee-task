package com.myaxa.data.movie_details

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myaxa.data.actors_remote.MovieDetailsInfoDataSource
import com.myaxa.domain.movie_details.Actor
import com.myaxa.domain.movie_details.DetailsInfoModel
import com.myaxa.domain.movie_details.Episode
import com.myaxa.domain.movie_details.Season
import java.io.IOException

class MovieDetailsInfoPagingSource<T : DetailsInfoModel>(
    private val movieId: Long,
    private val type: Class<T>,
    private val remoteDataSource: MovieDetailsInfoDataSource,
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {

        val page = params.key ?: INITIAL_PAGE_NUMBER

        return try {
            val responseResult = remoteDataSource.getInfoByMovieId(
                type,
                movieId,
                page,
                params.loadSize,
            )

            val list = responseResult.getOrNull()?.list
                ?.map { it.toDomainModel() }
                ?.let {
                    if (type == Actor::class.java) {
                        it.filter {item ->
                            (item as Actor).name != null
                        }
                    } else {
                        it
                    }
                }
                ?.run {
                    if (type == Episode::class.java) {
                        this
                            .filter { item -> (item as Season).number != 0 }
                            .flatMap { item -> (item as Season).episodes }
                    } else {
                        this
                    }
                }
                ?: emptyList<T>()
            if (list.isEmpty()) throw Exception()
            val pages = responseResult.getOrNull()?.pages ?: page

            return LoadResult.Page(
                list as List<T>,
                prevKey = (page - 1).takeIf { page > INITIAL_PAGE_NUMBER },
                nextKey = (page + 1).takeIf { page < pages },
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    private companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }
}


