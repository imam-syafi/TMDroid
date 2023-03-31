package com.edts.tmdroid.ui.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.edts.tmdroid.data.remote.response.PersonDto
import com.edts.tmdroid.di.NetworkModule
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val id: Int,
    val name: String,
    val profileUrl: String,
    val knownFor: List<KnownFor>,
) : Parcelable {

    companion object {

        val DIFFER = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem == newItem
            }
        }

        fun from(dto: PersonDto): Person = Person(
            id = dto.id,
            name = dto.name,
            profileUrl = "${NetworkModule.BASE_IMG_URL}${dto.profile_path}",
            knownFor = dto.known_for.map(KnownFor::from),
        )
    }
}
