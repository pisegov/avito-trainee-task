package com.myaxa.filters_bottomsheet_impl.ui.epoxy_models

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.epoxy.EpoxyController
import com.myaxa.filters_bottomsheet_impl.R
import com.myaxa.filters_bottomsheet_impl.databinding.ItemFilterListBinding
import com.myaxa.movies.common.ViewBindingKotlinModel

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

