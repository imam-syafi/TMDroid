package com.edts.tmdroid.ui.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
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

inline fun TextInputLayout.bind(
    initialValue: String?,
    @StringRes errorText: Int,
    crossinline validate: (String) -> Boolean,
    crossinline onChange: (value: String, isValid: Boolean) -> Unit,
) {
    isSaveEnabled = false

    editText?.let { et ->
        et.isSaveEnabled = false
        et.doAfterTextChanged { editable ->
            val text = editable.toString()
            val isValid = validate(text)

            error = if (isValid) null else context.getString(errorText)

            onChange(text, isValid)
        }

        if (!initialValue.isNullOrBlank()) {
            et.setText(initialValue)
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
