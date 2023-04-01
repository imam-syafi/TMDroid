package com.edts.tmdroid.ui.ext

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadFromUrl(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(ColorDrawable(Color.GRAY))
        .into(this)
}
