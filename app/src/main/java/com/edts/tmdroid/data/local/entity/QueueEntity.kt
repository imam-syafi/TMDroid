package com.edts.tmdroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edts.tmdroid.data.common.MediaType

@Entity(tableName = QueueEntity.TABLE_NAME)
data class QueueEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val user: String,
    val media_id: Int,
    val media_type: MediaType,
    val title: String,
    val poster_url: String,
) {
    companion object {
        const val TABLE_NAME = "t_queue"
        const val ID_COLUMN = "id"
        const val USER_COLUMN = "user"
        const val MEDIA_ID_COLUMN = "media_id"
        const val MEDIA_TYPE_COLUMN = "media_type"
    }
}
