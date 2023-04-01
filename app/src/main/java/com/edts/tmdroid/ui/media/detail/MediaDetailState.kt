package com.edts.tmdroid.ui.media.detail

import com.edts.tmdroid.ui.model.Media
import com.edts.tmdroid.ui.model.Review

data class MediaDetailState(
    val isLoading: Boolean,
    val isSaved: Boolean,
    val media: Media?,
    val reviews: List<Review>,
)
