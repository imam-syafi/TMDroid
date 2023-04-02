package com.edts.tmdroid.data.local.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edts.tmdroid.data.common.MediaType
import kotlinx.coroutines.flow.Flow

@Dao
interface QueueDao {

    @Query(
        """
            SELECT *
            FROM ${QueueEntity.TABLE_NAME}
            WHERE ${QueueEntity.USER_COLUMN} = :user
            ORDER BY ${QueueEntity.ID_COLUMN} DESC
        """,
    )
    fun getLatest(user: String): Flow<List<QueueEntity>>

    @Query(
        """
            SELECT EXISTS(
                SELECT *
                FROM ${QueueEntity.TABLE_NAME}
                WHERE ${QueueEntity.MEDIA_ID_COLUMN} = :id
                AND ${QueueEntity.MEDIA_TYPE_COLUMN} = :type
                AND ${QueueEntity.USER_COLUMN} = :user
            )
        """,
    )
    fun isMediaSaved(id: Int, type: MediaType, user: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: QueueEntity)

    @Query(
        """
            DELETE FROM ${QueueEntity.TABLE_NAME}
            WHERE ${QueueEntity.MEDIA_ID_COLUMN} = :id
            AND ${QueueEntity.MEDIA_TYPE_COLUMN} = :type
            AND ${QueueEntity.USER_COLUMN} = :user
        """,
    )
    suspend fun deleteMedia(id: Int, type: MediaType, user: String)
}
