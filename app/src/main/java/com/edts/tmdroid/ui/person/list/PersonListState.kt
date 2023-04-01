package com.edts.tmdroid.ui.person.list

import com.edts.tmdroid.ui.model.Fallback
import com.edts.tmdroid.ui.model.Person

data class PersonListState(
    val isLoading: Boolean,
    val fallback: Fallback?,
    val people: List<Person>,
)
