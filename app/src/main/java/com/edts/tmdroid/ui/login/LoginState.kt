package com.edts.tmdroid.ui.login

data class LoginState(
    val isLoading: Boolean,
    val name: String,
    val isNameValid: Boolean,
    val password: String,
    val isPasswordValid: Boolean,
) {
    val canSubmit = isNameValid && isPasswordValid
}
