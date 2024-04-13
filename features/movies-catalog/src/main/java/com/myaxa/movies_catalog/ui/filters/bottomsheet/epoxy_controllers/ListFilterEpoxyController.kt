package com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_controllers

import com.airbnb.epoxy.EpoxyController
import com.myaxa.movies_catalog.filters.Filter
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_models.ListFilterOptionEpoxyModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ListFilterEpoxyController @AssistedInject constructor(
    @Assisted(SUBMIT_CALLBACK_TAG) private val submitCallback: (Filter.ListFilter) -> Unit,
) : EpoxyController() {

    var filter: Filter.ListFilter? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        filter?.let { filter ->
            filter.options.forEach { option ->
                ListFilterOptionEpoxyModel(option.key, option.value) { key, value ->
                    val map = filter.options.toMutableMap()
                    map[key] = value
                    val new = filter.copy(options = map)
                    submitCallback(new)
                    this.filter = new
                }
                    .id("${filter.title}_${option.key}")
                    .addTo(this)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(SUBMIT_CALLBACK_TAG) submitCallback: (Filter.ListFilter) -> Unit,
        ): ListFilterEpoxyController
    }

    companion object {
        private const val SUBMIT_CALLBACK_TAG = "submit_callback"
    }
}