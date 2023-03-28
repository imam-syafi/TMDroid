package com.edts.tmdroid.ui.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.data.remote.response.GetMoviesResponse
import com.edts.tmdroid.ui.model.Movie
import kotlinx.coroutines.launch

class MovieListViewModel(
    movieListType: MovieListType,
    tmdbService: TmdbService,
) : ViewModel() {

    private val _state = MutableLiveData(MovieListState())
    val state: LiveData<MovieListState> = _state

    init {
        val call = when (movieListType) {
            MovieListType.TopRated -> tmdbService::getTopRatedMovies
            MovieListType.Upcoming -> tmdbService::getUpcomingMovies
            MovieListType.NowPlaying -> tmdbService::getNowPlayingMovies
            MovieListType.Popular -> tmdbService::getPopularMovies
        }

        fetchData(call)
    }

    private fun fetchData(
        call: suspend () -> GetMoviesResponse,
    ) {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)

            // Call API service
            val response = call()
            val movies = response.results.map(Movie::from)

            // Happy path
            _state.value = _state.value?.copy(
                isLoading = false,
                movies = movies,
            )
        }
    }
}
