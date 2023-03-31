package com.edts.tmdroid.ui.tv.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.R
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.databinding.FragmentMediaDetailBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.buildSnack
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.ext.showDialog
import com.edts.tmdroid.ui.model.Review
import com.edts.tmdroid.ui.review.ReviewListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvDetailFragment : BaseFragment<FragmentMediaDetailBinding>(
    FragmentMediaDetailBinding::inflate,
) {

    private val args by navArgs<TvDetailFragmentArgs>()
    private val viewModel by viewModels<TvDetailViewModel>()

    override fun FragmentMediaDetailBinding.setup() {
        btnToggle.setOnClickListener {
            val isSaved = viewModel.state.value?.isSaved == true

            viewModel.onToggle()

            val successMessage = if (isSaved) {
                R.string.queue_removed
            } else {
                R.string.queue_added
            }

            buildSnack(successMessage)
                .setAction(R.string.show_all) {
                    val directions = TvDetailFragmentDirections.toWatchListFragment()
                    findNavController().navigate(directions)
                }
                .also { snackbar = it }
                .show()
        }

        tvWriteReview.setOnClickListener {
            toReviewEditorFragment()
        }

        val reviewListAdapter = ReviewListAdapter(
            onClick = ::toReviewEditorFragment,
        ).also(rvReviews::setAdapter)

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            loadingDialog.showDialog(state.isLoading)

            btnToggle.setText(
                if (state.isSaved) {
                    R.string.remove_from_watch_list
                } else {
                    R.string.add_to_watch_list
                },
            )

            state.tv?.let {
                ivBackdrop.loadFromUrl(it.backdropUrl)
                ivPoster.loadFromUrl(it.posterUrl)
                tvTitle.text = it.name
                tvReleaseDate.text = it.firstAirDate
                tvRating.text = getString(R.string.rating, it.voteAverage.toString(), it.voteCount)
                tvOverview.text = it.overview
            }

            reviewListAdapter.submitList(state.reviews)
        }
    }

    private fun toReviewEditorFragment(review: Review? = null) {
        val resId = if (review != null) {
            R.string.edit_review
        } else {
            R.string.add_new_review
        }

        val directions = TvDetailFragmentDirections.toReviewEditorFragment(
            title = getString(resId),
            mediaId = args.tvId,
            mediaType = MediaType.Tv,
            review = review,
        )

        findNavController().navigate(directions)
    }
}
