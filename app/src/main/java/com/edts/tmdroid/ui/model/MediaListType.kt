package com.edts.tmdroid.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MediaListType : Parcelable {
    object MovieTopRated : MediaListType()
    object MovieUpcoming : MediaListType()
    object MovieNowPlaying : MediaListType()
    object MoviePopular : MediaListType()

    object TvPopular : MediaListType()
    object TvTopRated : MediaListType()
    object TvOnTheAir : MediaListType()
    object TvAiringToday : MediaListType()
}
