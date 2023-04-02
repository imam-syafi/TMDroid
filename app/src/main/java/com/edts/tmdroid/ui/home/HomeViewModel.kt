package com.edts.tmdroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.AuthRepository
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.ui.model.Fallback
import com.edts.tmdroid.ui.model.Queue
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    mediaRepository: MediaRepository,
) : ViewModel() {

    private val watchList: LiveData<List<Queue>> = mediaRepository
        .getWatchList()
        .asLiveData()

    private val currentUser: LiveData<String?> = authRepository
        .getLoggedInUser()
        .asLiveData()

    val state: LiveData<HomeState> = combineTuple(watchList, currentUser)
        .map { (watchList, currentUser) ->
            HomeState(
                queueList = watchList ?: emptyList(),
                fallback = if (watchList?.isEmpty() == true) {
                    Fallback.EMPTY.copy(icon = null)
                } else {
                    null
                },
                currentUser = currentUser,
            )
        }

    fun onLogout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
