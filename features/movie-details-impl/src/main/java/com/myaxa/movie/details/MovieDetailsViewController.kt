package com.myaxa.movie.details

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import coil.load
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.myaxa.movie.details.di.MovieDetailsViewScope
import com.myaxa.movie.details.impl.databinding.FragmentMovieDetailsBinding
import com.myaxa.movie.details.models.AdditionalListItem
import com.myaxa.movie.details.models.MovieDetailsUI
import com.myaxa.movies.common.R
import com.myaxa.movies.common.collectOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import javax.inject.Inject

@MovieDetailsViewScope
internal class MovieDetailsViewController @Inject constructor(
    private val binding: FragmentMovieDetailsBinding,
    private val viewModel: MovieDetailsViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val detailsEpoxyController: DetailsEpoxyController,
    private val recycledViewPool: RecycledViewPool,
    private val parentFragmentManager: FragmentManager,
) {
    fun setUpViews() {

        viewModel.movieFlow.collectOnLifecycle(lifecycleOwner) { model ->
            model?.let {
                detailsEpoxyController.model = it
                setUpToolbar(it)
            }
        }

        setUpDetailsList()

        setUpListObservers()
    }

    private fun setUpDetailsList() {
        binding.detailsList.setController(detailsEpoxyController)
        binding.detailsList.setRecycledViewPool(recycledViewPool)
    }

    private fun setUpToolbar(model: MovieDetailsUI) = with(binding) {
        backdrop.load(model.backdrop) {
            placeholder(R.drawable.movie_placeholder)

            listener(
                onError = { _, _ -> backdrop.load(model.poster) }
            )
        }

        collapsingToolbar.title = model.name

        genres.text = model.genres

        model.rating?.let { setUpRating(it) }

        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setUpRating(rating: Double) = with(binding) {
        val nf = NumberFormat.getNumberInstance()
        nf.setMaximumFractionDigits(1)
        ratingValue.text = nf.format(rating)
        listOf(star1, star2, star3, star4, star5).forEachIndexed { index, star ->
            star.isEnabled = (index + 1) < ((rating + 1) / 2)
        }
    }

    private fun setUpListObservers() {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeListFlow(viewModel.actorsFlow, detailsEpoxyController.actorsListConfig.controller)
                observeListFlow(viewModel.reviewsFlow, detailsEpoxyController.reviewsListConfig.controller)
                observeListFlow(viewModel.imagesFlow, detailsEpoxyController.imagesListConfig.controller)
                observeListFlow(viewModel.episodesFlow, detailsEpoxyController.episodeListConfig.controller)

                detailsEpoxyController.observeLoadingStates(viewLifecycleScope = lifecycleOwner.lifecycleScope)
            }
        }
    }

    private fun <T : AdditionalListItem> observeListFlow(
        flow: Flow<PagingData<T>>,
        controller: PagingDataEpoxyController<T>,
    ) {
        flow.collectOnLifecycle(lifecycleOwner) { list ->
            controller.submitData(list)
        }
    }
}
