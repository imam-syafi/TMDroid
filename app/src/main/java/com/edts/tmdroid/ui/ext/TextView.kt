package com.edts.tmdroid.ui.ext

import android.view.ViewGroup
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager

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
