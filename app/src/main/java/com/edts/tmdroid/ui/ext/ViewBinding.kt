package com.edts.tmdroid.ui.ext

import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

fun ViewBinding.buildSnack(message: String): Snackbar {
    return Snackbar.make(root, message, Snackbar.LENGTH_LONG)
}

fun ViewBinding.buildSnack(@StringRes resId: Int): Snackbar {
    return buildSnack(root.context.getString(resId))
}

fun ViewBinding.showSnack(message: String) {
    buildSnack(message).show()
}

fun ViewBinding.showSnack(@StringRes resId: Int) {
    showSnack(root.context.getString(resId))
}
