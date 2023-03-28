package com.edts.tmdroid.ui.ext

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.edts.tmdroid.R

fun AppCompatActivity.getPrefs(): SharedPreferences {
    return getSharedPreferences(
        getString(R.string.shared_prefs_key),
        Context.MODE_PRIVATE,
    )
}
