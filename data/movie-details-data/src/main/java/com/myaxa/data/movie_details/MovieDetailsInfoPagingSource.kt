package com.myaxa.data.movie_details

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myaxa.data.actors_remote.MovieDetailsInfoDataSource
import com.myaxa.domain.movie_details.Actor
import com.myaxa.domain.movie_details.DetailsInfoModel
import com.myaxa.domain.movie_details.Episode
import com.myaxa.domain.movie_details.Season
import com.myaxa.movies.database.datasources.MovieDetailsInfoLocalDataSource
import com.myaxa.movies.database.models.MovieDetailsInfoDBO
import java.io.IOException

internal class MovieDetailsInfoPagingSource<T : DetailsInfoModel>(
    private val movieId: Long,
    private val type: Class<T>,
    private val remoteDataSource: MovieDetailsInfoDataSource,
    private val localDataSource: MovieDetailsInfoLocalDataSource,
) : PagingSource<Int, T>() {

    private var localPages : Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {

        val page = params.key ?: INITIAL_PAGE_NUMBER

        return try {
            val responseResult = remoteDataSource.getInfoByMovieId(
                type,
                movieId,
                page,
                params.loadSize,
            )

            val remoteList = responseResult.getOrNull()?.list?.let { list ->
                list.map { it.toDomainModel() }
                    .let {
                        when (type) {
                            Actor::class.java -> {
                                it.filter { item ->
                                    (item as Actor).name != null
                                }
                            }

                            Episode::class.java -> {
                                it
                                    .filter { item -> (item as Season).number != 0 }
                                    .flatMap { item -> (item as Season).episodes }
                            }

                            else -> it
                        }
                    }
            }


            localDataSource.insertList(
                list = remoteList ?: emptyList(),
                movieId = movieId,
                type = type,
            )

            val list = remoteList ?: getLocalList(page, params.loadSize)
                .map { it.toDomainModel() }

            if (list.isEmpty()) throw Exception()

            val pages = responseResult.getOrNull()?.pages ?: localPages ?: page

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

   private suspend fun getLocalList(
       page: Int,
       pageSize: Int,
   ): List<MovieDetailsInfoDBO> {
       if (localPages == null) {
           localPages = localDataSource.getPagesCount(movieId, pageSize, type)
       }

       return localDataSource.getList(movieId, type, page, pageSize)
   }

    private companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }
}