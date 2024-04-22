package com.myaxa.movie_catalog_impl.di

import androidx.lifecycle.LifecycleCoroutineScope
import com.myaxa.movie_catalog_impl.databinding.FragmentMovieCatalogBinding
import com.myaxa.movie_catalog_impl.ui.MovieCatalogViewController
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Component(
    dependencies = [MovieCatalogComponent::class],
    modules = [MovieCatalogViewModule::class]
)
@MovieCatalogViewScope
internal interface MovieCatalogViewComponent {
    @Component.Factory
    interface Factory {
        fun create(
            moviesCatalogComponent: MovieCatalogComponent,
            @BindsInstance binding: FragmentMovieCatalogBinding,
            @BindsInstance lifecycleScope: LifecycleCoroutineScope,
        ): MovieCatalogViewComponent
    }

    @MovieCatalogViewScope
    val viewController: MovieCatalogViewController
}

@Module
internal interface MovieCatalogViewModule

@Scope
internal annotation class MovieCatalogViewScope