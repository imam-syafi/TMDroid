package com.edts.tmdroid.ui.media.list

import com.edts.tmdroid.ui.model.Media

data class MediaListState(
    val isLoading: Boolean,
    val media: List<Media>,
)
