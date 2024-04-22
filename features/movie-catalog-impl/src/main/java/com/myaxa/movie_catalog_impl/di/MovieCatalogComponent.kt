package com.myaxa.movie_catalog_impl.di

import androidx.fragment.app.Fragment
import com.myaxa.filters_bottomsheet_api.FiltersBottomSheetApi
import com.myaxa.movie.details.api.MovieDetailsApi
import com.myaxa.movie_catalog_api.FiltersStateHolder
import com.myaxa.movie_catalog_api.MovieCatalogViewModel
import com.myaxa.movie_catalog_impl.ui.MovieCatalogEpoxyController
import com.myaxa.movie_catalog_impl.ui.filters.SelectedFiltersEpoxyController
import com.myaxa.movies.common.Navigator
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Component(
    modules = [MovieCatalogModule::class]
)
@MovieCatalogScope
internal interface MovieCatalogComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment,
            @BindsInstance viewModel: MovieCatalogViewModel,
            @BindsInstance navigator: Navigator,
            @BindsInstance movieDetailsApi: MovieDetailsApi,
            @BindsInstance filtersBottomSheetApi: FiltersBottomSheetApi,
        ): MovieCatalogComponent
    }

    val fragment: Fragment
    val viewModel: MovieCatalogViewModel
    val filtersStateHolder: FiltersStateHolder
    val navigator: Navigator
    val movieDetailsApi: MovieDetailsApi
    val filtersBottomSheetApi: FiltersBottomSheetApi

    @MovieCatalogScope
    val movieCatalogEpoxyController: MovieCatalogEpoxyController

    @MovieCatalogScope
    val selectedFiltersEpoxyController: SelectedFiltersEpoxyController
}

@Module
internal interface MovieCatalogModule {
    @Binds
    fun bindFilterStateHolder(impl: MovieCatalogViewModel): FiltersStateHolder

    companion object {
        @Provides
        fun provideCatalogEpoxyController(
            fragment: Fragment,
            navigator: Navigator,
            movieDetailsApi: MovieDetailsApi,
        ): MovieCatalogEpoxyController {
            return MovieCatalogEpoxyController {
                navigator.navigateToFragment(fragment.parentFragmentManager, movieDetailsApi.provideMovieDetails(it))
            }
        }
    }
}

@Scope
internal annotation class MovieCatalogScope
