package com.edts.tmdroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edts.tmdroid.data.common.MediaType

@Entity(tableName = ReviewEntity.TABLE_NAME)
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val media_id: Int,
    val media_type: MediaType,
    val name: String,
    val comment: String,
) {

    companion object {
        const val TABLE_NAME = "t_review"
        const val ID_COLUMN = "id"
        const val MEDIA_ID_COLUMN = "media_id"
        const val MEDIA_TYPE_COLUMN = "media_type"
    }
}
