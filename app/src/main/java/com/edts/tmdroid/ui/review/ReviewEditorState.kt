package com.edts.tmdroid.ui.review

data class ReviewEditorState(
    val name: String = "",
    val isNameValid: Boolean = false,
    val comment: String = "",
    val isCommentValid: Boolean = false,
) {
    val canSubmit = isNameValid && isCommentValid
}
