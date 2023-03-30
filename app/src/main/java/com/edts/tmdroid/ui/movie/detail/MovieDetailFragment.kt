package com.edts.tmdroid.ui.movie.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.R
import com.edts.tmdroid.data.local.AppDatabase
import com.edts.tmdroid.data.remote.NetworkModule
import com.edts.tmdroid.databinding.FragmentMovieDetailBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.ext.showDialog

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(
    FragmentMovieDetailBinding::inflate,
) {

    private val args by navArgs<MovieDetailFragmentArgs>()
    private val viewModel by viewModels<MovieDetailViewModel> {
        viewModelFactory {
            initializer {
                val movieId = args.movieId
                val queueDao = AppDatabase
                    .getInstance(requireContext())
                    .queueDao()

                MovieDetailViewModel(movieId, NetworkModule.tmdbService, queueDao)
            }
        }
    }

    override fun FragmentMovieDetailBinding.setup() {
        btnToggle.setOnClickListener {
            viewModel.onToggle()
        }

        viewModel.isSaved.observe(viewLifecycleOwner) {
            val resId = if (it) R.string.remove_from_watch_list else R.string.add_to_watch_list
            btnToggle.setText(resId)
        }

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            loadingDialog.showDialog(state.isLoading)

            state.movie?.let {
                ivBackdrop.loadFromUrl(it.backdropUrl)
                ivPoster.loadFromUrl(it.posterUrl)
                tvTitle.text = it.title
                tvReleaseDate.text = it.releaseDate
                tvRating.text = getString(R.string.rating, it.voteAverage.toString(), it.voteCount)
                tvOverview.text = it.overview
            }
        }
    }
}
