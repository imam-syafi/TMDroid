package com.edts.tmdroid.ui.main.menu

import androidx.annotation.DrawableRes

sealed interface GridItem {

    data class Header(
        val title: String,
    ) : GridItem

    data class IconMenu(
        val title: String,
        @DrawableRes val icon: Int,
        val action: () -> Unit,
    ) : GridItem
}
