package com.edts.tmdroid.ui.media.list

import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.ItemMediaBinding
import com.edts.tmdroid.ui.common.BaseListAdapter
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.model.Media

class MediaListAdapter(
    onClick: (Media) -> Unit,
    private val onLastItem: () -> Unit,
) : BaseListAdapter<Media, ItemMediaBinding>(
    inflate = ItemMediaBinding::inflate,
    onClick = onClick,
    differ = Media.DIFFER,
) {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val isLastItem = position == itemCount - 1
        if (isLastItem) {
            onLastItem()
        }
    }

    override fun ItemMediaBinding.bind(item: Media) {
        val resources = root.resources

        ivPoster.loadFromUrl(item.posterUrl)
        tvTitle.text = when (item) {
            is Media.Movie -> item.title
            is Media.Tv -> item.name
        }
        tvOverview.text = item.overview
        tvRating.text = resources.getString(
            R.string.rating,
            item.voteAverage.toString(),
            item.voteCount,
        )
    }
}
