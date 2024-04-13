package com.myaxa.movie.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie.details.api.MovieDetailsDependenciesProvider
import com.myaxa.movie.details.di.DaggerMovieDetailsComponent
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.FragmentMovieDetailsBinding
import com.myaxa.movie.details.models.AdditionalListItem
import com.myaxa.movie.details.models.MovieDetailsUI
import kotlinx.coroutines.flow.Flow
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

        binding.detailsList.setController(controller)
        lifecycleScope.launch {
            viewModel.movieFlow.collect { model ->
                model?.let {
                    setupViews(it)
                }

                if (model?.isSeries == true) {
                    observeListFlow(viewModel.episodesFlow, controller.episodesEpoxyController)
                }
            }
        }

        setupListsObservers()
    }

    private fun setupViews(model: MovieDetailsUI) {
        with(binding) {
            controller.model = model

            backdrop.load(model.backdrop) {
                placeholder(CommonR.drawable.movie_placeholder)

                listener(
                    onError = { _, _ -> backdrop.load(model.poster) }
                )
            }
            collapsingToolbar.title = model.name
            tvReviewsNumber.text = model.reviewCount?.let { "$it отзывов" }

            model.rating?.let { rating ->
                val nf = NumberFormat.getNumberInstance()
                nf.setMaximumFractionDigits(1)
                ratingValue.text = nf.format(rating)
                listOf(star1, star2, star3, star4, star5).forEachIndexed { index, star ->
                    star.isEnabled = index < (rating.roundToInt() / 2)
                }
            }

            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setupListsObservers() {
        observeListFlow(viewModel.actorsFlow, controller.actorsEpoxyController)
        observeListFlow(viewModel.reviewsFlow, controller.reviewsEpoxyController)
        observeListFlow(viewModel.imagesFlow, controller.imagesEpoxyController)

        observeLoadingState(controller.actorsEpoxyController.loadStateFlow) { state ->
            controller.actorsLoadingState = state
        }
        observeLoadingState(controller.reviewsEpoxyController.loadStateFlow) { state ->
            controller.reviewsLoadingState = state
        }
        observeLoadingState(controller.imagesEpoxyController.loadStateFlow) { state ->
            controller.imagesLoadingState = state
        }
        observeLoadingState(controller.episodesEpoxyController.loadStateFlow) { state ->
            controller.episodesLoadingState = state
        }
    }

    private fun <T: AdditionalListItem> observeListFlow(flow: Flow<PagingData<T>>, controller: PagingDataEpoxyController<T>) {
        lifecycleScope.launch {
            flow.collect { list ->
                list.let {
                    controller.submitData(it)
                    controller.requestModelBuild()
                }
            }
        }
    }

    private fun observeLoadingState(flow: Flow<CombinedLoadStates>, stateSetter: (LoadState) -> Unit) {
        lifecycleScope.launch {
            flow.collect {
                stateSetter(it.source.refresh)
            }
        }
    }
}