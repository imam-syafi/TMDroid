package com.edts.tmdroid.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.edts.tmdroid.data.common.MediaType
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Query(
        """
            SELECT *
            FROM ${ReviewEntity.TABLE_NAME}
            WHERE ${ReviewEntity.MEDIA_ID_COLUMN} = :mediaId
            AND ${ReviewEntity.MEDIA_TYPE_COLUMN} = :mediaType
        """,
    )
    fun getByMedia(mediaId: Int, mediaType: MediaType): Flow<List<ReviewEntity>>

    @Upsert
    suspend fun upsert(entity: ReviewEntity)

    @Delete
    suspend fun delete(entity: ReviewEntity)
}
