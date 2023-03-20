package com.edts.tmdroid.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.onEndIconClick(
    onSubmit: (text: String) -> Unit,
) {
    setEndIconOnClickListener {
        editText?.let {
            it.hideKeyboard()
            it.resetFocus()
            onSubmit(it.text.toString())
        }
    }
}

fun EditText.on(
    actionId: Int,
    onSubmit: (text: String) -> Unit,
) {
    setOnEditorActionListener { _, receivedActionId, _ ->
        if (actionId == receivedActionId) {
            hideKeyboard()
            resetFocus()
            onSubmit(text.toString())
        }

        true
    }
}

fun EditText.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.resetFocus() {
    // isFocusableInTouchMode = false
    clearFocus()
    // isFocusableInTouchMode = true
}
