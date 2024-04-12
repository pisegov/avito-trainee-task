package com.myaxa.movie.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.myaxa.movie.details.api.MovieDetailsDependenciesProvider
import com.myaxa.movie.details.di.DaggerMovieDetailsComponent
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.FragmentMovieDetailsBinding
import kotlinx.coroutines.launch
import java.text.NumberFormat
import kotlin.math.roundToInt
import com.myaxa.movies.common.R as CommonR

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

    private lateinit var controller: DetailsEpoxyController
    override fun onAttach(context: Context) {
        super.onAttach(context)

        val component = DaggerMovieDetailsComponent.factory().create()
        controller = component.detailsEpoxyController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = arguments?.getLong(MOVIE_ID_KEY)
        id?.let { viewModel.loadMovie(id) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.movieFlow.collect { model ->
                model?.let {
                    setupViews(it)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.actorsFlow.collect { actors ->
                actors?.let {
                    controller.actorsEpoxyController.submitData(it)
                    controller.requestModelBuild()
                }
            }
        }
    }

    private fun setupViews(model: MovieDetailsUI) {
        with(binding) {
            controller.model = model
            detailsList.setController(controller)

            backdrop.load(model.backdrop) {
                placeholder(CommonR.drawable.movie_placeholder)

                listener(
                    onError = { _, _ -> backdrop.load(model.poster) }
                )
            }
            collapsingToolbar.title = model.name
            tvReviewsNumber.text = model.reviewCount?.let { "$it отзывов" }

            model.rating?.let { rating ->
                val nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(1);
                ratingValue.text = nf.format(rating)
                listOf(star1, star2, star3, star4, star5).forEachIndexed { index, star ->
                    star.isEnabled = index < (rating.roundToInt() / 2)
                }
            }
        }
    }
}