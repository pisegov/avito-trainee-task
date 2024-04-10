package com.myaxa.movie.details.di

import androidx.fragment.app.Fragment
import com.myaxa.movie.details.MovieDetailsFragment
import com.myaxa.movie.details.api.MovieDetailsApi
import javax.inject.Inject

internal class MovieDetailsApiImpl @Inject constructor(): MovieDetailsApi {
    override fun provideMovieDetails(id: Long): Fragment {

        return MovieDetailsFragment.newInstance(id)
    }
}