package com.edts.tmdroid.ui.ext

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.edts.tmdroid.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.setupOptionsMenu(
    @MenuRes menuRes: Int,
    onMenuItemSelected: (Int) -> Boolean,
) {
    val menuHost: MenuHost = requireActivity()

    menuHost.addMenuProvider(
        object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(menuRes, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return onMenuItemSelected(menuItem.itemId)
            }
        },
        viewLifecycleOwner,
        Lifecycle.State.RESUMED,
    )
}

fun Fragment.showErrorAlert(
    message: String,
    onRetry: () -> Unit,
) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.failed_to_load_data)
        .setMessage(getString(R.string.cause, message))
        .setPositiveButton(R.string.try_again) { _, _ ->
            onRetry()
        }
        .show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(@StringRes resId: Int) {
    showToast(getString(resId))
}
