package com.edts.tmdroid.ui.ext

import androidx.appcompat.app.AlertDialog

fun AlertDialog.showDialog(isVisible: Boolean) {
    if (isShowing != isVisible) {
        if (isVisible) {
            show()
        } else {
            dismiss()
        }
    }
}
