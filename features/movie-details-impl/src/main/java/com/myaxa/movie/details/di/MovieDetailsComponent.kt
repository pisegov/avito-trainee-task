package com.myaxa.movie.details.di

import com.myaxa.movie.details.DetailsEpoxyController
import dagger.Component
import javax.inject.Scope

@Component
@MovieDetailsScope
internal interface MovieDetailsComponent {
    @Component.Factory
    interface Factory {
        fun create(

        ): MovieDetailsComponent
    }

    val detailsEpoxyController: DetailsEpoxyController
}

@Scope
annotation class MovieDetailsScope