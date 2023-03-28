package com.edts.tmdroid.ui.movie.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MovieListType : Parcelable {
    object TopRated : MovieListType()
    object Upcoming : MovieListType()
    object NowPlaying : MovieListType()
    object Popular : MovieListType()

    data class Search(
        val query: String,
    ) : MovieListType()
}
