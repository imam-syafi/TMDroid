package com.edts.tmdroid.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.data.local.entity.ReviewEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mediaRepository: MediaRepository,
) : ViewModel() {

    private val args = ReviewEditorFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val mediaId = args.mediaId
    private val mediaType = args.mediaType
    private val review = args.review

    private val _state = MutableLiveData(
        ReviewEditorState(
            name = review?.name ?: "",
            comment = review?.comment ?: "",
        ),
    )
    val state: LiveData<ReviewEditorState> = _state

    fun onNameChange(name: String, isNameValid: Boolean) {
        _state.value = _state.value?.copy(name = name, isNameValid = isNameValid)
    }

    fun onCommentChange(comment: String, isCommentValid: Boolean) {
        _state.value = _state.value?.copy(comment = comment, isCommentValid = isCommentValid)
    }

    fun onSubmit(name: String, comment: String) {
        viewModelScope.launch {
            val entity = review
                ?.copy(name = name, comment = comment)
                ?.toReviewEntity()
                ?: ReviewEntity(
                    media_id = mediaId,
                    media_type = mediaType,
                    name = name,
                    comment = comment,
                )

            mediaRepository.upsertReview(entity)
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            review
                ?.toReviewEntity()
                ?.let {
                    mediaRepository.deleteReview(it)
                }
        }
    }
}
