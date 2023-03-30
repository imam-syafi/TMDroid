package com.edts.tmdroid.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert

@Dao
interface ReviewDao {

    @Upsert
    suspend fun upsert(entity: ReviewEntity)

    @Delete
    suspend fun delete(entity: ReviewEntity)
}
