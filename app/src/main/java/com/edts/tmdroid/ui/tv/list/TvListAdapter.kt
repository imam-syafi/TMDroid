package com.edts.tmdroid.ui.tv.list

import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.ItemMediaBinding
import com.edts.tmdroid.ui.common.BaseListAdapter
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.model.Tv

class TvListAdapter(
    onClick: (Tv) -> Unit,
) : BaseListAdapter<Tv, ItemMediaBinding>(
    inflate = ItemMediaBinding::inflate,
    onClick = onClick,
    differ = Tv.DIFFER,
) {

    override fun ItemMediaBinding.bind(item: Tv) {
        val resources = root.resources

        ivPoster.loadFromUrl(item.posterUrl)
        tvTitle.text = item.name
        tvOverview.text = item.overview
        tvRating.text = resources.getString(
            R.string.rating,
            item.voteAverage.toString(),
            item.voteCount,
        )
    }
}
