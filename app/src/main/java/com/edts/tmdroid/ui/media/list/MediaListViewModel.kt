package com.edts.tmdroid.ui.media.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.ui.model.Fallback
import com.edts.tmdroid.ui.model.Media
import com.edts.tmdroid.ui.model.MediaListType.MovieNowPlaying
import com.edts.tmdroid.ui.model.MediaListType.MoviePopular
import com.edts.tmdroid.ui.model.MediaListType.MovieTopRated
import com.edts.tmdroid.ui.model.MediaListType.MovieUpcoming
import com.edts.tmdroid.ui.model.MediaListType.TvAiringToday
import com.edts.tmdroid.ui.model.MediaListType.TvOnTheAir
import com.edts.tmdroid.ui.model.MediaListType.TvPopular
import com.edts.tmdroid.ui.model.MediaListType.TvTopRated
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MediaListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mediaRepository: MediaRepository,
) : ViewModel() {

    private val args = MediaListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    // Backing properties
    private val isLoading = MutableLiveData<Boolean>()
    private val fallback = MutableLiveData<Fallback?>()
    private val media = MutableLiveData<List<Media>>()

    val state: LiveData<MediaListState> = combineTuple(isLoading, fallback, media)
        .map { (isLoading, fallback, media) ->
            MediaListState(
                isLoading = isLoading ?: false,
                fallback = fallback,
                media = media ?: emptyList(),
            )
        }

    private val errorEmitter: EventEmitter<String> = EventEmitter()
    val errorMessage: EventSource<String> = errorEmitter

    private val call: suspend () -> Result<List<Media>, String> =
        when (val type = args.mediaListType) {
            MovieTopRated -> mediaRepository::getTopRatedMovies
            MovieUpcoming -> mediaRepository::getUpcomingMovies
            MovieNowPlaying -> mediaRepository::getNowPlayingMovies
            MoviePopular -> mediaRepository::getPopularMovies
            TvPopular -> mediaRepository::getPopularTvShows
            TvTopRated -> mediaRepository::getTopRatedTvShows
            TvOnTheAir -> mediaRepository::getOnTheAirTvShows
            TvAiringToday -> mediaRepository::getAiringTodayTvShows
        }

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true

            when (val result = call()) {
                is Ok -> {
                    result.value.let {
                        media.value = it
                        fallback.value = if (it.isEmpty()) Fallback.NO_RESULT else null
                    }
                }
                is Err -> {
                    fallback.value = Fallback.FETCH_ERROR
                    errorEmitter.emit(result.error)
                }
            }

            isLoading.value = false
        }
    }

    fun onRefresh() {
        media.value = emptyList()
        fetchData()
    }
}
