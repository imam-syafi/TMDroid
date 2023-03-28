package com.edts.tmdroid.ui.ext

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadFromUrl(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}
