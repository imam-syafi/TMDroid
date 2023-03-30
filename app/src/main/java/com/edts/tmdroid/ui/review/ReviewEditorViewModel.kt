package com.edts.tmdroid.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.data.local.entity.ReviewDao
import com.edts.tmdroid.data.local.entity.ReviewEntity
import kotlinx.coroutines.launch

class ReviewEditorViewModel(
    private val movieId: Int,
    private val reviewDao: ReviewDao,
) : ViewModel() {

    private val _state = MutableLiveData(ReviewEditorState())
    val state: LiveData<ReviewEditorState> = _state

    fun onNameChange(name: String, isNameValid: Boolean) {
        _state.value = _state.value?.copy(name = name, isNameValid = isNameValid)
    }

    fun onCommentChange(comment: String, isCommentValid: Boolean) {
        _state.value = _state.value?.copy(comment = comment, isCommentValid = isCommentValid)
    }

    fun onSubmit(name: String, comment: String) {
        viewModelScope.launch {
            val entity = ReviewEntity(
                media_id = movieId,
                media_type = MediaType.Movie,
                name = name,
                comment = comment,
            )

            reviewDao.upsert(entity)
        }
    }
}
