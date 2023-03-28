package com.edts.tmdroid.ui.person.list

import com.edts.tmdroid.ui.model.Person

data class PersonListState(
    val isLoading: Boolean = false,
    val people: List<Person> = emptyList(),
)