package com.edts.tmdroid.ui.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.ui.model.Fallback
import com.edts.tmdroid.ui.model.Queue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
) : ViewModel() {

    private val watchList: LiveData<List<Queue>> = mediaRepository
        .getWatchList()
        .asLiveData()

    val state: LiveData<WatchListState> = watchList
        .map {
            WatchListState(
                watchList = it,
                fallback = if (it.isEmpty()) Fallback.EMPTY else null,
            )
        }

    fun onDelete(queue: Queue) {
        viewModelScope.launch {
            mediaRepository.removeFromQueue(queue.mediaId, queue.mediaType)
        }
    }
}
