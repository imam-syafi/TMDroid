package com.edts.tmdroid.ui.login.view

import android.content.Context
import android.content.res.ColorStateList
import android.text.InputType
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.content.res.ResourcesCompat
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
}
