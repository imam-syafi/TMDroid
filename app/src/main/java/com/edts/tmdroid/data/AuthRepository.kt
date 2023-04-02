package com.edts.tmdroid.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.edts.tmdroid.data.local.entity.AccountDao
import com.edts.tmdroid.data.local.entity.AccountEntity
import com.edts.tmdroid.ui.model.LoginResult
import javax.inject.Inject
import kotlinx.coroutines.delay

class AuthRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val accountDao: AccountDao,
) {

    fun getLoggedInUser(): String? {
        return sharedPreferences.getString(LOGGED_IN_USER_KEY, null)
    }

    suspend fun register(name: String, password: String) {
        // Simulate network call
        delay(1000)

        val entity = AccountEntity(name, password)
        accountDao.save(entity)
    }

    suspend fun login(name: String, password: String): LoginResult {
        // Simulate network call
        delay(2000)

        return when {
            accountDao.isRegistered(name).not() -> LoginResult.NOT_REGISTERED
            accountDao.isAuthenticated(name, password).not() -> LoginResult.PASSWORD_INCORRECT
            else -> {
                sharedPreferences.edit(commit = true) {
                    putString(LOGGED_IN_USER_KEY, name)
                }

                LoginResult.OK
            }
        }
    }

    fun logout() {
        sharedPreferences.edit(commit = true) {
            remove(LOGGED_IN_USER_KEY)
        }
    }

    companion object {
        private const val LOGGED_IN_USER_KEY = "LOGGED_IN_USER_KEY"
    }
}
