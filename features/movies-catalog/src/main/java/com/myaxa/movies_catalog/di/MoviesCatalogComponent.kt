package com.myaxa.movies_catalog.di

import androidx.fragment.app.Fragment
import com.myaxa.movies_catalog.MoviesCatalogViewModel
import com.myaxa.movies_catalog.ui.MoviesEpoxyController
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Component(
    modules = [MoviesCatalogModule::class]
)
@MoviesCatalogScope
internal interface MoviesCatalogComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment,
            @BindsInstance viewModel: MoviesCatalogViewModel,
        ): MoviesCatalogComponent
    }

   val moviesEpoxyController: MoviesEpoxyController
   val fragment: Fragment
   val viewModel: MoviesCatalogViewModel
}

@Module
internal interface MoviesCatalogModule

@Scope
internal annotation class MoviesCatalogScope
