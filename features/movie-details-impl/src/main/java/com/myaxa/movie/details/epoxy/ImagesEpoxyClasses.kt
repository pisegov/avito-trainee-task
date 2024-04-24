package com.myaxa.movie.details.epoxy

import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemImageBinding
import com.myaxa.movie.details.models.ImageUI
import com.myaxa.movies.common.ViewBindingKotlinModel
import javax.inject.Inject
import com.myaxa.movies.common.R as CommonR

internal class ImagesEpoxyController @Inject constructor() : PagingDataEpoxyController<ImageUI>() {
    override fun buildItemModel(currentPosition: Int, item: ImageUI?): EpoxyModel<*> {
        return ImageEpoxyModel(item!!)
            .id("image_${currentPosition}")
    }
}

internal data class ImageEpoxyModel(private val model: ImageUI) :
    ViewBindingKotlinModel<ItemImageBinding>(R.layout.item_image) {

    override fun ItemImageBinding.bind() {
        image.load(model.url) {
            placeholder(CommonR.drawable.movie_placeholder)
            error(CommonR.drawable.movie_placeholder)
        }
    }
}

