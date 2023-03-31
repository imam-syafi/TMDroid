package com.edts.tmdroid.ui.movie.detail

import com.edts.tmdroid.ui.model.Movie
import com.edts.tmdroid.ui.model.Review

data class MovieDetailState(
    val isLoading: Boolean,
    val isSaved: Boolean,
    val movie: Movie?,
    val reviews: List<Review>,
)
