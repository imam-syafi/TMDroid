package com.edts.tmdroid.data.common

import com.google.gson.annotations.SerializedName

enum class MediaType {
    @SerializedName("movie")
    Movie,

    @SerializedName("tv")
    Tv,
}
