package com.myaxa.movie.details.di

import androidx.lifecycle.LifecycleOwner
import com.myaxa.movie.details.MovieDetailsViewController
import com.myaxa.movie.details.impl.databinding.FragmentMovieDetailsBinding
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Component(
    dependencies = [MovieDetailsFragmentComponent::class],
    modules = [MovieDetailsViewModule::class],
)
@MovieDetailsViewScope
internal interface MovieDetailsViewComponent {
    @Component.Factory
    interface Factory {
        fun create(
            movieDetailsFragmentComponent: MovieDetailsFragmentComponent,
            @BindsInstance binding: FragmentMovieDetailsBinding,
            @BindsInstance lifecycleOwner: LifecycleOwner,
        ): MovieDetailsViewComponent
    }

    val viewController: MovieDetailsViewController
}

@Module
internal interface MovieDetailsViewModule

@Scope
internal annotation class MovieDetailsViewScope