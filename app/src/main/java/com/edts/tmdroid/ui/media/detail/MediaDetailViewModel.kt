package com.edts.tmdroid.ui.media.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.ui.model.Media
import com.edts.tmdroid.ui.model.Review
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mediaRepository: MediaRepository,
) : ViewModel() {

    private val args = MediaDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val mediaId = args.mediaId
    private val mediaType = args.mediaType

    // Backing properties
    private val isLoading = MutableLiveData<Boolean>()
    private val media = MutableLiveData<Media>()

    private val isSaved: LiveData<Boolean> = mediaRepository
        .isSaved(mediaId, mediaType)
        .asLiveData()

    private val reviews: LiveData<List<Review>> = mediaRepository
        .getReviews(mediaId, mediaType)
        .asLiveData()

    val state: LiveData<MediaDetailState> = combineTuple(isLoading, isSaved, media, reviews)
        .map { (isLoading, isSaved, media, reviews) ->
            MediaDetailState(
                isLoading = isLoading ?: false,
                isSaved = isSaved ?: false,
                media = media,
                reviews = reviews ?: emptyList(),
            )
        }

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true

            when (val result = mediaRepository.getMediaDetail(mediaId, mediaType)) {
                is Ok -> result.value.let(media::setValue)
                is Err -> {
                    // TODO: Handle error
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
