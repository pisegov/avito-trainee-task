package com.myaxa.movies_catalog.di

import androidx.lifecycle.ViewModelProvider
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.ui.MoviesEpoxyController
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Component(
    dependencies = [MoviesCatalogDependencies::class],
    modules = [MoviesCatalogModule::class]
)
@MoviesCatalogScope
internal interface MoviesCatalogComponent {
    @Component.Factory
    interface Factory {
        fun create(
            dependencies: MoviesCatalogDependencies,
            @BindsInstance viewModel: MoviesCatalogViewModel,
        ): MoviesCatalogComponent
    }

   val moviesEpoxyController: MoviesEpoxyController
}

@Module
internal interface MoviesCatalogModule

@Scope
internal annotation class MoviesCatalogScope
