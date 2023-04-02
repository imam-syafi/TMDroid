package com.edts.tmdroid.ui.watchlist

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.R
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.data.common.QueueSortOption.ALPHABETICALLY
import com.edts.tmdroid.data.common.QueueSortOption.MOVIE_THEN_TV
import com.edts.tmdroid.data.common.QueueSortOption.NEWEST
import com.edts.tmdroid.data.common.QueueSortOption.OLDEST
import com.edts.tmdroid.ui.model.Fallback
import com.edts.tmdroid.ui.model.Queue
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
) : ViewModel() {

    // Backing properties
    private val selectedPosition = MutableLiveData<Int>()
    private val sortOption = MutableLiveData(NEWEST)

    val state: LiveData<WatchListState> = sortOption
        .switchMap { option ->
            val watchList = mediaRepository
                .getWatchList(option)
                .asLiveData()

            combineTuple(watchList, selectedPosition)
                .map { (watchList, selectedPosition) ->
                    WatchListState(
                        watchList = watchList ?: emptyList(),
                        fallback = if (watchList?.isEmpty() == true) Fallback.EMPTY else null,
                        selectedPosition = selectedPosition ?: 0,
                    )
                }
        }

    fun onDelete(queue: Queue) {
        viewModelScope.launch {
            mediaRepository.removeFromQueue(queue.mediaId, queue.mediaType)
        }
    }

    fun onSortChange(@StringRes id: Int) {
        when (id) {
            R.string.most_recently -> {
                sortOption.value = NEWEST
                selectedPosition.value = 0
            }
            R.string.least_recently -> {
                sortOption.value = OLDEST
                selectedPosition.value = 1
            }
            R.string.alphabetically -> {
                sortOption.value = ALPHABETICALLY
                selectedPosition.value = 2
            }
            R.string.movie_then_tv -> {
                sortOption.value = MOVIE_THEN_TV
                selectedPosition.value = 3
            }
        }
    }
}
