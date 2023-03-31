package com.edts.tmdroid.ui.watchlist

import com.edts.tmdroid.ui.model.Queue

data class WatchListState(
    val watchList: List<Queue> = emptyList(),
)
