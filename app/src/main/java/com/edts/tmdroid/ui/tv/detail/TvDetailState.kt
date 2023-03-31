package com.edts.tmdroid.ui.tv.detail

import com.edts.tmdroid.ui.model.Review
import com.edts.tmdroid.ui.model.Tv

data class TvDetailState(
    val isLoading: Boolean,
    val isSaved: Boolean,
    val tv: Tv?,
    val reviews: List<Review>,
)
