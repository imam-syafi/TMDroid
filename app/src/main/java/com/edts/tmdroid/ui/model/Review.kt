package com.edts.tmdroid.ui.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.data.local.entity.ReviewEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val id: Int,
    val mediaId: Int,
    val mediaType: MediaType,
    val name: String,
    val comment: String,
    val isEditable: Boolean,
) : Parcelable {

    fun toReviewEntity(): ReviewEntity = ReviewEntity(
        id = id,
        media_id = mediaId,
        media_type = mediaType,
        name = name,
        comment = comment,
    )

    companion object {

        val DIFFER = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }

        fun from(entity: ReviewEntity, isEditable: Boolean): Review = Review(
            id = entity.id,
            mediaId = entity.media_id,
            mediaType = entity.media_type,
            name = entity.name,
            comment = entity.comment,
            isEditable = isEditable,
        )
    }
}
