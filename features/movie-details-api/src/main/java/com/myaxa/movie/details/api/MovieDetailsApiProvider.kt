package com.myaxa.movie.details.api

import androidx.fragment.app.Fragment

interface MovieDetailsApiProvider {
    fun provideMovieDetailsApi(): MovieDetailsApi
}

interface MovieDetailsApi {
    fun provideMovieDetails(id: Long): Fragment
}