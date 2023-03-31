package com.edts.tmdroid.data.local.entity

import androidx.lifecycle.LiveData
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
            ORDER BY ${QueueEntity.ID_COLUMN} DESC
        """,
    )
    fun getLatest(): LiveData<List<QueueEntity>>

    @Query(
        """
            SELECT EXISTS(
                SELECT *
                FROM ${QueueEntity.TABLE_NAME}
                WHERE ${QueueEntity.MEDIA_ID_COLUMN} = :id
                AND ${QueueEntity.MEDIA_TYPE_COLUMN} = :type
            )
        """,
    )
    fun isMediaSaved(id: Int, type: MediaType): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: QueueEntity)

    @Query(
        """
            DELETE FROM ${QueueEntity.TABLE_NAME}
            WHERE ${QueueEntity.MEDIA_ID_COLUMN} = :id
            AND ${QueueEntity.MEDIA_TYPE_COLUMN} = :type
        """,
    )
    suspend fun deleteMedia(id: Int, type: MediaType)
}
