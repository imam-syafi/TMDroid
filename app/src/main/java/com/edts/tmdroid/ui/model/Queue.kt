package com.edts.tmdroid.ui.model

import androidx.recyclerview.widget.DiffUtil
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.data.local.entity.QueueEntity

data class Queue(
    val id: Int,
    val mediaId: Int,
    val mediaType: MediaType,
    val title: String,
    val posterUrl: String,
) {

    companion object {

        val DIFFER = object : DiffUtil.ItemCallback<Queue>() {

            override fun areItemsTheSame(oldItem: Queue, newItem: Queue): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Queue, newItem: Queue): Boolean {
                return oldItem == newItem
            }
        }

        fun from(entity: QueueEntity): Queue = Queue(
            id = entity.id,
            mediaId = entity.media_id,
            mediaType = entity.media_type,
            title = entity.title,
            posterUrl = entity.poster_url,
        )

        fun from(entities: List<QueueEntity>): List<Queue> = entities.map(Queue::from)
    }
}
