package com.edts.tmdroid.ui.model

import com.edts.tmdroid.data.remote.response.PersonDetailDto

data class PersonDetail(
    val id: Int,
    val biography: String,
) {

    companion object {

        fun from(dto: PersonDetailDto): PersonDetail = PersonDetail(
            id = dto.id,
            biography = dto.biography,
        )
    }
}
