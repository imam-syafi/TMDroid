package com.edts.tmdroid.ui.movie.list

import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.ItemMediaBinding
import com.edts.tmdroid.ui.common.BaseListAdapter
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.model.Movie

class MovieListAdapter(
    onClick: (Movie) -> Unit,
) : BaseListAdapter<Movie, ItemMediaBinding>(
    inflate = ItemMediaBinding::inflate,
    onClick = onClick,
    differ = Movie.DIFFER,
) {

    override fun ItemMediaBinding.bind(item: Movie) {
        val resources = root.resources

        ivPoster.loadFromUrl(item.posterUrl)
        tvTitle.text = item.title
        tvOverview.text = item.overview
        tvRating.text = resources.getString(
            R.string.rating,
            item.voteAverage.toString(),
            item.voteCount,
        )
    }
}
