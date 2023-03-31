package com.edts.tmdroid.ui.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.edts.tmdroid.data.remote.response.TvDto
import com.edts.tmdroid.di.NetworkModule
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tv(
    val id: Int,
    val name: String,
    val overview: String,
    val firstAirDate: String,
    val backdropUrl: String,
    val posterUrl: String,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
) : Parcelable {

    companion object {

        val DIFFER = object : DiffUtil.ItemCallback<Tv>() {

            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem == newItem
            }
        }

        fun from(dto: TvDto): Tv = Tv(
            id = dto.id,
            name = dto.name,
            overview = dto.overview ?: "Overview is undefined",
            firstAirDate = dto.first_air_date ?: "Release date is undefined",
            backdropUrl = "${NetworkModule.BASE_IMG_URL}${dto.backdrop_path}",
            posterUrl = "${NetworkModule.BASE_IMG_URL}${dto.poster_path}",
            popularity = dto.popularity,
            voteAverage = dto.vote_average,
            voteCount = dto.vote_count,
        )
    }
}
