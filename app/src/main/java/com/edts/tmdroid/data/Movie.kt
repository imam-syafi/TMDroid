package com.edts.tmdroid.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val backdropUrl: String,
    val posterUrl: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
) : Parcelable {
    companion object {
    }
}
