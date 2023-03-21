package com.edts.tmdroid.data

import android.os.Parcelable
import com.edts.tmdroid.service.TmdbService
import com.edts.tmdroid.service.response.MovieDto
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

        fun from(dto: MovieDto): Movie = Movie(
            id = dto.id,
            title = dto.title,
            overview = dto.overview ?: "Overview is undefined",
            releaseDate = dto.release_date ?: "Release date is undefined",
            backdropUrl = "${TmdbService.BASE_IMG_URL}${dto.backdrop_path}",
            posterUrl = "${TmdbService.BASE_IMG_URL}${dto.poster_path}",
            popularity = dto.popularity,
            voteAverage = dto.vote_average,
            voteCount = dto.vote_count,
        )
    }
}
