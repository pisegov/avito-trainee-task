package com.myaxa.movies_catalog.ui.filters

import com.myaxa.movies_catalog.MoviesCatalogViewModel
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