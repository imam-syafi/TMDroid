package com.edts.tmdroid.ui.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.local.entity.QueueDao
import com.edts.tmdroid.ui.model.Queue
import kotlinx.coroutines.launch

class WatchListViewModel(
    private val queueDao: QueueDao,
) : ViewModel() {

    val watchList: LiveData<List<Queue>> = queueDao
        .getLatest()
        .map(Queue::from)

    fun onDelete(queue: Queue) {
        viewModelScope.launch {
            queueDao.deleteMedia(queue.mediaId, queue.mediaType)
        }
    }
}
