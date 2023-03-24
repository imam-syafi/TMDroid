package com.edts.tmdroid.ui.login.view

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Parcelable
import android.text.InputType
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import com.edts.tmdroid.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EmailInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : TextInputLayout(context, attrs) {

    var isValid = false
    var onChange: ((email: String, isValid: Boolean) -> Unit)? = null

    init {
        hint = context.getString(R.string.email)
        isErrorEnabled = true
        isSaveEnabled = true
        error = null

        addView(
            /**
             * Deliberately using TextInputLayout's `context` to create the view. This will allow
             * TextInputLayout to pass along the appropriate styling to the TextInputEditText.
             */
            TextInputEditText(this.context).apply {
                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                setTextColor(
                    ColorStateList.valueOf(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            context.theme,
                        )
                    )
                )
            },
        )

        editText?.doAfterTextChanged { editable ->
            val email = editable.toString()
            isValid = email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

            error = if (isValid) null else context.getString(R.string.email_invalid)
            onChange?.invoke(email, isValid)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        return bundleOf(
            "inputText" to editText?.text.toString(),
            "superState" to super.onSaveInstanceState()
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val newState = if (state is Bundle) {
            editText?.setText(state.getString("inputText"))
            state.getParcelable("superState")
        } else {
            state
        }

        super.onRestoreInstanceState(newState)
    }
}
