package com.edts.tmdroid.ui.media.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.FragmentMediaDetailBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.buildSnack
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.ext.showDialog
import com.edts.tmdroid.ui.model.Media
import com.edts.tmdroid.ui.model.Review
import com.edts.tmdroid.ui.review.ReviewListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaDetailFragment : BaseFragment<FragmentMediaDetailBinding>(
    FragmentMediaDetailBinding::inflate,
) {

    private val args by navArgs<MediaDetailFragmentArgs>()
    private val viewModel by viewModels<MediaDetailViewModel>()

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
                    val directions = MediaDetailFragmentDirections.toWatchListFragment()
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

            state.media?.let {
                ivBackdrop.loadFromUrl(it.backdropUrl)
                ivPoster.loadFromUrl(it.posterUrl)
                tvRating.text = getString(R.string.rating, it.voteAverage.toString(), it.voteCount)
                tvOverview.text = it.overview

                when (it) {
                    is Media.Movie -> {
                        tvTitle.text = it.title
                        tvReleaseDate.text = it.releaseDate
                    }
                    is Media.Tv -> {
                        tvTitle.text = it.name
                        tvReleaseDate.text = it.firstAirDate
                    }
                }
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

        val directions = MediaDetailFragmentDirections.toReviewEditorFragment(
            title = getString(resId),
            mediaId = args.mediaId,
            mediaType = args.mediaType,
            review = review,
        )

        findNavController().navigate(directions)
    }
}
