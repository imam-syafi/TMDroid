package com.edts.tmdroid.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.edts.tmdroid.R

data class Fallback(
    @DrawableRes val icon: Int?,
    @StringRes val message: Int,
) {

    companion object {

        val FETCH_ERROR = Fallback(
            icon = R.drawable.ic_error,
            message = R.string.failed_to_load_data,
        )

        val NO_RESULT = Fallback(
            icon = R.drawable.ic_no_result,
            message = R.string.no_result,
        )

        val EMPTY = Fallback(
            icon = R.drawable.ic_empty,
            message = R.string.still_empty,
        )
    }
}
