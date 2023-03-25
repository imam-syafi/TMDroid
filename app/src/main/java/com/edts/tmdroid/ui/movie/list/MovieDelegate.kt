package com.edts.tmdroid.ui.movie.list

import com.edts.tmdroid.ui.model.Movie

interface MovieDelegate {
    fun onMovieClicked(movie: Movie)
}
