package com.myaxa.movie.details.di

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.myaxa.movie.details.DetailsEpoxyController
import com.myaxa.movie.details.MovieDetailsViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Component(
    modules = [MovieDetailsFragmentModule::class],
)
@MovieDetailsFragmentScope
internal interface MovieDetailsFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance parentFragmentManager: FragmentManager,
            @BindsInstance viewModel: MovieDetailsViewModel,
        ): MovieDetailsFragmentComponent
    }

    val parentFragmentManager: FragmentManager
    val viewModel: MovieDetailsViewModel
    val detailsEpoxyController: DetailsEpoxyController
    val recycledViewPool: RecycledViewPool
}

@Module
internal interface MovieDetailsFragmentModule {
    companion object {
        @Provides
        @MovieDetailsFragmentScope
        fun provideRecycledViewPool(): RecycledViewPool {
            return RecycledViewPool()
        }
    }
}

@Scope
internal annotation class MovieDetailsFragmentScope