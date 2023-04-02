package com.edts.tmdroid.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.data.local.entity.ReviewEntity
import com.zhuinden.livedatacombinetuplekt.combineTuple
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
    private val currentUser = args.currentUser

    private val initialName = review?.name ?: currentUser
    private val initialComment = review?.comment ?: ""

    // Backing properties
    private val name = MutableLiveData<String>()
    private val isNameValid = MutableLiveData<Boolean>()
    private val comment = MutableLiveData<String>()
    private val isCommentValid = MutableLiveData<Boolean>()

    val state: LiveData<ReviewEditorState> =
        combineTuple(name, isNameValid, comment, isCommentValid)
            .map { (name, isNameValid, comment, isCommentValid) ->
                ReviewEditorState(
                    name = name ?: initialName,
                    isNameValid = isNameValid ?: false,
                    comment = comment ?: initialComment,
                    isCommentValid = isCommentValid ?: false,
                )
            }

    fun onNameChange(name: String, isNameValid: Boolean) {
        this.name.value = name
        this.isNameValid.value = isNameValid
    }

    fun onCommentChange(comment: String, isCommentValid: Boolean) {
        this.comment.value = comment
        this.isCommentValid.value = isCommentValid
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
