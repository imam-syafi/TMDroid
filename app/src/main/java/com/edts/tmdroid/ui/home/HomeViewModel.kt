package com.edts.tmdroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.edts.tmdroid.data.local.entity.QueueDao
import com.edts.tmdroid.ui.model.Queue

class HomeViewModel(
    queueDao: QueueDao,
) : ViewModel() {

    val watchList: LiveData<List<Queue>> = queueDao
        .getLatest()
        .map(Queue::from)
}
