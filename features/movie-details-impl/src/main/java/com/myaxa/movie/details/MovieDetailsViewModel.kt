package com.myaxa.movie.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.myaxa.domain.movie_details.Actor
import com.myaxa.domain.movie_details.DetailsInfoModel
import com.myaxa.domain.movie_details.Episode
import com.myaxa.domain.movie_details.Image
import com.myaxa.domain.movie_details.MovieDetailsRepository
import com.myaxa.domain.movie_details.Review
import com.myaxa.movie.details.models.ActorUI
import com.myaxa.movie.details.models.AdditionalListItem
import com.myaxa.movie.details.models.EpisodeUI
import com.myaxa.movie.details.models.ImageUI
import com.myaxa.movie.details.models.ReviewUI
import com.myaxa.movie.details.models.toActorUI
import com.myaxa.movie.details.models.toEpisodeUI
import com.myaxa.movie.details.models.toImageUI
import com.myaxa.movie.details.models.toMovieDetailsUI
import com.myaxa.movie.details.models.toReviewUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel() {

    private val _movieFlow = MutableStateFlow(0L)
    internal val movieFlow = _movieFlow.flatMapLatest {
        repository.getMovieDetailsFlow(it)
    }.map {
        it?.toMovieDetailsUI()
    }

    val actorsFlow: Flow<PagingData<ActorUI>> = additionalListFlow(Actor::class.java) as Flow<PagingData<ActorUI>>

    val reviewsFlow = additionalListFlow(Review::class.java, 3) as Flow<PagingData<ReviewUI>>

    val imagesFlow = additionalListFlow(Image::class.java, 3) as Flow<PagingData<ImageUI>>

    val episodesFlow = additionalListFlow(Episode::class.java, 1) as Flow<PagingData<EpisodeUI>>

    internal fun loadMovie(id: Long) {
        viewModelScope.launch {
            repository.loadMovieDetails(id)
        }
        _movieFlow.tryEmit(id)
    }

    private fun <T : DetailsInfoModel> additionalListFlow(
        type: Class<T>,
        pageSize: Int = 10,
    ): Flow<PagingData<AdditionalListItem>> {
        return _movieFlow
            .map { id ->
                newPager(id, type, pageSize)
            }
            .flatMapLatest { pager ->
                pager.flow.map { pagingData -> pagingData.map { map(it, type) } }
            }
            .cachedIn(viewModelScope)
    }

    private fun <T : DetailsInfoModel> newPager(
        id: Long,
        type: Class<T>,
        pageSize: Int = 10,
    ): Pager<Int, T> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false,
                prefetchDistance = 1,
                initialLoadSize = pageSize
            )
        ) {
            repository.getInfo(id, type)
        }
    }

    private fun <T : DetailsInfoModel> map(item: DetailsInfoModel, type: Class<T>): AdditionalListItem {
        return when (type) {

            Actor::class.java -> {
                (item as Actor).toActorUI()
            }

            Review::class.java -> {
                (item as Review).toReviewUI()
            }

            Episode::class.java -> {
                (item as Episode).toEpisodeUI()
            }

            else -> {
                (item as Image).toImageUI()
            }
        }
    }
}