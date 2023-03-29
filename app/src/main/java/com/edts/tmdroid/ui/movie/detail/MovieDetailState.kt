package com.edts.tmdroid.ui.movie.detail

import com.edts.tmdroid.ui.model.Movie

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
)
