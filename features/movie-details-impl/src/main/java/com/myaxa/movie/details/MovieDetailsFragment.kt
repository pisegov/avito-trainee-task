package com.myaxa.movie.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myaxa.movie.details.impl.R
import com.myaxa.movie.details.impl.databinding.FragmentMovieDetailsBinding

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.text.text = arguments?.getLong(MOVIE_ID_KEY).toString()
    }
}