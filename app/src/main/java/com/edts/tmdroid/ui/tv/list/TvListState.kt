package com.edts.tmdroid.ui.tv.list

import com.edts.tmdroid.ui.model.Tv

data class TvListState(
    val isLoading: Boolean,
    val tvShows: List<Tv>,
)
