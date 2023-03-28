package com.edts.tmdroid.ui.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.ui.model.Movie
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val tmdbService: TmdbService,
) : ViewModel() {

    private val _state = MutableLiveData(MovieListState())
    val state: LiveData<MovieListState> = _state

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)

            // Call API service
            val response = tmdbService.getTopRatedMovies()
            val movies = response.results.map(Movie::from)

            // Happy path
            _state.value = _state.value?.copy(
                isLoading = false,
                movies = movies,
            )
        }
    }
}
