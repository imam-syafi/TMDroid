package com.edts.tmdroid.ui.ext

import androidx.core.view.isVisible
import com.edts.tmdroid.databinding.ViewFallbackBinding
import com.edts.tmdroid.ui.model.Fallback

fun ViewFallbackBinding.bind(fallback: Fallback?) {
    root.isVisible = fallback != null

    fallback?.let { (icon, message) ->
        icon?.let {
            tvMessage.setCompoundDrawables(top = it)
        }
        tvMessage.setText(message)
    }
}
