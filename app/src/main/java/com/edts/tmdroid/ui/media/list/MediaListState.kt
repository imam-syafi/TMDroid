package com.edts.tmdroid.ui.media.list

import com.edts.tmdroid.ui.model.Fallback
import com.edts.tmdroid.ui.model.Media

data class MediaListState(
    val isLoading: Boolean,
    val fallback: Fallback?,
    val media: List<Media>,
)
