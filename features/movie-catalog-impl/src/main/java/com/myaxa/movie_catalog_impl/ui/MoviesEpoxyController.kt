package com.myaxa.movie_catalog_impl.ui

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie_catalog_api.models.MovieInCatalog
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class MoviesEpoxyController @AssistedInject constructor(
    @Assisted(NAVIGATION_CALLBACK_TAG) private val navigationCallback: (id: Long) -> Unit,
) : PagingDataEpoxyController<MovieInCatalog>() {
    override fun buildItemModel(currentPosition: Int, item: MovieInCatalog?): EpoxyModel<*> {

        return MovieEpoxyModel(item!!, ::navigationCallback.get())
            .id("movie_${item.id}")
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(NAVIGATION_CALLBACK_TAG) navigationCallback: (Long) -> Unit,
        ): MoviesEpoxyController
    }

    private companion object {
        const val NAVIGATION_CALLBACK_TAG = "navigation_callback"
    }
}