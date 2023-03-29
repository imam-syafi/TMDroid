package com.edts.tmdroid.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.ui.model.Movie
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val tmdbService: TmdbService,
) : ViewModel() {

    private val _state = MutableLiveData(MovieDetailState())
    val state: LiveData<MovieDetailState> = _state

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)

            // Call API service
            val response = tmdbService.getMovie(movieId)
            val movie = response.let(Movie::from)

            // Happy path
            _state.value = _state.value?.copy(
                isLoading = false,
                movie = movie,
            )
        }
    }
}
