package com.edts.tmdroid.data.remote.response

data class GetMoviesResponse(
    val results: List<MovieDto>,
)

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String?,
    val release_date: String?,
    val backdrop_path: String?,
    val poster_path: String?,
    val popularity: Double,
    val vote_average: Double,
    val vote_count: Int,
)
