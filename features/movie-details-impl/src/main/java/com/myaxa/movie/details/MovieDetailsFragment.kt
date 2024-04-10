package com.myaxa.movie.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myaxa.movie.details.api.MovieDetailsDependenciesProvider
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.FragmentMovieDetailsBinding
import kotlinx.coroutines.launch

internal class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    companion object {
        fun newInstance(id: Long): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                arguments = Bundle().apply { putLong(MOVIE_ID_KEY, id) }
            }
        }

        private const val MOVIE_ID_KEY = "movie_id_key"
    }

    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val viewModelFactory
        get() = (requireActivity().applicationContext as MovieDetailsDependenciesProvider)
            .provideMovieDetailsDependencies()
            .viewModelFactory

    private val viewModel: MovieDetailsViewModel by viewModels { viewModelFactory }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = arguments?.getLong(MOVIE_ID_KEY)
        id?.let { viewModel.loadMovie(id) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.movieFlow.collect {
                binding.id.text = it?.id.toString()
                binding.name.text = it?.name
            }
        }

    }
}