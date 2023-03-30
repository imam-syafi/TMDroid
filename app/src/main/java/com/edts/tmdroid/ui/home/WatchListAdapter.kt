package com.edts.tmdroid.ui.home

import com.edts.tmdroid.databinding.ItemQueueBinding
import com.edts.tmdroid.ui.common.BaseListAdapter
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.model.Queue

class WatchListAdapter(
    onClick: (Queue) -> Unit,
) : BaseListAdapter<Queue, ItemQueueBinding>(
    inflate = ItemQueueBinding::inflate,
    onClick = onClick,
    differ = Queue.DIFFER,
) {

    override fun ItemQueueBinding.bind(item: Queue) {
        ivPoster.loadFromUrl(item.posterUrl)
        tvTitle.text = item.title
    }
}
