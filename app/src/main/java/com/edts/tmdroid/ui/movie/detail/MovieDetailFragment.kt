package com.edts.tmdroid.ui.movie.detail

import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.FragmentMovieDetailBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.loadFromUrl

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(
    FragmentMovieDetailBinding::inflate,
) {

    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun FragmentMovieDetailBinding.setup() {
        val movie = args.movie

        ivBackdrop.loadFromUrl(movie.backdropUrl)
        ivPoster.loadFromUrl(movie.posterUrl)
        tvTitle.text = movie.title
        tvReleaseDate.text = movie.releaseDate
        tvRating.text = getString(R.string.rating, movie.voteAverage.toString(), movie.voteCount)
        tvOverview.text = movie.overview
    }
}
