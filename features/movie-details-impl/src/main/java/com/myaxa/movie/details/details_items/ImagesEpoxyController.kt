package com.myaxa.movie.details.details_items

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.ItemImageBinding
import com.myaxa.movie.details.impl.databinding.ItemImagesListBinding
import com.myaxa.movie.details.models.ImageUI
import com.myaxa.movies.common.ViewBindingKotlinModel
import javax.inject.Inject
import com.myaxa.movies.common.R as CommonR

internal data class ImagesListEpoxyModel(
    private val controller: ImagesEpoxyController,
) : ViewBindingKotlinModel<ItemImagesListBinding>(R.layout.item_images_list) {

    override fun ItemImagesListBinding.bind() {
        val context = this.root.context
        val layoutManager = LinearLayoutManager(context, GridLayoutManager.HORIZONTAL, false)
        list.setController(controller)
        list.layoutManager = layoutManager
    }
}

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

