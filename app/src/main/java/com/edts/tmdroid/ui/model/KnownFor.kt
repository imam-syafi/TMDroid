package com.edts.tmdroid.ui.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.data.remote.response.KnownForDto
import com.edts.tmdroid.di.NetworkModule
import kotlinx.parcelize.Parcelize

@Parcelize
data class KnownFor(
    val id: Int,
    val mediaType: MediaType,
    val title: String,
    val posterUrl: String,
) : Parcelable {

    companion object {

        val DIFFER = object : DiffUtil.ItemCallback<KnownFor>() {

            override fun areItemsTheSame(oldItem: KnownFor, newItem: KnownFor): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: KnownFor, newItem: KnownFor): Boolean {
                return oldItem == newItem
            }
        }

        fun from(dto: KnownForDto): KnownFor {
            return KnownFor(
                id = dto.id,
                mediaType = dto.media_type,
                title = dto.title ?: dto.name ?: "Title is undefined",
                posterUrl = "${NetworkModule.BASE_IMG_URL}${dto.poster_path}",
            )
        }
    }
}
