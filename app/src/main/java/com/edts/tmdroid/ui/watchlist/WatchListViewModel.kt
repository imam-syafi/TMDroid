package com.edts.tmdroid.ui.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.local.entity.QueueDao
import com.edts.tmdroid.ui.model.Queue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class WatchListViewModel @Inject constructor(
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
