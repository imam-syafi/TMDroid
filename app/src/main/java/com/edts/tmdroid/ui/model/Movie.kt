package com.edts.tmdroid.ui.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.edts.tmdroid.data.local.entity.FavoriteMovieEntity
import com.edts.tmdroid.data.remote.NetworkModule
import com.edts.tmdroid.data.remote.response.MovieDto
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

    fun toEntity(): FavoriteMovieEntity = FavoriteMovieEntity(
        id,
        title,
        overview,
        releaseDate,
        backdropUrl,
        posterUrl,
        popularity,
        voteAverage,
        voteCount,
    )

    companion object {

        val DIFFER = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }

        fun from(dto: MovieDto): Movie = Movie(
            id = dto.id,
            title = dto.title,
            overview = dto.overview ?: "Overview is undefined",
            releaseDate = dto.release_date ?: "Release date is undefined",
            backdropUrl = "${NetworkModule.BASE_IMG_URL}${dto.backdrop_path}",
            posterUrl = "${NetworkModule.BASE_IMG_URL}${dto.poster_path}",
            popularity = dto.popularity,
            voteAverage = dto.vote_average,
            voteCount = dto.vote_count,
        )

        fun from(entity: FavoriteMovieEntity): Movie = Movie(
            id = entity.id,
            title = entity.title,
            overview = entity.overview,
            releaseDate = entity.releaseDate,
            backdropUrl = entity.backdropUrl,
            posterUrl = entity.posterUrl,
            popularity = entity.popularity,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount,
        )
    }
}
