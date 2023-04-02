package com.edts.tmdroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.edts.tmdroid.data.AuthRepository
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.ui.model.Fallback
import com.edts.tmdroid.ui.model.Queue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    mediaRepository: MediaRepository,
) : ViewModel() {

    private val watchList: LiveData<List<Queue>> = mediaRepository
        .getWatchList()
        .asLiveData()

    val state: LiveData<HomeState> = watchList
        .map {
            HomeState(
                queueList = it,
                fallback = if (it.isEmpty()) Fallback.EMPTY.copy(icon = null) else null,
            )
        }

    fun onLogout() {
        authRepository.logout()
    }
}
