package com.edts.tmdroid.ui.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.data.remote.response.GetMoviesResponse
import com.edts.tmdroid.ui.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MovieListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    tmdbService: TmdbService,
) : ViewModel() {

    private val args = MovieListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _state = MutableLiveData(MovieListState())
    val state: LiveData<MovieListState> = _state

    private val call: suspend () -> GetMoviesResponse

    init {
        call = when (val type = args.movieListType) {
            MovieListType.TopRated -> tmdbService::getTopRatedMovies
            MovieListType.Upcoming -> tmdbService::getUpcomingMovies
            MovieListType.NowPlaying -> tmdbService::getNowPlayingMovies
            MovieListType.Popular -> tmdbService::getPopularMovies
            is MovieListType.Search -> suspend { tmdbService.searchMovies(type.query) }
        }

        fetchData()
    }

    private fun fetchData() {
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

    fun onRefresh() {
        _state.value = _state.value?.copy(movies = emptyList())
        fetchData()
    }
}
