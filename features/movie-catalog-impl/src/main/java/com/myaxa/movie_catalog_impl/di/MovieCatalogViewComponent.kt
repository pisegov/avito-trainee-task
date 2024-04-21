package com.myaxa.movie_catalog_impl.di

import androidx.lifecycle.LifecycleCoroutineScope
import com.myaxa.filters_bottomsheet_api.FiltersBottomSheetApi
import com.myaxa.movie.details.api.MovieDetailsApi
import com.myaxa.movie_catalog_impl.ui.MovieCatalogViewController
import com.myaxa.movies.common.Navigator
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Component(
    dependencies = [MovieCatalogComponent::class],
    modules = [MoviesCatalogViewModule::class]
)
@MoviesCatalogViewScope
internal interface MovieCatalogViewComponent {
    @Component.Factory
    interface Factory {
        fun create(
            moviesCatalogComponent: MovieCatalogComponent,
            @BindsInstance lifecycleScope: LifecycleCoroutineScope,
            @BindsInstance navigator: Navigator,
            @BindsInstance movieDetailsApi: MovieDetailsApi,
            @BindsInstance filtersBottomSheetApi: FiltersBottomSheetApi,
        ): MovieCatalogViewComponent
    }

    val viewController: MovieCatalogViewController
}

@Module
internal interface MoviesCatalogViewModule

@Scope
internal annotation class MoviesCatalogViewScope