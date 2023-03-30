package com.edts.tmdroid.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewEditorViewModel(
) : ViewModel() {

    private val _state = MutableLiveData(ReviewEditorState())
    val state: LiveData<ReviewEditorState> = _state

    fun onNameChange(name: String, isNameValid: Boolean) {
        _state.value = _state.value?.copy(name = name, isNameValid = isNameValid)
    }

    fun onCommentChange(comment: String, isCommentValid: Boolean) {
        _state.value = _state.value?.copy(comment = comment, isCommentValid = isCommentValid)
    }
}
