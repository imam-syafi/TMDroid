package com.edts.tmdroid.data.local.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query(
        """
            SELECT *
            FROM ${AccountEntity.TABLE_NAME}
        """,
    )
    fun getAll(): Flow<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(entity: AccountEntity)

    @Query(
        """
            SELECT EXISTS(
                SELECT *
                FROM ${AccountEntity.TABLE_NAME}
                WHERE ${AccountEntity.NAME_COLUMN} = :name
            )
        """,
    )
    suspend fun isRegistered(name: String): Boolean

    @Query(
        """
            SELECT EXISTS(
                SELECT *
                FROM ${AccountEntity.TABLE_NAME}
                WHERE ${AccountEntity.NAME_COLUMN} = :name
                AND ${AccountEntity.PASSWORD_COLUMN} = :password
            )
        """,
    )
    suspend fun isAuthenticated(name: String, password: String): Boolean
}
