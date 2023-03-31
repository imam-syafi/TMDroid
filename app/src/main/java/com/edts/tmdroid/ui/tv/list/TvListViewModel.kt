package com.edts.tmdroid.ui.tv.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.data.remote.response.GetTvShowsResponse
import com.edts.tmdroid.ui.model.Tv
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TvListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    tmdbService: TmdbService,
) : ViewModel() {

    private val args = TvListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    // Backing properties
    private val isLoading = MutableLiveData<Boolean>()
    private val tvShows = MutableLiveData<List<Tv>>()

    val state: LiveData<TvListState> = combineTuple(isLoading, tvShows)
        .map { (isLoading, tvShows) ->
            TvListState(
                isLoading = isLoading ?: false,
                tvShows = tvShows ?: emptyList(),
            )
        }

    private val call: suspend () -> GetTvShowsResponse

    init {
        call = when (args.tvListType) {
            TvListType.Popular -> tmdbService::getPopularTvShows
            TvListType.TopRated -> tmdbService::getTopRatedTvShows
            TvListType.OnTheAir -> tmdbService::getOnTheAirTvShows
            TvListType.AiringToday -> tmdbService::getAiringTodayTvShows
        }

        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true

            // Call API service
            val response = call()

            // Happy path
            tvShows.value = response.results.map(Tv::from)

            isLoading.value = false
        }
    }

    fun onRefresh() {
        tvShows.value = emptyList()
        fetchData()
    }
}
