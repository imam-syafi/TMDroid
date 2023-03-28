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

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(@StringRes resId: Int) {
    showToast(getString(resId))
}
