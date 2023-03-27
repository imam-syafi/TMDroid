package com.edts.tmdroid.ui.home.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed interface GridItem {

    data class Header(
        @StringRes val title: Int,
    ) : GridItem

    data class IconMenu(
        @StringRes val title: Int,
        @DrawableRes val icon: Int,
        val onClick: () -> Unit,
    ) : GridItem
}
