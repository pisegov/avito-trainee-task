package com.myaxa.movie.details

import androidx.lifecycle.ViewModel
import com.myaxa.domain.movie_details.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel() {

}