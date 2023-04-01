package com.edts.tmdroid.ui.ext

import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager

fun TextView.setCompoundDrawables(
    @DrawableRes left: Int = 0,
    @DrawableRes top: Int = 0,
    @DrawableRes right: Int = 0,
    @DrawableRes bottom: Int = 0,
) {
    setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
}

fun TextView.setToggleMaxLines(initial: Int) {
    val transition = ChangeBounds().apply {
        duration = 200
        interpolator = FastOutSlowInInterpolator()
    }

    setOnClickListener {
        TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
        maxLines = if (maxLines > initial) initial else Int.MAX_VALUE
    }
}
