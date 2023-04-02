package com.edts.tmdroid.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    val current: String?
        get() = sharedPreferences.getString(LOGGED_IN_USER_KEY, null)

    fun save(name: String) {
        sharedPreferences.edit(commit = true) {
            putString(LOGGED_IN_USER_KEY, name)
        }
    }

    fun remove() {
        sharedPreferences.edit(commit = true) {
            remove(LOGGED_IN_USER_KEY)
        }
    }

    companion object {
        private const val LOGGED_IN_USER_KEY = "LOGGED_IN_USER_KEY"
    }
}
