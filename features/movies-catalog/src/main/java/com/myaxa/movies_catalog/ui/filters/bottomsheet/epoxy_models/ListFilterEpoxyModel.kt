package com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.epoxy.EpoxyController
import com.myaxa.movies.common.ViewBindingKotlinModel
import com.myaxa.movies_catalog.R
import com.myaxa.movies_catalog.databinding.ItemFilterListBinding




data class ListFilterEpoxyModel(
    val titleText: String,
    val controller: EpoxyController,
    val spanCount: Int = 3,
) : ViewBindingKotlinModel<ItemFilterListBinding>(R.layout.item_filter_list) {
    override fun ItemFilterListBinding.bind() {
        title.text = titleText
        val layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL)
        list.setController(controller)
        list.layoutManager = layoutManager
    }
}

