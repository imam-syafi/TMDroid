package com.edts.tmdroid.data.remote.response

import com.edts.tmdroid.data.common.Gender

data class PersonDetailDto(
    val id: Int,
    val biography: String,
    val gender: Gender,
    val known_for_department: String,
    val birthday: String?,
    val deathday: String?,
    val place_of_birth: String?,
    val also_known_as: List<String>,
)
