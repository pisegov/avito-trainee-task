package com.myaxa.data.movie_details

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myaxa.data.actors_remote.ActorsRemoteDataSource
import com.myaxa.data.mappers.toActor
import com.myaxa.domain.movie_details.Actor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.IOException

class ActorsPagingSource @AssistedInject constructor(
    @Assisted(MOVIE_ID_TAG) private val movieId: Long,
    private val remoteDataSource: ActorsRemoteDataSource,
    // private val localDataSource: ActorsLocalDataSource,
) : PagingSource<Int, Actor>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Actor> {

        val page = params.key ?: INITIAL_PAGE_NUMBER

        return try {
            val responseResult = remoteDataSource.getActorsByMovieId(
                movieId,
                page,
                params.loadSize,
            )

            val list = responseResult.getOrNull()?.actors
                ?.map { it.toActor() }
                ?.filter { it.name != null }
                ?: emptyList()
            val pages = responseResult.getOrNull()?.pages ?: page

            return LoadResult.Page(
                list,
                prevKey = (page - 1).takeIf { page > INITIAL_PAGE_NUMBER },
                nextKey = (page + 1).takeIf { page < pages },
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Actor>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(MOVIE_ID_TAG) movieId: Long,
        ): ActorsPagingSource
    }

    private companion object {
        const val MOVIE_ID_TAG = "movie_id"

        const val INITIAL_PAGE_NUMBER = 1
    }
}


