package com.edts.tmdroid.ui.watchlist

import com.edts.tmdroid.databinding.ItemToWatchBinding
import com.edts.tmdroid.ui.common.BaseListAdapter
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.model.Queue

class WatchListAdapter(
    onClick: (Queue) -> Unit,
    private val onDelete: (Queue) -> Unit,
) : BaseListAdapter<Queue, ItemToWatchBinding>(
    inflate = ItemToWatchBinding::inflate,
    onClick = onClick,
    differ = Queue.DIFFER,
) {

    override fun ItemToWatchBinding.bind(item: Queue) {
        ivPoster.loadFromUrl(item.posterUrl)
        tvTitle.text = item.title

        ivTrash.setOnClickListener {
            onDelete(item)
        }
    }
}
