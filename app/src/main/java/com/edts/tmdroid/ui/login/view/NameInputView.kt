package com.edts.tmdroid.ui.login.view

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import com.edts.tmdroid.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NameInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : TextInputLayout(context, attrs) {

    init {
        hint = context.getString(R.string.name)
        endIconMode = END_ICON_CLEAR_TEXT

        addView(
            /**
             * Deliberately using TextInputLayout's `context` to create the view. This will allow
             * TextInputLayout to pass along the appropriate styling to the TextInputEditText.
             */
            TextInputEditText(this.context).apply {
                inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            },
        )
    }
}
