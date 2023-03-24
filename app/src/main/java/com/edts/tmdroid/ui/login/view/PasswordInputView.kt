package com.edts.tmdroid.ui.login.view

import android.content.Context
import android.content.res.ColorStateList
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.edts.tmdroid.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PasswordInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : TextInputLayout(context, attrs) {

    var isValid = false
    var onChange: ((password: String, isValid: Boolean) -> Unit)? = null

    init {
        hint = context.getString(R.string.password)
        endIconMode = END_ICON_PASSWORD_TOGGLE

        addView(
            /**
             * Deliberately using TextInputLayout's `context` to create the view. This will allow
             * TextInputLayout to pass along the appropriate styling to the TextInputEditText.
             */
            TextInputEditText(this.context).apply {
                inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                transformationMethod = PasswordTransformationMethod.getInstance()
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
            val password = editable.toString()
            isValid = password.isNotBlank()

            error = if (isValid) null else context.getString(R.string.password_invalid)
            onChange?.invoke(password, isValid)
        }
    }
}
