package com.edts.tmdroid.ui.movie.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.R
import com.edts.tmdroid.data.local.AppDatabase
import com.edts.tmdroid.data.remote.NetworkModule
import com.edts.tmdroid.databinding.FragmentMovieDetailBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.buildSnack
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.ext.showDialog
import com.edts.tmdroid.ui.model.Review
import com.edts.tmdroid.ui.review.ReviewListAdapter

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(
    FragmentMovieDetailBinding::inflate,
) {

    private val args by navArgs<MovieDetailFragmentArgs>()
    private val viewModel by viewModels<MovieDetailViewModel> {
        viewModelFactory {
            initializer {
                val db = AppDatabase.getInstance(requireContext())

                MovieDetailViewModel(
                    movieId = args.movieId,
                    tmdbService = NetworkModule.tmdbService,
                    queueDao = db.queueDao(),
                    reviewDao = db.reviewDao(),
                )
            }
        }
    }

    override fun FragmentMovieDetailBinding.setup() {
        btnToggle.setOnClickListener {
            val isSaved = viewModel.isSaved.value == true

            viewModel.onToggle()

            val successMessage = if (isSaved) {
                R.string.queue_removed
            } else {
                R.string.queue_added
            }

            buildSnack(successMessage)
                .setAction(R.string.show_all) {
                    val directions = MovieDetailFragmentDirections.toWatchListFragment()
                    findNavController().navigate(directions)
                }
                .also { snackbar = it }
                .show()
        }

        viewModel.isSaved.observe(viewLifecycleOwner) {
            val resId = if (it) R.string.remove_from_watch_list else R.string.add_to_watch_list
            btnToggle.setText(resId)
        }

        tvWriteReview.setOnClickListener {
            toReviewEditorFragment()
        }

        val reviewListAdapter = ReviewListAdapter(
            onClick = ::toReviewEditorFragment,
        ).also(rvReviews::setAdapter)

        viewModel.reviews.observe(viewLifecycleOwner, reviewListAdapter::submitList)

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

    private fun toReviewEditorFragment(review: Review? = null) {
        val resId = if (review != null) {
            R.string.edit_review
        } else {
            R.string.add_new_review
        }

        val directions = MovieDetailFragmentDirections.toReviewEditorFragment(
            title = getString(resId),
            movieId = args.movieId,
            review = review,
        )

        findNavController().navigate(directions)
    }
}
