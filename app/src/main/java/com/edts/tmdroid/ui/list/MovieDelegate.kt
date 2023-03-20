package com.edts.tmdroid.ui.list

import com.edts.tmdroid.data.Movie

interface MovieDelegate {
    fun onMovieClicked(movie: Movie)
}
