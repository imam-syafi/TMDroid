package com.edts.tmdroid.ui.movie.detail

import com.edts.tmdroid.ui.model.Movie
import com.edts.tmdroid.ui.model.Review

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val reviews: List<Review> = emptyList(),
)
