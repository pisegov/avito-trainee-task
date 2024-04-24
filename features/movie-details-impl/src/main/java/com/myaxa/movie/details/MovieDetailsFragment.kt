package com.myaxa.movie.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.myaxa.movie.details.api.MovieDetailsDependenciesProvider
import com.myaxa.movie.details.di.DaggerMovieDetailsFragmentComponent
import com.myaxa.movie.details.di.DaggerMovieDetailsViewComponent
import com.myaxa.movie.details.di.MovieDetailsFragmentComponent
import com.myaxa.movie.details.di.MovieDetailsViewComponent
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.FragmentMovieDetailsBinding
import com.myaxa.movies.common.unsafeLazy

internal class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    companion object {
        fun newInstance(id: Long): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                arguments = Bundle().apply { putLong(MOVIE_ID_KEY, id) }
            }
        }

        private const val MOVIE_ID_KEY = "movie_id_key"
    }

    private val viewModelFactory
        get() = (requireActivity().applicationContext as MovieDetailsDependenciesProvider)
            .provideMovieDetailsDependencies()
            .viewModelFactory

    private val viewModel: MovieDetailsViewModel by viewModels { viewModelFactory }

    private val component: MovieDetailsFragmentComponent by unsafeLazy {
        DaggerMovieDetailsFragmentComponent.factory().create(
            parentFragmentManager = this.parentFragmentManager,
            viewModel = viewModel,
        )
    }

    private var viewComponent: MovieDetailsViewComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getLong(MOVIE_ID_KEY)?.let {
            viewModel.loadMovie(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMovieDetailsBinding.inflate(inflater)

        viewComponent = DaggerMovieDetailsViewComponent.factory().create(
            movieDetailsFragmentComponent = component,
            binding = binding,
            lifecycleOwner = viewLifecycleOwner,
        ).apply {
            viewController.setUpViews()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewComponent = null
    }
}