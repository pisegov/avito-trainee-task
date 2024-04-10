package com.myaxa.movies_catalog.di

import androidx.lifecycle.LifecycleCoroutineScope
import com.myaxa.movie.details.api.MovieDetailsApi
import com.myaxa.movies.common.Navigator
import com.myaxa.movies_catalog.ui.MoviesCatalogViewController
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Component(
    dependencies = [MoviesCatalogComponent::class],
    modules = [MoviesCatalogViewModule::class]
)
@MoviesCatalogViewScope
internal interface MoviesCatalogViewComponent {
    @Component.Factory
    interface Factory {
        fun create(
            moviesCatalogComponent: MoviesCatalogComponent,
            @BindsInstance lifecycleScope: LifecycleCoroutineScope,
            @BindsInstance navigator: Navigator,
            @BindsInstance movieDetailsApi: MovieDetailsApi,
        ): MoviesCatalogViewComponent
    }

    val viewController: MoviesCatalogViewController
}

@Module
interface MoviesCatalogViewModule

@Scope
annotation class MoviesCatalogViewScope