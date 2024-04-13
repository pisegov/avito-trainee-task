package com.myaxa.movies_catalog.ui.filters.bottomsheet

import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.ui.filters.bottomsheet.epoxy_controllers.FiltersEpoxyController
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Component
@FiltersBottomSheetScope
interface FiltersBottomSheetComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance viewModel: MoviesCatalogViewModel
        ): FiltersBottomSheetComponent
    }
    val filtersEpoxyController: FiltersEpoxyController
}

@Scope
annotation class FiltersBottomSheetScope