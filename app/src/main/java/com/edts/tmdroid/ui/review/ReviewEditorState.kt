package com.edts.tmdroid.ui.review

data class ReviewEditorState(
    val name: String,
    val isNameValid: Boolean,
    val comment: String,
    val isCommentValid: Boolean,
) {
    val canSubmit = isNameValid && isCommentValid
}
