package com.edts.tmdroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AccountEntity.TABLE_NAME)
data class AccountEntity(
    @PrimaryKey val name: String,
    val password: String,
) {
    companion object {
        const val TABLE_NAME = "t_account"
        const val NAME_COLUMN = "name"
        const val PASSWORD_COLUMN = "password"
    }
}
