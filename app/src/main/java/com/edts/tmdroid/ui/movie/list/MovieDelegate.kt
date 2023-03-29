package com.edts.tmdroid.ui.movie.list

import com.edts.tmdroid.ui.model.Movie

fun interface MovieDelegate {
    fun onMovieClicked(movie: Movie)
}
