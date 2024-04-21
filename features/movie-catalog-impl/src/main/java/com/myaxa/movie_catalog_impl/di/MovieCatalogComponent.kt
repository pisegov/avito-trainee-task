package com.myaxa.movie_catalog_impl.di

import androidx.fragment.app.Fragment
import com.myaxa.movie_catalog_api.MovieCatalogViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
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
        ): MovieCatalogComponent
    }

    val fragment: Fragment
    val viewModel: MovieCatalogViewModel
}

@Module
internal interface MovieCatalogModule

@Scope
internal annotation class MovieCatalogScope
