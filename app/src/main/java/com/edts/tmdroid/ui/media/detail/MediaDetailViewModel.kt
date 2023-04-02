package com.edts.tmdroid.ui.media.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.AuthRepository
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.ui.model.Fallback
import com.edts.tmdroid.ui.model.Media
import com.edts.tmdroid.ui.model.Review
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mediaRepository: MediaRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val args = MediaDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val mediaId = args.mediaId
    private val mediaType = args.mediaType

    // Backing properties
    private val isLoading = MutableLiveData<Boolean>()
    private val fallback = MutableLiveData<Fallback?>()
    private val media = MutableLiveData<Media>()

    private val isSaved: LiveData<Boolean> = mediaRepository
        .isSaved(mediaId, mediaType)
        .asLiveData()

    private val reviews: LiveData<List<Review>> = mediaRepository
        .getReviews(mediaId, mediaType)
        .asLiveData()

    private val currentUser: LiveData<String?> = authRepository
        .getLoggedInUser()
        .asLiveData()

    val state: LiveData<MediaDetailState> = combineTuple(
        isLoading,
        isSaved,
        fallback,
        media,
        reviews,
        currentUser,
    )
        .map { (isLoading, isSaved, fallback, media, reviews, currentUser) ->
            MediaDetailState(
                isLoading = isLoading ?: false,
                isSaved = isSaved ?: false,
                fallback = fallback,
                media = media,
                reviews = reviews ?: emptyList(),
                currentUser = currentUser,
            )
        }

    private val errorEmitter: EventEmitter<String> = EventEmitter()
    val errorMessage: EventSource<String> = errorEmitter

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true

            when (val result = mediaRepository.getMediaDetail(mediaId, mediaType)) {
                is Ok -> {
                    result.value.let {
                        media.value = it
                        fallback.value = null
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

    fun onToggle() {
        viewModelScope.launch {
            media.value?.let {
                if (isSaved.value == true) {
                    mediaRepository.removeFromQueue(mediaId, mediaType)
                } else {
                    mediaRepository.addToQueue(it, mediaType)
                }
            }
        }
    }
}
