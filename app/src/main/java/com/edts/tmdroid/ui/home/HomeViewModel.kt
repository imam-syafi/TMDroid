package com.edts.tmdroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.edts.tmdroid.data.local.entity.QueueDao
import com.edts.tmdroid.ui.model.Queue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    queueDao: QueueDao,
) : ViewModel() {

    val watchList: LiveData<List<Queue>> = queueDao
        .getLatest()
        .map(Queue::from)
}
