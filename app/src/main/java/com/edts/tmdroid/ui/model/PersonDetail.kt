package com.edts.tmdroid.ui.model

import com.edts.tmdroid.data.common.Gender
import com.edts.tmdroid.data.remote.response.PersonDetailDto
import java.time.LocalDate
import java.time.Period

data class PersonDetail(
    val id: Int,
    val biography: String,
    val gender: Gender,
    val department: String,
    val birthday: String?,
    val deathDate: String?,
    val birthPlace: String?,
    val alsoKnownAs: String,
) {
    private val isDead: Boolean = deathDate != null

    private val rawAge: Int = Period.between(
        LocalDate.parse(birthday),
        if (isDead) LocalDate.parse(deathDate) else LocalDate.now(),
    ).years

    private val age: String = if (isDead) {
        "died, at the age of $rawAge"
    } else {
        "$rawAge years old"
    }

    val genderAndDepartment: String = "$gender, $department"
    val birthDayAndAge: String = "$birthday ($age)"

    companion object {

        fun from(dto: PersonDetailDto): PersonDetail = PersonDetail(
            id = dto.id,
            biography = dto.biography,
            gender = dto.gender,
            department = dto.known_for_department,
            birthday = dto.birthday,
            deathDate = dto.deathday,
            birthPlace = dto.place_of_birth,
            alsoKnownAs = dto.also_known_as.joinToString(", "),
        )
    }
}
