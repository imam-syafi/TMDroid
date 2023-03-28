package com.edts.tmdroid.data.remote.response

import com.edts.tmdroid.data.common.MediaType

data class KnownForDto(
    val id: Int,
    val media_type: MediaType,
    val title: String?,
    val name: String?,
    val poster_path: String?,
)
