package com.edts.tmdroid.data.remote.response

data class TvDto(
    val id: Int,
    val name: String,
    val overview: String?,
    val first_air_date: String?,
    val backdrop_path: String?,
    val poster_path: String?,
    val popularity: Double,
    val vote_average: Double,
    val vote_count: Int,
)
