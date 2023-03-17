package com.edts.tmdroid.ui.main.menu

import androidx.annotation.DrawableRes

data class IconMenu(
    val title: String,
    @DrawableRes val icon: Int,
    val action: () -> Unit,
)
