package com.edts.tmdroid.ui.model

import androidx.recyclerview.widget.DiffUtil
import com.edts.tmdroid.data.remote.response.MovieDto
import com.edts.tmdroid.data.remote.response.TvDto
import com.edts.tmdroid.di.NetworkModule

sealed interface Media {
    val id: Int
    val overview: String
    val backdropUrl: String
    val posterUrl: String
    val popularity: Double
    val voteAverage: Double
    val voteCount: Int

    data class Movie(
        override val id: Int,
        override val overview: String,
        override val backdropUrl: String,
        override val posterUrl: String,
        override val popularity: Double,
        override val voteAverage: Double,
        override val voteCount: Int,
        val title: String,
        val releaseDate: String,
    ) : Media

    data class Tv(
        override val id: Int,
        override val overview: String,
        override val backdropUrl: String,
        override val posterUrl: String,
        override val popularity: Double,
        override val voteAverage: Double,
        override val voteCount: Int,
        val name: String,
        val firstAirDate: String,
    ) : Media

    companion object {

        val DIFFER = object : DiffUtil.ItemCallback<Media>() {

            override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
                return oldItem == newItem
            }
        }

        fun from(movieDto: MovieDto): Movie = Movie(
            id = movieDto.id,
            title = movieDto.title,
            overview = movieDto.overview ?: "Overview is undefined",
            releaseDate = movieDto.release_date ?: "Release date is undefined",
            backdropUrl = "${NetworkModule.BASE_IMG_URL}${movieDto.backdrop_path}",
            posterUrl = "${NetworkModule.BASE_IMG_URL}${movieDto.poster_path}",
            popularity = movieDto.popularity,
            voteAverage = movieDto.vote_average,
            voteCount = movieDto.vote_count,
        )

        fun from(tvDto: TvDto): Tv = Tv(
            id = tvDto.id,
            name = tvDto.name,
            overview = tvDto.overview ?: "Overview is undefined",
            firstAirDate = tvDto.first_air_date ?: "Release date is undefined",
            backdropUrl = "${NetworkModule.BASE_IMG_URL}${tvDto.backdrop_path}",
            posterUrl = "${NetworkModule.BASE_IMG_URL}${tvDto.poster_path}",
            popularity = tvDto.popularity,
            voteAverage = tvDto.vote_average,
            voteCount = tvDto.vote_count,
        )
    }
}
