package com.edts.tmdroid.ui.ext

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addDivider(@DrawableRes resId: Int) {
    val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
    val drawable = ContextCompat.getDrawable(context, resId)
    drawable?.let(decoration::setDrawable)
    addItemDecoration(decoration)
}
