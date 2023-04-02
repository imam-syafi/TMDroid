package com.edts.tmdroid.data

import com.edts.tmdroid.data.local.SessionManager
import com.edts.tmdroid.data.local.entity.AccountDao
import com.edts.tmdroid.data.local.entity.AccountEntity
import com.edts.tmdroid.ui.model.LoginResult
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class AuthRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val sessionManager: SessionManager,
) {

    fun getLoggedInUser(): Flow<String?> {
        return sessionManager.current
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
                sessionManager.save(name)

                LoginResult.OK
            }
        }
    }

    suspend fun logout() {
        sessionManager.remove()
    }
}
