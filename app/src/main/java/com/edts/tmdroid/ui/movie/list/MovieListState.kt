package com.edts.tmdroid.ui.movie.list

import com.edts.tmdroid.ui.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
)
